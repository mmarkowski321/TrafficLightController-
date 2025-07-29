package pl.markowski.milosz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import pl.markowski.milosz.dto.SimulationCommand;
import pl.markowski.milosz.dto.SimulationRequest;
import pl.markowski.milosz.dto.SimulationResponse;
import pl.markowski.milosz.service.SimulationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.main.web-application-type=none"
})
public class SimulationServiceTest {

    @Autowired
    private SimulationService simulationService;

    @Test
    public void testBasicSimulation() {
        List<SimulationCommand> commands = Arrays.asList(
            new SimulationCommand("addVehicle", "vehicle1", "south", "north"),
            new SimulationCommand("addVehicle", "vehicle2", "north", "south"),
            new SimulationCommand("step"),
            new SimulationCommand("step")
        );
        
        SimulationRequest request = new SimulationRequest(commands);
        
        SimulationResponse response = simulationService.executeSimulation(request);
        
        assertNotNull(response);
        assertNotNull(response.getStepStatuses());
        assertEquals(2, response.getStepStatuses().size());
        
        assertTrue(response.getStepStatuses().get(0).getLeftVehicles().size() > 0 || 
                  response.getStepStatuses().get(1).getLeftVehicles().size() > 0);
        assertTrue(response.getStepStatuses().get(0).getLeftVehicles().size() > 0 || 
                  response.getStepStatuses().get(1).getLeftVehicles().size() > 0);
    }
    
    @Test
    public void testComplexSimulation() {
        List<SimulationCommand> commands = Arrays.asList(
            new SimulationCommand("addVehicle", "vehicle1", "south", "north"),
            new SimulationCommand("addVehicle", "vehicle2", "north", "south"),
            new SimulationCommand("step"),
            new SimulationCommand("step"),
            new SimulationCommand("addVehicle", "vehicle3", "west", "south"),
            new SimulationCommand("addVehicle", "vehicle4", "west", "south"),
            new SimulationCommand("step"),
            new SimulationCommand("step")
        );
        
        SimulationRequest request = new SimulationRequest(commands);
        
        SimulationResponse response = simulationService.executeSimulation(request);
        
        assertNotNull(response);
        assertNotNull(response.getStepStatuses());
        assertEquals(4, response.getStepStatuses().size());
        
        int totalLeftVehicles = response.getStepStatuses().stream()
            .mapToInt(step -> step.getLeftVehicles().size())
            .sum();
        
        assertTrue(totalLeftVehicles > 0, "Przynajmniej jeden pojazd powinien opuścić skrzyżowanie");
    }
} 