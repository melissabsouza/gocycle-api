package fiap.tds.gocycleapi.service;


import fiap.tds.gocycleapi.dto.ProfileDTO;
import fiap.tds.gocycleapi.model.Address;
import fiap.tds.gocycleapi.model.Profile;
import fiap.tds.gocycleapi.model.Telephone;
import fiap.tds.gocycleapi.model.User;
import fiap.tds.gocycleapi.repository.AddressRepository;
import fiap.tds.gocycleapi.repository.ProfileRepository;
import fiap.tds.gocycleapi.repository.TelephoneRepository;
import fiap.tds.gocycleapi.repository.UserRepository;
import fiap.tds.gocycleapi.service.mapper.AddressMapper;
import fiap.tds.gocycleapi.service.mapper.ProfileMapper;
import fiap.tds.gocycleapi.service.mapper.ProfileMapperImpl;
import fiap.tds.gocycleapi.service.mapper.TelephoneMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Cria/edita o profile, telephone e address


@Service
@AllArgsConstructor
public class ProfileService {

    private final ProfileMapperImpl profileMapperImpl;
    private ProfileRepository profileRepository;
    private AddressRepository addressRepository;
    private TelephoneRepository telephoneRepository;
    private UserRepository userRepository;

    private ProfileMapper profileMapper;
    private AddressMapper addressMapper;
    private TelephoneMapper telephoneMapper;

    @Transactional
    public List<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(profileMapper::toDto)
                .collect(Collectors.toList());
    }


    // Paginação
    public Page<ProfileDTO> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable).map(profileMapper::toDto);
    }

    @Transactional
    public Optional<ProfileDTO> getProfileByCpf(String cpf) {
        return profileRepository.findByCpf(cpf).map(profileMapper::toDto);
    }

    // aqui seta o user como nulo pra não ter problema na exclusão
    @Transactional
    public void deleteProfileByCpf(String cpf) {
        Optional<Profile> existingProfile = profileRepository.findByCpf(cpf);

        if (existingProfile.isPresent()) {
            Profile profile = existingProfile.get();

            if (profile.getUser() != null) {
                profile.setUser(null);
                profileRepository.save(profile);
            }
            profileRepository.delete(profile);
        } else {
            throw new IllegalArgumentException("Profile not found");
        }
    }


//    @Transactional
//    public void deleteProfileByCpf(String cpf) {
//        Optional<Profile> existingProfile = profileRepository.findByCpf(cpf);
//        if (existingProfile.isPresent()) {
//            profileRepository.delete(existingProfile.get());
//        }else{
//            throw new IllegalArgumentException("Profile not found");
//        }
//    }

    @Transactional
    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        Optional<Profile> newProfile = profileRepository.findByCpf(profileDTO.getCpf());

        if (newProfile.isPresent()) {
            throw new IllegalArgumentException("Profile already exists");
        }

        User user = userRepository.findById(profileDTO.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Address address = addressMapper.toEntity(profileDTO.getAddress());
        Address savedAddress = addressRepository.save(address);

        Telephone telephone = telephoneMapper.toEntity(profileDTO.getTelephone());
        Telephone savedTelephone = telephoneRepository.save(telephone);

        Profile profile = profileMapper.toEntity(profileDTO);
        profile.setAddress(savedAddress);
        profile.setTelephone(savedTelephone);
        profile.setUser(user);

        Profile savedProfile = profileRepository.save(profile);

        return profileMapper.toDto(savedProfile);
    }

    public ProfileDTO updateProfile(String cpf, ProfileDTO profileDTO) {
        Profile existingProfile = profileRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Profile with cpf " + cpf + " not found"));

        Address address = addressMapper.toEntity(profileDTO.getAddress());
        address.setId(existingProfile.getAddress().getId());
        Address updatedAddress = addressRepository.save(address);

        Telephone telephone = telephoneMapper.toEntity(profileDTO.getTelephone());
        telephone.setId(existingProfile.getTelephone().getId());
        Telephone updatedTelephone = telephoneRepository.save(telephone);

        existingProfile.setBirthdate(profileDTO.getBirthdate());
        existingProfile.setName(profileDTO.getName());
        existingProfile.setUsername(profileDTO.getUsername());
        existingProfile.setTelephone(updatedTelephone);
        existingProfile.setAddress(updatedAddress);

        Profile updatedProfile = profileRepository.save(existingProfile);
        return profileMapper.toDto(updatedProfile);

    }

}
