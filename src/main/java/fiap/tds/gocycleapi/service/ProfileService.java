package fiap.tds.gocycleapi.service;


import fiap.tds.gocycleapi.dto.ProfileDTO;
import fiap.tds.gocycleapi.model.Address;
import fiap.tds.gocycleapi.model.Profile;
import fiap.tds.gocycleapi.model.Telephone;
import fiap.tds.gocycleapi.repository.AddressRepository;
import fiap.tds.gocycleapi.repository.ProfileRepository;
import fiap.tds.gocycleapi.repository.TelephoneRepository;
import fiap.tds.gocycleapi.service.mapper.AddressMapper;
import fiap.tds.gocycleapi.service.mapper.ProfileMapper;
import fiap.tds.gocycleapi.service.mapper.TelephoneMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO
// Pra criar o service desse tem que usar perfil telefone e endereco
// o sistema de pontos depois tem que ser adicionado por usagem .

@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;
    private AddressRepository addressRepository;
    private TelephoneRepository telephoneRepository;

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

//    Endereco endereco = enderecoMapper.toEntity(clinicaDTO.getEndereco());
//    Endereco savedEndereco = enderecoRepository.save(endereco);
//
//    Telefone telefone = telefoneMapper.toEntity(clinicaDTO.getTelefone());
//    Telefone savedTelefone = telefoneRepository.save(telefone);
//
//
//    Clinica clinica = clinicaMapper.toEntity(clinicaDTO);
//        clinica.setUsuario(savedUsuario);
//        clinica.setEndereco(savedEndereco);
//        clinica.setTelefone(savedTelefone);
//
//    Clinica savedClinica = clinicaRepository.save(clinica);
//        return clinicaMapper.toDto(savedClinica);

    public ProfileDTO saveProfile(ProfileDTO profileDTO) {

        Optional<Profile> newProfile = profileRepository.findByCpf(profileDTO.getCpf());

        if(newProfile.isPresent()){
            throw new IllegalArgumentException("Profile already exists");
        }

        Address address = addressMapper.toEntity(profileDTO.getAddress());
        Address savedAddress = addressRepository.save(address);

        Telephone telephone = telephoneMapper.toEntity(profileDTO.getTelephone());
        Telephone savedTelephone = telephoneRepository.save(telephone);

        Profile profile = profileMapper.toEntity(profileDTO);
        profile.setAddress(savedAddress);
        profile.setTelephone(savedTelephone);

         Profile savedProfile = profileRepository.save(profile);

         return profileMapper.toDto(savedProfile);

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
