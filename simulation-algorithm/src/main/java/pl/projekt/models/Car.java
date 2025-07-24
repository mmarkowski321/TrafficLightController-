package pl.projekt.models;

public class Car {
    private final String id;
    private State state;
    private Direction direction;
    private Location location;
    private final int travelDurationSec = 10;

    public Car(String id, State state, Direction direction, Location location) {
        this.id = id;
        this.state = state;
        this.direction = direction;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public int getTravelDurationSec() {
        return travelDurationSec;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isFinished() {
        return state == State.FINISHED;
    }

    @Override
    public String toString() {
        return "Car" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", direction=" + direction +
                ", location=" + location +
                ", travelDurationSec=" + travelDurationSec;
    }
}
