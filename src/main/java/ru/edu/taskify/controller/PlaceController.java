package ru.edu.taskify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.edu.taskify.dto.PlaceRequest;
import ru.edu.taskify.entity.AppUser;
import ru.edu.taskify.entity.Place;
import ru.edu.taskify.repo.PlaceRepository;
import ru.edu.taskify.repo.UserRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PlaceController {

   private final PlaceRepository placeRepository;
    private final UserRepository userRepository;


    @GetMapping("/places")
    public List<Place> places() {
        return placeRepository.findAll();
    }

    @GetMapping("/places/{id}")
    public Place getPlaceById(@PathVariable Long id) {
        return placeRepository.findById(id).orElse(null);
    }

    @GetMapping("/user-places")
    public List<Place> getUserPlaces(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        String email = userDetails.getUsername();
        AppUser appUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        return placeRepository.findByOwnerId(appUser.getId());
    }

    @PostMapping("/places")
    public ResponseEntity<?> createPlace(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody PlaceRequest request
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }


        AppUser appUser = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        Place place = Place.builder()
            .owner(appUser)
            .title(request.getTitle())
            .address(request.getAddress())
            .photos(request.getAddedPhotos())
            .description(request.getDescription())
            .price(request.getPrice())
            .perks(request.getPerks())
            .extraInfo(request.getExtraInfo())
            .checkIn(request.getCheckIn())
            .checkOut(request.getCheckOut())
            .maxGuests(request.getMaxGuests())
            .build();


        Place saved = placeRepository.save(place);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/places")
    public ResponseEntity<?> updatePlace(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody PlaceRequest request
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }


        AppUser appUser = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        Long placeId = request.getId();
        if (placeId == null) {
            return ResponseEntity.badRequest().body("Place ID is required");
        }

        Place place = placeRepository.findById(placeId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));


        if (!place.getOwner().getId().equals(appUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not the owner");
        }


        place.setTitle(request.getTitle());
        place.setAddress(request.getAddress());
        place.setPhotos(request.getAddedPhotos());
        place.setDescription(request.getDescription());
        place.setPrice(request.getPrice());
        place.setPerks(request.getPerks());
        place.setExtraInfo(request.getExtraInfo());
        place.setCheckIn(request.getCheckIn());
        place.setCheckOut(request.getCheckOut());
        place.setMaxGuests(request.getMaxGuests());


        placeRepository.save(place);

        return ResponseEntity.ok("ok");
    }
}
