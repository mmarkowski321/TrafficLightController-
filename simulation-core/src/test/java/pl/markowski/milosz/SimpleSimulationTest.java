package pl.markowski.milosz;

import org.junit.jupiter.api.Test;
import pl.markowski.milosz.dto.SimulationCommand;
import pl.markowski.milosz.dto.SimulationRequest;
import pl.markowski.milosz.dto.SimulationResponse;
import pl.markowski.milosz.service.SimulationService;
import pl.markowski.milosz.service.MonitoringService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleSimulationTest {

    @Test
    public void testBasicSimulation() {
        MonitoringService monitoringService = new MonitoringService();
        SimulationService service = new SimulationService();
        try {
            java.lang.reflect.Field field = SimulationService.class.getDeclaredField("monitoringService");
            field.setAccessible(true);
            field.set(service, monitoringService);
        } catch (Exception e) {
            fail("Nie można ustawić monitoringService: " + e.getMessage());
        }
        
        List<SimulationCommand> commands = Arrays.asList(
            new SimulationCommand("addVehicle", "vehicle1", "south", "north"),
            new SimulationCommand("addVehicle", "vehicle2", "north", "south"),
            new SimulationCommand("step"),
            new SimulationCommand("step")
        );
        
        SimulationRequest request = new SimulationRequest(commands);
        
        SimulationResponse response = service.executeSimulation(request);
        
        assertNotNull(response);
        assertNotNull(response.getStepStatuses());
        assertEquals(2, response.getStepStatuses().size());
        
        assertTrue(response.getStepStatuses().get(0).getLeftVehicles().size() > 0 || 
                  response.getStepStatuses().get(1).getLeftVehicles().size() > 0);
        assertTrue(response.getStepStatuses().get(0).getLeftVehicles().size() > 0 || 
                  response.getStepStatuses().get(1).getLeftVehicles().size() > 0);
        
        System.out.println("Test passed! Response: " + response.getStepStatuses());
    }
} 