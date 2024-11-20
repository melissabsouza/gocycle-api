package fiap.tds.gocycleapi.service;

import fiap.tds.gocycleapi.dto.UserDTO;
import fiap.tds.gocycleapi.model.User;
import fiap.tds.gocycleapi.repository.UserRepository;
import fiap.tds.gocycleapi.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Transactional
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<UserDTO> getUserById(Long id){
        return userRepository.findById(id).map(userMapper::toDto);
    }

    // Paginação
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    public void deleteUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else{
            throw new IllegalArgumentException("User not found");
        }
    }

    public User saveUser(UserDTO userDTO) {
        User newUser = userMapper.toEntity(userDTO);

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        } else {
            return userRepository.save(newUser);
        }
    }


    public UserDTO updateUser(Long id, UserDTO userDTO){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));

        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setStatus(userDTO.getStatus());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }
}
