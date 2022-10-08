package com.example.backenddemo.service;

import com.example.backenddemo.domain.User;
import com.example.backenddemo.domain.exceptions.UserException;
import com.example.backenddemo.dto.UserDTO;
import com.example.backenddemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User saveUser(UserDTO userDTO) throws UserException {
        try {
            User user = User
                    .builder()
                    .name(userDTO.getName())
                    .password(userDTO.getPassword())
                    .actorType(userDTO.getActorType())
                    .build();
            userRepository.save(user);
            return user;
        } catch (Exception ex) {
            throw new UserException(ex.getMessage());
        }
    }

    public User userLogin(String name, String password) throws UserException {
        User user = findUserByName(name);
        if (user == null) {
            throw new UserException(String.format("Could not find user by name \"%s\".", name));
        }
        else if (!password.equals(user.getPassword())) {
            throw new UserException("Invalid password.");
        }
        return user;
    }

}
