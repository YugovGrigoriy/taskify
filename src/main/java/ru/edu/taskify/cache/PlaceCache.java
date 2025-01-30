package ru.edu.taskify.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.edu.taskify.entity.Place;
import ru.edu.taskify.repo.PlaceRepository;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
@Getter
public class PlaceCache {

    private final PlaceRepository placeRepository;
    private List<Place> placeCache = new ArrayList<>();

    @Scheduled(fixedRate = 60000) // Каждую минуту обновляем кеш
    private void loadCache() {
       placeCache.clear();
       placeCache=placeRepository.findAll();
    }


}
