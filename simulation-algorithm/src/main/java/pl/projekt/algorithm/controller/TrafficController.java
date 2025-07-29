package pl.projekt.algorithm.controller;

import pl.projekt.models.*;

import java.util.Map;

public interface TrafficController {
    Map<Location, TrafficLight.TrafficLightColor> determineTrafficLights(Intersection intersection);
    Map<Location, PedestrianLight.PedestrianLightColor> determinePedestrianLights(Intersection intersection);
    boolean isCarMoveAllowed(Car car);
}
