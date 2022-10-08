package com.example.backenddemo.service;

import com.example.backenddemo.domain.User;
import com.example.backenddemo.dto.UserDTO;
import com.example.backenddemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(UserDTO userDTO) throws Exception {
        try {
            User user = User
                    .builder()
                    .name(userDTO.getName().toLowerCase(Locale.ROOT))
                    .password(userDTO.getPassword())
                    .actorType(userDTO.getActorType())
                    .build();
            userRepository.save(user);
            return user;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public User userRegistration(UserDTO userDTO) throws Exception {
        try {
            User user = userRepository.findByName(userDTO.getName().toLowerCase(Locale.ROOT));
            if (user == null) {
                return createUser(userDTO);
            } else {
                throw new Exception("User already exists");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public User userLogin(String name, String password) throws Exception {
        User user = userRepository.findByName(name.toLowerCase());
        if (user == null) {
            throw new Exception(String.format("Could not find user by name \"%s\".", name));
        } else if (!password.equals(user.getPassword())) {
            throw new Exception("Invalid password.");
        }
        return user;
    }

}
