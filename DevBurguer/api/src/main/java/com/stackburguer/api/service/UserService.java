package com.stackburguer.api.service;

import com.stackburguer.api.DTO.UserRequestDTO;
import com.stackburguer.api.DTO.UserResponseDTO;
import com.stackburguer.api.models.User;
import com.stackburguer.api.repositories.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserService {

    @Autowired
    private UserRepository userRepository;

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

        //TODO: Utilizar o bcrypt aqui
        user.setPassword(dto.password());

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
