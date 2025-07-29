package pl.projekt.models;

public class Pedestrian extends MovingObject{
    private PedestrianDestination destination;
    private PedestrianLocation location;
    private final int travelDurationSec = 10;

    public Pedestrian(String id, State state, PedestrianDestination destination, PedestrianLocation location) {
        super(id, state);
        this.destination = destination;
        this.location = location;
    }

    public enum PedestrianDestination {
        NORTH_EAST,
        SOUTH_EAST,
        SOUTH_WEST,
        NORTH_WEST;
    }

    public enum PedestrianLocation {
        NORTH_EAST,
        SOUTH_EAST,
        SOUTH_WEST,
        NORTH_WEST;
    }

    public PedestrianDestination getDestination() {
        return destination;
    }

    public void setDestination(PedestrianDestination destination) {
        this.destination = destination;
    }

    public PedestrianLocation getLocation() {
        return location;
    }

    public void setLocation(PedestrianLocation location) {
        this.location = location;
    }

    public int getTravelDurationSec() {
        return travelDurationSec;
    }
}
