package pl.projekt.algorithm.state;

import pl.projekt.models.*;
import java.util.Map;

public class SimulationState {
    private final int step;
    private final Map<Location, Integer> vehicleCounts;
    private final Map<Location, Integer> pedestrianCounts;
    private final int vehiclesInTransit;
    private final int pedestriansInTransit;

    public SimulationState(int step, 
                         Map<Location, Integer> vehicleCounts,
                         Map<Location, Integer> pedestrianCounts,
                         int vehiclesInTransit,
                         int pedestriansInTransit) {
        this.step = step;
        this.vehicleCounts = vehicleCounts;
        this.pedestrianCounts = pedestrianCounts;
        this.vehiclesInTransit = vehiclesInTransit;
        this.pedestriansInTransit = pedestriansInTransit;
    }

    public int getStep() {
        return step;
    }

    public Map<Location, Integer> getVehicleCounts() {
        return vehicleCounts;
    }

    public Map<Location, Integer> getPedestrianCounts() {
        return pedestrianCounts;
    }

    public int getVehiclesInTransit() {
        return vehiclesInTransit;
    }

    public int getPedestriansInTransit() {
        return pedestriansInTransit;
    }

    public int getTotalVehicles() {
        return vehicleCounts.values().stream().mapToInt(Integer::intValue).sum() + vehiclesInTransit;
    }

    public int getTotalPedestrians() {
        return pedestrianCounts.values().stream().mapToInt(Integer::intValue).sum() + pedestriansInTransit;
    }

    @Override
    public String toString() {
        return String.format("SimulationState{step=%d, vehicles=%s, pedestrians=%s, inTransit=%d/%d}",
                step, vehicleCounts, pedestrianCounts, vehiclesInTransit, pedestriansInTransit);
    }
}
