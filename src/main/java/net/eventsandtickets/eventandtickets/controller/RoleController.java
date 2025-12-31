package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.model.Role;
import net.eventsandtickets.eventandtickets.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepo;

    @PostMapping
    public Role saveRole(@RequestBody Role role) {
        return roleRepo.save(role);

    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        return roleRepo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Role updateRole(@RequestBody Role role, @PathVariable Long id) {
        if (role.getRoleId() == id) {
            return roleRepo.save(role);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleRepo.deleteById(id);
        return "Success";
    }
}
