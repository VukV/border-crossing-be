package vukv.bordercrossingbe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vukv.bordercrossingbe.domain.dtos.border.BorderCreateRequest;
import vukv.bordercrossingbe.domain.dtos.border.BorderFilterRequest;
import vukv.bordercrossingbe.domain.dtos.border.BorderUpdateRequest;
import vukv.bordercrossingbe.services.BorderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/border")
@RequiredArgsConstructor
public class BorderController {

    private final BorderService borderService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBorderById(@PathVariable UUID id) {
        return ResponseEntity.ok(borderService.getBorderById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllBorders(BorderFilterRequest borderFilterRequest) {
        return ResponseEntity.ok(borderService.getAllBorders(borderFilterRequest));
    }

    @GetMapping("/distance")
    public ResponseEntity<?> getAllBordersByDistance(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double distance) {
        return ResponseEntity.ok(borderService.getAllBordersByDistance(distance, latitude, longitude));
    }
    
    @GetMapping("/favourites")
    public ResponseEntity<?> getFavouriteBorders() {
        return ResponseEntity.ok(borderService.getFavouriteBorders());
    }
    
    @PatchMapping("/favourite/{id}")
    public ResponseEntity<?> favouriteBorder(@PathVariable UUID id) {
        borderService.favouriteBorder(id);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/unfavourite/{id}")
    public ResponseEntity<?> unfavouriteBorder(@PathVariable UUID id) {
        borderService.unfavouriteBorder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createBorder(@RequestBody @Valid BorderCreateRequest borderCreateRequest) {
        return ResponseEntity.ok(borderService.createBorder(borderCreateRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateBorder(@PathVariable UUID id, @RequestBody @Valid BorderUpdateRequest borderUpdateRequest) {
        return ResponseEntity.ok(borderService.updateBorder(id, borderUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBorder(@PathVariable UUID id) {
        borderService.deleteBorder(id);
        return ResponseEntity.ok().build();
    }

}
