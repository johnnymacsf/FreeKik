package com.FreeKik.server.Controllers;

import com.FreeKik.server.Handlers.UserStatsDto;
import com.FreeKik.server.models.User;
import com.FreeKik.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<UserStatsDto> getUserStats(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserStatsDto stats = new UserStatsDto(user.getUsername(), user.getPoints(), user.getWins(), user.getLosses());
        return ResponseEntity.ok(stats);
    }
}