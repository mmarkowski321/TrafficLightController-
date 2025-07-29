package pl.markowski.milosz.controller;

import pl.markowski.milosz.service.MonitoringService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monitoring")
@CrossOrigin(origins = "*")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;
    @GetMapping("/stats")
    public ResponseEntity<MonitoringService.SimulationStats> getStats() {
        return ResponseEntity.ok(monitoringService.getStats());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Monitoring service is running!");
    }
} 