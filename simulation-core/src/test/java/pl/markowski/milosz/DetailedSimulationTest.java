package pl.markowski.milosz;

import org.junit.jupiter.api.Test;
import pl.markowski.milosz.dto.SimulationCommand;
import pl.markowski.milosz.dto.SimulationRequest;
import pl.markowski.milosz.dto.SimulationResponse;
import pl.markowski.milosz.dto.StepStatus;
import pl.markowski.milosz.service.SimulationService;
import pl.markowski.milosz.service.MonitoringService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DetailedSimulationTest {

    @Test
    public void testDetailedSimulation() {
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
            new SimulationCommand("step"),
            new SimulationCommand("addVehicle", "vehicle3", "west", "south"),
            new SimulationCommand("addVehicle", "vehicle4", "west", "south"),
            new SimulationCommand("step"),
            new SimulationCommand("step")
        );
        
        SimulationRequest request = new SimulationRequest(commands);
        
        SimulationResponse response = service.executeSimulation(request);
        
        assertNotNull(response);
        assertNotNull(response.getStepStatuses());
        assertEquals(4, response.getStepStatuses().size());
        
        System.out.println("=== SZCZEGÓŁOWE WYNIKI SYMULACJI ===");
        System.out.println("=== SZCZEGÓŁOWE WYNIKI SYMULACJI ===");
        for (int i = 0; i < response.getStepStatuses().size(); i++) {
            StepStatus step = response.getStepStatuses().get(i);
            System.out.println("Krok " + (i + 1) + ": " + step.getLeftVehicles());
        }
        
        int totalLeftVehicles = response.getStepStatuses().stream()
            .mapToInt(step -> step.getLeftVehicles().size())
            .sum();
        
        assertTrue(totalLeftVehicles > 0, "Przynajmniej jeden pojazd powinien opuścić skrzyżowanie");
        System.out.println("Łącznie pojazdów, które opuściły skrzyżowanie: " + totalLeftVehicles);
        
        List<String> allLeftVehicles = response.getStepStatuses().stream()
            .flatMap(step -> step.getLeftVehicles().stream())
            .toList();
        
        System.out.println("Wszystkie pojazdy, które opuściły skrzyżowanie: " + allLeftVehicles);
        
        long uniqueVehicles = allLeftVehicles.stream().distinct().count();
        assertEquals(allLeftVehicles.size(), uniqueVehicles, "Wszystkie pojazdy powinny być unikalne");
        
        System.out.println("Test przeszedł pomyślnie!");
    }
} 