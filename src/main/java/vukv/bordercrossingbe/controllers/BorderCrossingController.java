package vukv.bordercrossingbe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vukv.bordercrossingbe.services.BorderCrossingAnalyticsService;
import vukv.bordercrossingbe.services.BorderCrossingService;

import java.util.UUID;

@RestController
@RequestMapping("/api/border-crossing")
@RequiredArgsConstructor
public class BorderCrossingController {

    private final BorderCrossingService borderCrossingService;
    private final BorderCrossingAnalyticsService borderCrossingAnalyticsService;

    @PostMapping("/{borderId}")
    public ResponseEntity<?> arrivedAtBorder(@PathVariable UUID borderId) {
        return ResponseEntity.ok(borderCrossingService.arrivedAtBorder(borderId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> crossedBorder(@PathVariable UUID id) {
        return ResponseEntity.ok(borderCrossingService.crossedBorder(id));
    }

    @GetMapping("/analytics/{borderId}")
    public ResponseEntity<?> getAnalytics(@PathVariable UUID borderId, @RequestParam(defaultValue = "UTC") String userTimeZone) {
        return ResponseEntity.ok(borderCrossingAnalyticsService.getAnalyticsByBorderId(borderId, userTimeZone));
    }

}
