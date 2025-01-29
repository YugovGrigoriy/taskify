package ru.edu.taskify.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long _id;
    @ElementCollection
    private List<String> photos;
    private String address;
    private String title;
    private double price;
    private String description;
    @ElementCollection
    private List<String> perks = new ArrayList<>();

    private String extraInfo;

    private LocalTime checkIn;
    private LocalTime checkOut;

    private Integer maxGuests;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AppUser owner;
}
