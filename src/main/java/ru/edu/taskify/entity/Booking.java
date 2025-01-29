package ru.edu.taskify.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;



    private LocalDate checkIn;


    private LocalDate checkOut;


    @Column(nullable = false)
    private String name;


    private String phone;


    private Double price;
    private int numberOfGuests;
}