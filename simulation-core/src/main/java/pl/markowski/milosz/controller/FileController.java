package pl.markowski.milosz.controller;

import pl.markowski.milosz.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeFromFile(@RequestParam String inputFile, 
                                                @RequestParam String outputFile) {
        try {
            fileService.executeSimulationFromFile(inputFile, outputFile);
            return ResponseEntity.ok("Simulation executed successfully. Output saved to: " + outputFile);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing files: " + e.getMessage());
        }
    }
} 