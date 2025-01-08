package ru.edu.taskify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.taskify.entity.Place;
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
