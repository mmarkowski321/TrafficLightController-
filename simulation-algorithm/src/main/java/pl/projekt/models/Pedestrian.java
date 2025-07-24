package pl.projekt.models;

public class Pedestrian {
    private final String id;
    private State state = State.WAITING;
    private Direction direction;
    private Location location;
    private final int travelDurationSec = 5;

    public Pedestrian(String id, State state, Direction direction, Location location) {
        this.id = id;
        this.state = state;
        this.direction = direction;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
