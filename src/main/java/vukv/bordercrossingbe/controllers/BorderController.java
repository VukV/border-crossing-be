package vukv.bordercrossingbe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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



}
