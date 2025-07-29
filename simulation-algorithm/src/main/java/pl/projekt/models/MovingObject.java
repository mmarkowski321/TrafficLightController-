package pl.projekt.models;

public abstract class MovingObject {
    private final String id;
    private State state;

    public MovingObject(String id, State state) {
        this.id = id;
        this.state = state;
    }

    public enum State {
        WAITING,
        CROSSING,
        FINISHED
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
}
