package ru.edu.taskify.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class PlaceRequest {
    private Long id;
    private String title;
    private String address;
    private List<String> addedPhotos;
    private String description;
    private Double price;
    private List<String> perks;
    private String extraInfo;

    // Если используем LocalTime
    private LocalTime checkIn;
    private LocalTime checkOut;

    private Integer maxGuests;
}
