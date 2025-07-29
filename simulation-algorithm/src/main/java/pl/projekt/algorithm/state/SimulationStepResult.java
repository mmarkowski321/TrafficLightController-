package pl.projekt.algorithm.state;

import pl.projekt.models.Location;
import pl.projekt.models.TrafficLight;
import pl.projekt.models.PedestrianLight;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SimulationStepResult {
    private final int stepNumber;
    private final SimulationState state;
    private final List<String> leftCars;
    private final List<String> leftPedestrians;
    private final List<String> leftConflicts;
    private final Map<Location, TrafficLight.TrafficLightColor> carLights;
    private final Map<Location, PedestrianLight.PedestrianLightColor> pedestrianLights;

    public SimulationStepResult(int stepNumber,
                                SimulationState state,
                                List<String> leftCars,
                                List<String> leftPedestrians,
                                List<String> leftConflicts,
                                Map<Location, TrafficLight.TrafficLightColor> carLights,
                                Map<Location, PedestrianLight.PedestrianLightColor> pedestrianLights) {
        this.stepNumber = stepNumber;
        this.state = state;
        this.leftCars = leftCars;
        this.leftPedestrians = leftPedestrians;
        this.leftConflicts = leftConflicts;
        this.carLights = carLights;
        this.pedestrianLights = pedestrianLights;
    }

    public SimulationStepResult(int stepNumber,
                                List<String> leftCars,
                                Map<Location, TrafficLight.TrafficLightColor> carLights,
                                Map<Location, PedestrianLight.PedestrianLightColor> pedestrianLights) {
        this(
                stepNumber,
                new SimulationState(
                        stepNumber,
                        Collections.emptyMap(),
                        Collections.emptyMap(),
                        0,
                        0
                ),
                leftCars,
                Collections.emptyList(),
                Collections.emptyList(),
                carLights,
                pedestrianLights
        );
    }


    public SimulationStepResult(int stepNumber,
                                List<String> leftCars,
                                List<String> leftPedestrians,
                                Map<Location, TrafficLight.TrafficLightColor> carLights,
                                Map<Location, PedestrianLight.PedestrianLightColor> pedestrianLights) {
        this(
                stepNumber,
                new SimulationState(
                        stepNumber,
                        Collections.emptyMap(),
                        Collections.emptyMap(),
                        0,
                        0
                ),
                leftCars,
                leftPedestrians,
                Collections.emptyList(),
                carLights,
                pedestrianLights
        );
    }

    public int getStepNumber() { return stepNumber; }
    public SimulationState getState() { return state; }
    public List<String> getLeftCars() { return leftCars; }
    public List<String> getLeftPedestrians() { return leftPedestrians; }
    public List<String> getLeftConflicts() { return leftConflicts; }
    public Map<Location, TrafficLight.TrafficLightColor> getCarLights() { return carLights; }
    public Map<Location, PedestrianLight.PedestrianLightColor> getPedestrianLights() { return pedestrianLights; }
}
