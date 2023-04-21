package com.application.controller;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.model.User;
import com.application.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(path = "/API")
public class UserController {
    @Autowired

    private UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping(path = "/user")
    public @ResponseBody Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping(path = "/user/{id}")
    public @ResponseBody Optional<User> getUser(@PathVariable Integer id) {

        return userRepository.findById(id);
    }

    @PostMapping(path = "/user")
    public @ResponseBody String addNewUser(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password) {

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return "Le nouvelle utilisateur a bien été sauvegardé";
    }

    @PutMapping(path = "/user/{id}/update")
    public @ResponseBody String updateUser(
            @PathVariable Integer id,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password) {

        User user = userRepository.findById(id).get();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        return "L'utilisateur a bien été mis à jour";
    }

    @DeleteMapping(path = "/user/{id}/delete")
    public @ResponseBody String delUser(@PathVariable(value = "id") Integer id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return "L'utilisateur a bien été supprimé";
    }
}