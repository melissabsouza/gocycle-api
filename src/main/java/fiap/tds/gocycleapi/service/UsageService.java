package fiap.tds.gocycleapi.service;

import fiap.tds.gocycleapi.dto.UsageDTO;
import fiap.tds.gocycleapi.model.Payment;
import fiap.tds.gocycleapi.model.Usage;
import fiap.tds.gocycleapi.repository.PaymentRepository;
import fiap.tds.gocycleapi.repository.ProfileRepository;
import fiap.tds.gocycleapi.repository.UsageRepository;
import fiap.tds.gocycleapi.service.mapper.PaymentMapper;
import fiap.tds.gocycleapi.service.mapper.UsageMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO LOGICA DOS PONTOS

@Service
@AllArgsConstructor
public class UsageService {

    private UsageRepository usageRepository;
    private PaymentRepository paymentRepository;

    private UsageMapper usageMapper;
    private PaymentMapper paymentMapper;

    private ProfileRepository profileRepository;



    @Transactional
    public List<UsageDTO> getAllUsages() {
        return usageRepository.findAll()
                .stream()
                .map(usageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<UsageDTO> getUsageById(Long id) {
        return usageRepository.findById(id).map(usageMapper::toDto);
    }

    // Paginação
    public Page<UsageDTO> findAll(Pageable pageable) {
        return usageRepository.findAll(pageable).map(usageMapper::toDto);
    }


    public UsageDTO saveUsage(UsageDTO usageDTO) {






//        Optional<Usage> newUsage = usageRepository.findById(usageDTO.getId());
//        if (newUsage.isPresent()) {
//            throw new IllegalArgumentException("Usage already exists");
//        }
//
//        Payment payment = paymentMapper.toEntity(usageDTO.getPayment());
//        Payment newPayment = paymentRepository.save(payment);
//
//        Usage usage = usageMapper.toEntity(usageDTO);
//        usage.setPayment(newPayment);
//
//        Usage savedUsage = usageRepository.save(usage);
//        return usageMapper.toDto(savedUsage);

    }

    public UsageDTO updateUsage(Long id, UsageDTO usageDTO) {
        Usage existingUsage = usageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usage not found"));

        Payment payment = paymentMapper.toEntity(usageDTO.getPayment());
        payment.setId(existingUsage.getId());
        Payment updatedPayment = paymentRepository.save(payment);

        existingUsage.setPickupDateTime(existingUsage.getPickupDateTime());
        existingUsage.setReturnDateTime(existingUsage.getReturnDateTime());
        existingUsage.setDuration(usageDTO.getDuration());

        Usage updatedUsage = usageRepository.save(existingUsage);
        return usageMapper.toDto(updatedUsage);
    }


    public void deleteUsageById(Long id) {
        Optional<Usage> existingUsage = usageRepository.findById(id);
        if (existingUsage.isPresent()) {
            usageRepository.delete(existingUsage.get());
        }else{
            throw new IllegalArgumentException("Usage not found");
        }
    }
}
