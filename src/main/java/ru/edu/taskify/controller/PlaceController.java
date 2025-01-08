package ru.edu.taskify.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.edu.taskify.entity.Place;
import ru.edu.taskify.repo.PlaceRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/places")
public class PlaceController {
    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
    PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @GetMapping
    public List<Place> places() {
        logger.info("connect");
        return placeRepository.findAll();
    }
    @GetMapping("/undefined")
    public void undefined(){

    }
}
