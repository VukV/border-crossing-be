package vukv.bordercrossingbe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingCreateRequest;
import vukv.bordercrossingbe.services.BorderCrossingAnalyticsService;
import vukv.bordercrossingbe.services.BorderCrossingService;

import java.util.UUID;

@RestController
@RequestMapping("/api/border-crossing")
@RequiredArgsConstructor
public class BorderCrossingController {

    private final BorderCrossingService borderCrossingService;
    private final BorderCrossingAnalyticsService borderCrossingAnalyticsService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBorderCrossing(@PathVariable UUID id) {
        return ResponseEntity.ok(borderCrossingService.getBorderCrossingById(id));
    }

    @PostMapping("/{borderId}")
    public ResponseEntity<?> arrivedAtBorder(@PathVariable UUID borderId) {
        return ResponseEntity.ok(borderCrossingService.arrivedAtBorder(borderId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> crossedBorder(@PathVariable UUID id) {
        return ResponseEntity.ok(borderCrossingService.crossedBorder(id));
    }

    @PostMapping("/manual/{borderId}")
    public ResponseEntity<?> crossedBorderManual(@PathVariable UUID borderId, @RequestBody @Valid BorderCrossingCreateRequest request) {
        return ResponseEntity.ok(borderCrossingService.crossedBorderManual(borderId, request));
    }

    @GetMapping("/recent/{borderId}")
    public ResponseEntity<?> getRecentCrossings(@PathVariable UUID borderId) {
        return ResponseEntity.ok(borderCrossingService.getRecentCrossingsByBorderId(borderId));
    }

    @GetMapping("/analytics/{borderId}")
    public ResponseEntity<?> getAnalytics(@PathVariable UUID borderId, @RequestParam(defaultValue = "UTC") String userTimeZone) {
        return ResponseEntity.ok(borderCrossingAnalyticsService.getAnalyticsByBorderId(borderId, userTimeZone));
    }

}
