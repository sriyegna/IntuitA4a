package com.example.backenddemo.controller;

import com.example.backenddemo.domain.User;
import com.example.backenddemo.domain.exceptions.UserException;
import com.example.backenddemo.dto.UserDTO;
import com.example.backenddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

//    @GetMapping("/user")
//    public User getUser(@RequestParam(value = "name") String name) {
//        return new User(name, "user");
//    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.userLogin(userDTO.getName(), userDTO.getPassword());
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        } catch (UserException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.saveUser(userDTO);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (UserException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) throws Exception {
//        userService.saveUser(userDTO);
//        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
//    }

//    @PostMapping("/user")
//    public ResponseEntity<UserDTO> postUser(@RequestBody UserDTO userDTO) throws Exception {
//        userService.saveUser(userDTO);
//        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
//    }
}
