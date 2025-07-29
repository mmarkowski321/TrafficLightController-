package pl.markowski.milosz.service;

import pl.markowski.milosz.dto.SimulationCommand;
import pl.markowski.milosz.dto.SimulationRequest;
import pl.markowski.milosz.dto.SimulationResponse;
import pl.markowski.milosz.dto.StepStatus;
import pl.projekt.algorithm.simulator.IntersectionSimulator;
import pl.projekt.algorithm.controller.TrafficController;
import pl.projekt.algorithm.controller.TrafficLightControllerImpl;
import pl.projekt.algorithm.state.SimulationStepResult;
import pl.projekt.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimulationService {

    @Autowired
    private MonitoringService monitoringService;

    public SimulationResponse executeSimulation(SimulationRequest request) {
        monitoringService.startSimulation();
        
        try {
            Intersection intersection = createIntersection();
            TrafficController controller = new TrafficLightControllerImpl();
            IntersectionSimulator simulator = new IntersectionSimulator(intersection, controller);
            
            List<StepStatus> stepStatuses = new ArrayList<>();
            int vehicleCount = 0;
            
            for (SimulationCommand command : request.getCommands()) {
                switch (command.getType()) {
                    case "addVehicle":
                        addVehicle(intersection, command);
                        vehicleCount++;
                        break;
                    case "step":
                        StepStatus stepStatus = executeStep(simulator);
                        stepStatuses.add(stepStatus);
                        break;
                    default:
                        throw new IllegalArgumentException("Nieznany typ komendy: " + command.getType());
                }
            }
            
            monitoringService.addVehicles(vehicleCount);
            
            return new SimulationResponse(stepStatuses);
        } finally {
            monitoringService.endSimulation();
        }
    }

    private Intersection createIntersection() {
        Road[] roads = new Road[4];
        
        roads[0] = new Road("north_road", Location.NORTH,
                           new TrafficLight("TL_NORTH", Location.NORTH, TrafficLight.TrafficLightColor.RED), 
                           new PedestrianLight("PL_NORTH", Location.NORTH, PedestrianLight.PedestrianLightColor.RED));
        
        roads[1] = new Road("south_road", Location.SOUTH,
                           new TrafficLight("TL_SOUTH", Location.SOUTH, TrafficLight.TrafficLightColor.RED), 
                           new PedestrianLight("PL_SOUTH", Location.SOUTH, PedestrianLight.PedestrianLightColor.RED));
        
        roads[2] = new Road("east_road", Location.EAST,
                           new TrafficLight("TL_EAST", Location.EAST, TrafficLight.TrafficLightColor.RED), 
                           new PedestrianLight("PL_EAST", Location.EAST, PedestrianLight.PedestrianLightColor.RED));
        
        roads[3] = new Road("west_road", Location.WEST,
                           new TrafficLight("TL_WEST", Location.WEST, TrafficLight.TrafficLightColor.RED), 
                           new PedestrianLight("PL_WEST", Location.WEST, PedestrianLight.PedestrianLightColor.RED));
        
        return new Intersection(roads);
    }

    private void addVehicle(Intersection intersection, SimulationCommand command) {
        Location startLocation = parseLocation(command.getStartRoad());
        Location endLocation = parseLocation(command.getEndRoad());
        Car.CarDirection direction = calculateDirection(startLocation, endLocation);
        
        Car car = new Car(command.getVehicleId(), MovingObject.State.WAITING, direction, startLocation);
        
        for (Road road : intersection.getRoads()) {
            if (road.getLocation() == startLocation) {
                road.addCar(car);
                break;
            }
        }
    }

    private StepStatus executeStep(IntersectionSimulator simulator) {
        SimulationStepResult result = simulator.step();
        
        List<String> leftVehicles = new ArrayList<>();
        leftVehicles.addAll(result.getLeftCars());
        
        return new StepStatus(leftVehicles);
    }

    private Location parseLocation(String road) {
        return switch (road.toLowerCase()) {
            case "north" -> Location.NORTH;
            case "south" -> Location.SOUTH;
            case "east" -> Location.EAST;
            case "west" -> Location.WEST;
            default -> throw new IllegalArgumentException("Nieznana droga: " + road);
        };
    }

    private Car.CarDirection calculateDirection(Location start, Location end) {
        return switch (end) {
            case NORTH -> Car.CarDirection.NORTH;
            case SOUTH -> Car.CarDirection.SOUTH;
            case EAST -> Car.CarDirection.EAST;
            case WEST -> Car.CarDirection.WEST;
        };
    }
} 