package ru.edu.taskify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.taskify.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
