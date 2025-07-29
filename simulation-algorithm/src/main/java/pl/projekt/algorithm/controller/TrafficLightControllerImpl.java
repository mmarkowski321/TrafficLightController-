package pl.projekt.algorithm.controller;

import pl.projekt.models.*;
import java.util.*;


public class TrafficLightControllerImpl implements TrafficController {
    private int currentPhase = 0;
    private int lastPhase = 0;


    private static final Map<Integer, Map<Location, List<Car.CarDirection>>> ALLOWED = Map.of(
            1, Map.of(Location.NORTH, List.of(Car.CarDirection.EAST),
                    Location.SOUTH, List.of(Car.CarDirection.WEST)),
            2, Map.of(Location.WEST,  List.of(Car.CarDirection.NORTH),
                    Location.EAST,  List.of(Car.CarDirection.SOUTH)),
            3, Map.of(Location.NORTH, List.of(Car.CarDirection.SOUTH),
                    Location.SOUTH, List.of(Car.CarDirection.NORTH)),
            4, Map.of(Location.WEST,  List.of(Car.CarDirection.EAST),
                    Location.EAST,  List.of(Car.CarDirection.WEST)),
            5, Map.of(Location.NORTH, List.of(Car.CarDirection.WEST),
                    Location.SOUTH, List.of(Car.CarDirection.EAST)),
            6, Map.of(Location.WEST,  List.of(Car.CarDirection.SOUTH),
                    Location.EAST,  List.of(Car.CarDirection.NORTH))
    );

    @Override
    public Map<Location, TrafficLight.TrafficLightColor> determineTrafficLights(Intersection intersection) {
        Optional<Road> prio = Arrays.stream(intersection.getRoads())
                .filter(r -> r.getCarCount() > 5)
                .findFirst();
        int phase;
        if (prio.isPresent()) {
            Road priorityRoad = prio.get();
            Car firstCar = priorityRoad.peekFirstCar();
            phase = findPhaseFor(priorityRoad.getLocation(), firstCar.getDirection());
        } else {
            phase = -1;
            for (int i = 0; i < 6; i++) {
                int nextPhase = currentPhase % 6 + 1;
                boolean anyMove = false;
                for (Road r : intersection.getRoads()) {
                    if (r.hasCars()) {
                        Car firstCar = r.peekFirstCar();
                        Location loc = r.getLocation();
                        List<Car.CarDirection> allowedDirs = ALLOWED
                                .getOrDefault(nextPhase, Collections.emptyMap())
                                .getOrDefault(loc, Collections.emptyList());
                        if (allowedDirs.contains(firstCar.getDirection())) {
                            anyMove = true;
                            break;
                        }
                    }
                }
                currentPhase = nextPhase;
                if (anyMove) {
                    phase = nextPhase;
                    break;
                }
            }
            if (phase == -1) {
                phase = (currentPhase == 0 ? 1 : currentPhase);
            }
        }
        lastPhase = phase;
        return new EnumMap<>(TrafficPhase.of(phase).carLights);
    }

    @Override
    public Map<Location, PedestrianLight.PedestrianLightColor> determinePedestrianLights(Intersection intersection) {
        if (lastPhase == 0) {
            lastPhase = 1;
        }
        return new EnumMap<>(TrafficPhase.of(lastPhase).pedLights);
    }

    @Override
    public boolean isCarMoveAllowed(Car car) {
        TrafficLight.TrafficLightColor light = TrafficPhase.of(lastPhase).carLights.get(car.getLocation());
        if (light != TrafficLight.TrafficLightColor.GREEN) {
            return false;
        }
        List<Car.CarDirection> allowedDirs = ALLOWED
                .getOrDefault(lastPhase, Collections.emptyMap())
                .getOrDefault(car.getLocation(), Collections.emptyList());
        return allowedDirs.contains(car.getDirection());
    }
    private int findPhaseFor(Location start, Car.CarDirection dir) {
        for (int ph = 1; ph <= 6; ph++) {
            List<Car.CarDirection> allowedDirs = ALLOWED
                    .getOrDefault(ph, Collections.emptyMap())
                    .getOrDefault(start, Collections.emptyList());
            if (allowedDirs.contains(dir)) {
                return ph;
            }
        }
        return currentPhase % 6 + 1;
    }
}
