package pl.markowski.milosz.controller;

import pl.markowski.milosz.dto.SimulationRequest;
import pl.markowski.milosz.dto.SimulationResponse;
import pl.markowski.milosz.service.SimulationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "*")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @PostMapping("/execute")
    public ResponseEntity<SimulationResponse> executeSimulation(@RequestBody SimulationRequest request) {
        try {
            SimulationResponse response = simulationService.executeSimulation(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Simulation service is running!");
    }
} 