package net.eventsandtickets.eventandtickets.repository;

import net.eventsandtickets.eventandtickets.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}