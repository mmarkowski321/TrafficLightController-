package pl.projekt;

import pl.projekt.algorithm.controller.TrafficLightControllerImpl;
import pl.projekt.algorithm.simulator.IntersectionSimulator;
import pl.projekt.algorithm.state.SimulationStepResult;
import pl.projekt.models.*;

import java.util.*;

public class Main {
    private static final String RESET  = "\u001B[0m";
    private static final String RED    = "\u001B[31m";
    private static final String GREEN  = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        Road north = new Road("R1", Location.NORTH,
                new TrafficLight("TL1", Location.NORTH, TrafficLight.TrafficLightColor.RED),
                new PedestrianLight("PL1", Location.NORTH, PedestrianLight.PedestrianLightColor.RED));
        Road east  = new Road("R2", Location.EAST,
                new TrafficLight("TL2", Location.EAST, TrafficLight.TrafficLightColor.RED),
                new PedestrianLight("PL2", Location.EAST, PedestrianLight.PedestrianLightColor.RED));
        Road south = new Road("R3", Location.SOUTH,
                new TrafficLight("TL3", Location.SOUTH, TrafficLight.TrafficLightColor.RED),
                new PedestrianLight("PL3", Location.SOUTH, PedestrianLight.PedestrianLightColor.RED));
        Road west  = new Road("R4", Location.WEST,
                new TrafficLight("TL4", Location.WEST, TrafficLight.TrafficLightColor.RED),
                new PedestrianLight("PL4", Location.WEST, PedestrianLight.PedestrianLightColor.RED));

        Intersection intersection = new Intersection(new Road[]{north, east, south, west});
        IntersectionSimulator sim = new IntersectionSimulator(intersection, new TrafficLightControllerImpl());

        Random rnd = new Random();
        Map<Location, List<Pedestrian.PedestrianLocation>> pedMap = Map.of(
                Location.NORTH, List.of(Pedestrian.PedestrianLocation.NORTH_EAST, Pedestrian.PedestrianLocation.NORTH_WEST),
                Location.EAST,  List.of(Pedestrian.PedestrianLocation.NORTH_EAST, Pedestrian.PedestrianLocation.SOUTH_EAST),
                Location.SOUTH, List.of(Pedestrian.PedestrianLocation.SOUTH_EAST, Pedestrian.PedestrianLocation.SOUTH_WEST),
                Location.WEST,  List.of(Pedestrian.PedestrianLocation.NORTH_WEST, Pedestrian.PedestrianLocation.SOUTH_WEST)
        );

        for (int step = 1; step <= 20; step++) {
            System.out.println("\n===== KROK " + step + " =====\n");

            for (Road r : intersection.getRoads()) {
                if (rnd.nextDouble() < 0.3) {
                    String id = "car" + step + "_" + r.getLocation();
                    Car.CarDirection dir = randomDir(r.getLocation(), rnd);
                    r.addCar(new Car(id, MovingObject.State.WAITING, dir, r.getLocation()));
                }
                if (rnd.nextDouble() < 0.2) {
                    String id = "ped" + step + "_" + r.getLocation();
                    var locs = pedMap.get(r.getLocation());
                    var start = locs.get(rnd.nextInt(locs.size()));
                    Pedestrian.PedestrianDestination dest;
                    do {
                        dest = Pedestrian.PedestrianDestination.values()[rnd.nextInt(Pedestrian.PedestrianDestination.values().length)];
                    } while (dest.name().equals(start.name()));
                    r.addPedestrian(new Pedestrian(id, MovingObject.State.WAITING, dest, start));
                }
            }

            System.out.println("🚦  ŚWIATŁA DROGOWE PRZED KROKIEM:");
            System.out.println("🚦  ŚWIATŁA DROGOWE PRZED KROKIEM:");
            for (Road r : intersection.getRoads()) {
                var c = r.getTrafficLight().getColor();
                String col = switch (c) {
                    case GREEN  -> GREEN  + "🟢 GREEN"  + RESET;
                    case YELLOW -> YELLOW + "🟡 YELLOW" + RESET;
                    default     -> RED    + "🔴 RED"    + RESET;
                };
                System.out.printf("   %-5s → %s%n", r.getLocation(), col);
            }

            System.out.println("\n🚶  ŚWIATŁA DLA PIESZYCH PRZED KROKIEM:");
            for (Road r : intersection.getRoads()) {
                var p = r.getPedestrianLight().getColor();
                String col = (p == PedestrianLight.PedestrianLightColor.GREEN
                        ? GREEN + "🟢 GREEN" + RESET
                        : RED   + "🔴 RED"   + RESET);
                System.out.printf("   %-5s → %s%n", r.getLocation(), col);
            }

            System.out.println("\n⏳  KOLEJKI PRZED KROKIEM:");
            for (Road r : intersection.getRoads()) {
                String cars = r.getCars().stream()
                        .map(c -> String.format("🚗%s(%s→%s)", c.getId(), c.getLocation(), c.getDirection()))
                        .reduce((a,b)->a+" "+b).orElse("—");
                String peds = r.getPedestrians().stream()
                        .map(p -> String.format("🚶%s(%s→%s)", p.getId(), p.getLocation(), p.getDestination()))
                        .reduce((a,b)->a+" "+b).orElse("—");
                System.out.printf("   %-5s → auta[%2d]: %s%n", r.getLocation(), r.getCarCount(), cars);
                System.out.printf("         piesi[%2d]: %s%n", r.getPedestrianCount(), peds);
            }

            SimulationStepResult result = sim.step();

            System.out.println("\n→ Opuściły auta: " + result.getLeftCars());
            System.out.println("\n→ Opuściły auta: " + result.getLeftCars());
            System.out.println("→ Opuściły piesi: " + result.getLeftPedestrians());
        }
    }

    private static Car.CarDirection randomDir(Location start, Random rnd) {
        var all = Car.CarDirection.values();
        Car.CarDirection d;
        do { d = all[rnd.nextInt(all.length)]; }
        while (d.name().equals(start.name()));
        return d;
    }
}
