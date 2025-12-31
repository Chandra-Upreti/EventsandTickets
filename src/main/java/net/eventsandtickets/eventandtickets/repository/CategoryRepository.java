package net.eventsandtickets.eventandtickets.repository;

import net.eventsandtickets.eventandtickets.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}