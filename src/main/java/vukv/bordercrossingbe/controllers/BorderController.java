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

    @GetMapping("/all")
    public ResponseEntity<?> getAllBorders(BorderFilterRequest borderFilterRequest) {
        return ResponseEntity.ok(borderService.getAllBorders(borderFilterRequest));
    }

    @GetMapping
    public ResponseEntity<?> getAllBordersPageable(BorderFilterRequest borderFilterRequest) {
        return ResponseEntity.ok(borderService.getAllBordersPageable(borderFilterRequest));
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
