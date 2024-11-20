package fiap.tds.gocycleapi.service;

import fiap.tds.gocycleapi.dto.UsageDTO;
import fiap.tds.gocycleapi.model.Usage;
import fiap.tds.gocycleapi.repository.PaymentRepository;
import fiap.tds.gocycleapi.repository.UsageRepository;
import fiap.tds.gocycleapi.service.mapper.PaymentMapper;
import fiap.tds.gocycleapi.service.mapper.UsageMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsageService {

    private UsageRepository usageRepository;
    private PaymentRepository paymentRepository;

    private UsageMapper usageMapper;
    private PaymentMapper paymentMapper;



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

    public void deleteUsageById(Long id) {
        Optional<Usage> existingUsage = usageRepository.findById(id);
        if (existingUsage.isPresent()) {
            usageRepository.delete(existingUsage.get());
        }else{
            throw new IllegalArgumentException("Usage not found");
        }
    }
}
