package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.dto.LoginRequest;
import net.eventsandtickets.eventandtickets.dto.LoginResponse;
import net.eventsandtickets.eventandtickets.model.Users;
import net.eventsandtickets.eventandtickets.repository.UserRepository;
import net.eventsandtickets.eventandtickets.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        Users user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();


        String token = jwtTokenUtil.generateToken(
                user.getUsername(),
                user.getRole().getRoleName()
        );


        return new LoginResponse(
                true,
                "Login successful",
                user.getRole().getRoleName(),
                token,
                user.getUserId()   // VERY IMPORTANT for Android
        );
    }
}