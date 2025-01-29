package ru.edu.taskify.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    private Long place;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private int numberOfGuests;
    private String name;
    private String phone;
    private Double price;
}