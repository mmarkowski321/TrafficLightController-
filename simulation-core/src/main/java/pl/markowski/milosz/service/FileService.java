package pl.markowski.milosz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.markowski.milosz.dto.SimulationRequest;
import pl.markowski.milosz.dto.SimulationResponse;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SimulationRequest loadSimulationRequest(String inputFile) throws IOException {
        return objectMapper.readValue(new File(inputFile), SimulationRequest.class);
    }

    public void saveSimulationResponse(SimulationResponse response, String outputFile) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter()
                   .writeValue(new File(outputFile), response);
    }

    public void executeSimulationFromFile(String inputFile, String outputFile) throws IOException {
        SimulationRequest request = loadSimulationRequest(inputFile);
        SimulationResponse response = simulationService.executeSimulation(request);
        saveSimulationResponse(response, outputFile);
    }

    private final SimulationService simulationService;

    public FileService(SimulationService simulationService) {
        this.simulationService = simulationService;
    }
} 