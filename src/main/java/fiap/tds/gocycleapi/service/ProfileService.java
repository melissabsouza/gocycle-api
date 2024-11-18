package fiap.tds.gocycleapi.service;


import fiap.tds.gocycleapi.dto.ProfileDTO;
import fiap.tds.gocycleapi.dto.UserDTO;
import fiap.tds.gocycleapi.model.Profile;
import fiap.tds.gocycleapi.model.User;
import fiap.tds.gocycleapi.repository.ProfileRepository;
import fiap.tds.gocycleapi.service.mapper.ProfileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


// Pra criar o service desse tem que usar perfil telefone e endereco
//o sistema de pontos depois tem que ser adicionado por usagem .

@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;
    private ProfileMapper profileMapper;

    @Transactional
    public List<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(profileMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ProfileDTO> getProfileByCpf(String cpf) {
        return profileRepository.findByCpf(cpf).map(profileMapper::toDto);
    }

    public void deleteProfileByCpf(String cpf) {
        Optional<Profile> existingProfile = profileRepository.findByCpf(cpf);
        if (existingProfile.isPresent()) {
            profileRepository.delete(existingProfile.get());
        }else{
            throw new IllegalArgumentException("Profile not found");
        }
    }

    public Profile saveProfile(ProfileDTO profileDTO) {
        Optional<Profile> newProfile = profileRepository.findByCpf(profileDTO.getCpf());

        if(newProfile.isPresent()){
            throw new IllegalArgumentException("Profile already exists");
        } else{

            Profile profile = profileMapper.toEntity(profileDTO);
            return profileRepository.save(profile);
        }

    }


    public ProfileDTO updateProfile(String cpf, ProfileDTO profileDTO) {
        Profile existingProfile = profileRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Profile with cpf " + cpf + " not found"));

        existingProfile.setBirthdate(profileDTO.getBirthdate());
        existingProfile.setName(profileDTO.getName());
        existingProfile.setUsername(profileDTO.getUsername());
        Profile updatedProfile = profileRepository.save(existingProfile);
        return profileMapper.toDto(updatedProfile);

    }

}
