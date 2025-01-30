package ru.edu.taskify.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.edu.taskify.entity.Place;
import ru.edu.taskify.repo.PlaceRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Log
@Getter
public class PlaceCache {

    private final PlaceRepository placeRepository;
    private List<Place> placeCache = new ArrayList<>();

    @Autowired
    public PlaceCache(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        loadCache();
    }

    @Scheduled(fixedRate = 30000, initialDelay = 0)
    public void loadCache() {
        log.info("scheduled is jobbing");
        placeCache.clear();
        placeCache = placeRepository.findAll();
    }


}
