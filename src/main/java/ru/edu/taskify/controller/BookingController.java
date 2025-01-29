package ru.edu.taskify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.edu.taskify.dto.BookingRequest;
import ru.edu.taskify.entity.Booking;
import ru.edu.taskify.repo.BookingRepository;
import ru.edu.taskify.repo.PlaceRepository;
import ru.edu.taskify.repo.UserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingRepository bookingRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(
        @RequestBody BookingRequest request,
        @AuthenticationPrincipal UserDetails userDetails
    ) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }


        var appUser = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        var place = placeRepository.findById(request.getPlace())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));


        Booking booking = Booking.builder()
            .place(place)
            .user(appUser)
            .checkIn(request.getCheckIn())
            .checkOut(request.getCheckOut())
            .numberOfGuests(request.getNumberOfGuests())
            .name(request.getName())
            .phone(request.getPhone())
            .price(request.getPrice())
            .build();


        Booking savedBooking = bookingRepository.save(booking);


        return ResponseEntity.ok(savedBooking);
    }

    @GetMapping("/bookings")
    public List<Booking> getUserBookings(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }


        var appUser = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        //List<Booking>

        return bookingRepository.findByUserId(appUser.getId());
    }
}