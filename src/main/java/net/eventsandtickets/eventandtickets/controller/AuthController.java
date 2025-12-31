package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.dto.LoginRequest;
import net.eventsandtickets.eventandtickets.dto.LoginResponse;
import net.eventsandtickets.eventandtickets.model.Users;
import net.eventsandtickets.eventandtickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
                    );

            Users user = userRepository
                    .findByUsername(request.getUsername())
                    .orElseThrow();

            return new LoginResponse(
                    true,
                    "Login successful",
                    user.getRole().getRoleName(),
                    user.getUserId()
            );

        } catch (Exception e) {
            return new LoginResponse(
                    false,
                    "Invalid username or password",
                    null,
                    null
            );
        }
    }
}