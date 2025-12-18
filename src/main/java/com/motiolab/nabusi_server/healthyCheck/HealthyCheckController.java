package com.motiolab.nabusi_server.healthyCheck;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HealthyCheckController {
    @GetMapping("/healthy/check")
    public ResponseEntity<Boolean> getHealthCheck() {
        return ResponseEntity.ok(true);
    }
}
