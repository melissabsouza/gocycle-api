package fiap.tds.gocycleapi.service;

import fiap.tds.gocycleapi.dto.AddressDTO;
import fiap.tds.gocycleapi.dto.ProfileDTO;
import fiap.tds.gocycleapi.model.Address;
import fiap.tds.gocycleapi.model.Profile;
import fiap.tds.gocycleapi.repository.AddressRepository;
import fiap.tds.gocycleapi.service.mapper.AddressMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService {
    private AddressMapper addressMapper;
    private AddressRepository addressRepository;

    @Transactional
    public List<AddressDTO> getAllAddresses(){
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<AddressDTO> getAddressById(Long id) {
        return addressRepository.findById(id).map(addressMapper::toDto);
    }

    public Address saveAddress(AddressDTO addressDTO){

        Address newAddress = addressMapper.toEntity(addressDTO);
        return addressRepository.save(newAddress);

//        Optional<Address> newAddress = addressRepository.findByProfile(addressDTO.getProfile());
//
//        if(newAddress.isPresent()){
//            throw  new IllegalArgumentException("Profile from Address already exists");
//        }else{
//            Address address = addressMapper.toEntity(addressDTO);
//            return addressRepository.save(address);
//        }
    }

    public AddressDTO updateAddress(Long id, AddressDTO addressDTO){
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address with id " + id + "not found"));

        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setNumber(addressDTO.getNumber());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setZipCode(addressDTO.getZipCode());
        existingAddress.setAddInfo(addressDTO.getAddInfo());

        Address updatedAddress = addressRepository.save(existingAddress);
        return addressMapper.toDto(updatedAddress);
    }

    public void deleteAddressById(Long id) {
        Optional<Address> existingAddress = addressRepository.findById(id);
        if (existingAddress.isPresent()) {
            addressRepository.delete(existingAddress.get());
        }else{
            throw new IllegalArgumentException("Address not found");
        }
    }
}
