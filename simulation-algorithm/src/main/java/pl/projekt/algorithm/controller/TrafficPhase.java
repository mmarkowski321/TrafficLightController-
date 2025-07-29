package pl.projekt.algorithm.controller;

import pl.projekt.models.Location;
import pl.projekt.models.TrafficLight;
import pl.projekt.models.PedestrianLight;

import java.util.EnumMap;
import java.util.Map;

public enum TrafficPhase {
    PHASE1(
            "north_road -> east_road, south_road -> west_road",
            Map.of(
                    Location.NORTH, TrafficLight.TrafficLightColor.GREEN,
                    Location.EAST,  TrafficLight.TrafficLightColor.RED,
                    Location.WEST,  TrafficLight.TrafficLightColor.RED,
                    Location.SOUTH, TrafficLight.TrafficLightColor.GREEN
            ),
            "brak",
            Map.of(
                    Location.NORTH, PedestrianLight.PedestrianLightColor.RED,
                    Location.EAST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.WEST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.SOUTH, PedestrianLight.PedestrianLightColor.RED
            )
    ),
    PHASE2(
            "west_road -> north_road, east_road -> south_road",
            Map.of(
                    Location.NORTH, TrafficLight.TrafficLightColor.RED,
                    Location.EAST,  TrafficLight.TrafficLightColor.GREEN,
                    Location.WEST,  TrafficLight.TrafficLightColor.GREEN,
                    Location.SOUTH, TrafficLight.TrafficLightColor.RED
            ),
            "brak",
            Map.of(
                    Location.NORTH, PedestrianLight.PedestrianLightColor.RED,
                    Location.EAST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.WEST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.SOUTH, PedestrianLight.PedestrianLightColor.RED
            )
    ),
    PHASE3(
            "north_road -> south_road, south_road -> north_road",
            Map.of(
                    Location.NORTH, TrafficLight.TrafficLightColor.GREEN,
                    Location.EAST,  TrafficLight.TrafficLightColor.RED,
                    Location.WEST,  TrafficLight.TrafficLightColor.RED,
                    Location.SOUTH, TrafficLight.TrafficLightColor.GREEN
            ),
            "north_east -> south_east, south_east -> north_east, north_west -> south_west, south_west -> north_west",
            Map.of(
                    Location.NORTH, PedestrianLight.PedestrianLightColor.RED,
                    Location.EAST,  PedestrianLight.PedestrianLightColor.GREEN,
                    Location.WEST,  PedestrianLight.PedestrianLightColor.GREEN,
                    Location.SOUTH, PedestrianLight.PedestrianLightColor.RED
            )
    ),
    PHASE4(
            "west_road -> east_road, east_road -> west_road",
            Map.of(
                    Location.NORTH, TrafficLight.TrafficLightColor.RED,
                    Location.EAST,  TrafficLight.TrafficLightColor.GREEN,
                    Location.WEST,  TrafficLight.TrafficLightColor.GREEN,
                    Location.SOUTH, TrafficLight.TrafficLightColor.RED
            ),
            "north_west -> north_east, north_east -> north_west, south_west -> south_east, south_east -> south_west",
            Map.of(
                    Location.NORTH, PedestrianLight.PedestrianLightColor.GREEN,
                    Location.EAST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.WEST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.SOUTH, PedestrianLight.PedestrianLightColor.GREEN
            )
    ),
    PHASE5(
            "north_road -> west_road, south_road -> east_road",
            Map.of(
                    Location.NORTH, TrafficLight.TrafficLightColor.GREEN,
                    Location.EAST,  TrafficLight.TrafficLightColor.RED,
                    Location.WEST,  TrafficLight.TrafficLightColor.RED,
                    Location.SOUTH, TrafficLight.TrafficLightColor.GREEN
            ),
            "brak",
            Map.of(
                    Location.NORTH, PedestrianLight.PedestrianLightColor.RED,
                    Location.EAST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.WEST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.SOUTH, PedestrianLight.PedestrianLightColor.RED
            )
    ),
    PHASE6(
            "west_road -> south_road, east_road -> north_road",
            Map.of(
                    Location.NORTH, TrafficLight.TrafficLightColor.RED,
                    Location.EAST,  TrafficLight.TrafficLightColor.GREEN,
                    Location.WEST,  TrafficLight.TrafficLightColor.GREEN,
                    Location.SOUTH, TrafficLight.TrafficLightColor.RED
            ),
            "brak",
            Map.of(
                    Location.NORTH, PedestrianLight.PedestrianLightColor.RED,
                    Location.EAST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.WEST,  PedestrianLight.PedestrianLightColor.RED,
                    Location.SOUTH, PedestrianLight.PedestrianLightColor.RED
            )
    );

    public final String carDesc;
    public final EnumMap<Location, TrafficLight.TrafficLightColor> carLights;
    public final String pedDesc;
    public final EnumMap<Location, PedestrianLight.PedestrianLightColor> pedLights;

    TrafficPhase(String carDesc,
                 Map<Location, TrafficLight.TrafficLightColor> carLights,
                 String pedDesc,
                 Map<Location, PedestrianLight.PedestrianLightColor> pedLights) {
        this.carDesc  = carDesc;
        this.carLights = new EnumMap<>(carLights);
        this.pedDesc  = pedDesc;
        this.pedLights = new EnumMap<>(pedLights);
    }

    public static TrafficPhase of(int phase) {
        return values()[phase - 1];
    }
}
