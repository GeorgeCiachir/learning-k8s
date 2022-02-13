package com.georgeciachir.appforcloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping
public class HealthController {

    private static final Logger log = LoggerFactory.getLogger(HealthController.class);
    private boolean healthy = true;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        log.info("Health check request received. System is: {}", healthy ? "Up" : "Down");
        if (!healthy) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Down");
        }
        return ok("Up");
    }

    @PostMapping("/health/{healthy}")
    public void setHealth(@PathVariable boolean healthy) {
        log.info("Setting health to: {}", healthy ? "Up" : "Down");
        this.healthy = healthy;
    }

    @ResponseStatus(OK)
    @GetMapping("/readiness")
    public String readiness() {
        log.info("Readiness check request received");
        return "Ready";
    }
}
