package com.stackburguer.api.service;

import com.stackburguer.api.DTO.user.UserRequestDTO;
import com.stackburguer.api.DTO.user.UserResponseDTO;
import com.stackburguer.api.models.User;
import com.stackburguer.api.repositories.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserResponseDTO mapToResponseDTO(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.isAdmin());
    }

    public UserResponseDTO create(UserRequestDTO dto){
        if (userRepository.existsByEmail(dto.email())){
            throw new RuntimeException("Email já existe");
        }

        User user = new User();
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setAdmin(false);

        String hash = passwordEncoder.encode(dto.password());
        user.setPassword(hash); //Passando o password já criptografado

        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getName(), savedUser.isAdmin());
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos."));
    }

    public UserResponseDTO getUserById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return mapToResponseDTO(user);
    }
}
