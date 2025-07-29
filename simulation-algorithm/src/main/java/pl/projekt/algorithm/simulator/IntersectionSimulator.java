package pl.projekt.algorithm.simulator;

import pl.projekt.algorithm.controller.TrafficController;
import pl.projekt.algorithm.state.SimulationStepResult;
import pl.projekt.models.*;

import java.util.*;

public class IntersectionSimulator {
    private final Intersection intersection;
    private final TrafficController controller;
    private int stepNumber = 0;

    public IntersectionSimulator(Intersection intersection, TrafficController controller) {
        this.intersection = intersection;
        this.controller = controller;
    }

    public SimulationStepResult step() {
        stepNumber++;

        List<Car> waitingCars = new ArrayList<>();
        List<Pedestrian> waitingPeds = new ArrayList<>();
        for (Road r : intersection.getRoads()) {
            waitingCars.addAll(r.getCars());
            waitingPeds.addAll(r.getPedestrians());
        }

        Map<Location, TrafficLight.TrafficLightColor> carLights = controller.determineTrafficLights(intersection);
        Map<Location, PedestrianLight.PedestrianLightColor> pedLights = controller.determinePedestrianLights(intersection);

        for (Road r : intersection.getRoads()) {
            r.getTrafficLight().setColor(carLights.get(r.getLocation()));
            r.getPedestrianLight().setColor(pedLights.get(r.getLocation()));
        }

        List<String> leftCars = new ArrayList<>();
        for (Road r : intersection.getRoads()) {
            if (r.getTrafficLight().getColor() == TrafficLight.TrafficLightColor.GREEN) {
                while (r.hasCars()) {
                    Car c = r.peekFirstCar();
                    if (controller.isCarMoveAllowed(c)) {
                        leftCars.add(r.getFirstCar().getId());
                    } else {
                        break;
                    }
                }
            }
        }

        List<String> leftPedestrians = new ArrayList<>();
        for (Road r : intersection.getRoads()) {
            if (r.getPedestrianLight().getColor() == PedestrianLight.PedestrianLightColor.GREEN) {
                Iterator<Pedestrian> it = r.getPedestrians().iterator();
                while (it.hasNext()) {
                    Pedestrian p = it.next();
                    leftPedestrians.add(p.getId());
                    it.remove();
                }
            }
        }

        return new SimulationStepResult(stepNumber, leftCars, leftPedestrians, carLights, pedLights);
    }
}
