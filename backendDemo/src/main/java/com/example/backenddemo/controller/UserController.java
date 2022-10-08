package com.example.backenddemo.controller;

import com.example.backenddemo.domain.User;
import com.example.backenddemo.dto.UserDTO;
import com.example.backenddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Checks to see if the userDTO name/password provided is correct and returns the full User
     *
     * @param userDTO
     * @return ResponseEntity<?> - User || String error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.userLogin(userDTO.getName(), userDTO.getPassword());
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Registers user using the userDTO
     *
     * @param userDTO
     * @return ResponseEntity<?> - User || String error message
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.userRegistration(userDTO);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
