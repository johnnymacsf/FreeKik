package com.FreeKik.server.Controllers;

import com.FreeKik.server.models.User;
import com.FreeKik.server.service.JwtService;
import com.FreeKik.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user)  {
        User existingUser = userService.findUserByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User with that username does not exist!"));
        if(!userService.checkPassword(user.getPassword(), existingUser.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Incorrect password"));
        }
        String jwt = jwtService.generateToken(existingUser.getUsername());
        //return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body("Logged in as " + existingUser.getUsername());
        return ResponseEntity.ok(Map.of(
                "token", jwt,
                "username", existingUser.getUsername()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        if(userService.findUserByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this username already exists!");
        }
        if(userService.findUserByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists!");
        }

        userService.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully registered!");
    }
}
