package com.FreeKik.server.service;

import com.FreeKik.server.models.User;
import com.FreeKik.server.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public boolean checkPassword(String password, String securePassword){
        return passwordEncoder.matches(password, securePassword);
    }

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    public User updateUser(User user){
        return userRepo.save(user);
    }

    public void deleteUser(Long id){
        userRepo.deleteUserByUserId(id);
    }

    public User findUserById(Long id){
        return userRepo.findUserByUserId(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    public Optional<User> findUserByEmail(String email){
        return userRepo.findUserByEmail(email);
    }

    public Optional<User> findUserByUsername(String username){
        return userRepo.findUserByUsername(username);
    }
}
