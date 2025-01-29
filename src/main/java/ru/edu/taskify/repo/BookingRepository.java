package ru.edu.taskify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.taskify.entity.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
}
