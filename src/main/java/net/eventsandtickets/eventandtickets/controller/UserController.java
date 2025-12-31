package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.model.Role;
import net.eventsandtickets.eventandtickets.model.Users;
import net.eventsandtickets.eventandtickets.repository.RoleRepository;
import net.eventsandtickets.eventandtickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder; // inject password encoder

    // CREATE USER
    @PostMapping
    public Users saveUser(@RequestBody Users user) {

        // Fetch role from DB using roleId sent in request
        Long roleId = user.getRole().getRoleId();
        Role role = roleRepo.findById(roleId).orElse(null);

        if (role == null) {
            throw new RuntimeException("Role not found with id " + roleId);
        }

        user.setRole(role);

        // Encode the plain password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    // READ ALL USERS
    @GetMapping
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    // READ USER BY ID
    @GetMapping("/{id}")
    public Users getUser(@PathVariable Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @GetMapping("/username/{username}")
    public Users getUserByUsername(@PathVariable String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public Users updateUser(@RequestBody Users user, @PathVariable Long id) {

        if (!userRepo.existsById(id)) {
            return null;
        }

        Role role = roleRepo.findById(user.getRole().getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setUserId(id);
        user.setRole(role);

        // Optional: encode password on update if it's changed
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @PutMapping("/change-password")
    public String changePassword(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam String newPassword) {

        Users user = null;

        if (email != null) {
            user = userRepo.findByEmail(email).orElse(null);
        } else if (username != null) {
            user = userRepo.findByUsername(username).orElse(null);
        }

        if (user == null) {
            return "User not found";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return "Password updated successfully";
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "User deleted successfully";
    }
}