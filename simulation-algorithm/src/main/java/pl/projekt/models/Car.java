package pl.projekt.models;

public class Car extends MovingObject{
    private CarDirection direction;
    private Location location;
    private final int travelDurationSec = 5;


    public Car(String id, State state, CarDirection direction, Location location) {
        super(id, state);
        this.direction = direction;
        this.location = location;
    }

    public enum CarDirection {
        EAST,
        NORTH,
        SOUTH,
        WEST;
    }

    public CarDirection getDirection() {
        return direction;
    }

    public void setDirection(CarDirection direction) {
        this.direction = direction;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getTravelDurationSec() {
        return travelDurationSec;
    }
}