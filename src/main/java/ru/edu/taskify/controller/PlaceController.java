package ru.edu.taskify.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.taskify.entity.Place;
import ru.edu.taskify.repo.PlaceRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlaceController {
    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
    PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @GetMapping(value = "/places")
    public List<Place> places() {
        logger.info("connect");
        return placeRepository.findAll();
    }
}
