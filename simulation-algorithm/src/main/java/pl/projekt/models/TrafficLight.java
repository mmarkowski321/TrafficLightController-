package pl.projekt.models;

public class TrafficLight extends Light{
    private TrafficLightColor color;

    public TrafficLight(String id, Location location, TrafficLightColor color) {
        super(id, location);
        this.color = color;
    }

    public enum TrafficLightColor {
        GREEN,
        YELLOW,
        RED;
    }

    public TrafficLightColor getColor() {
        return color;
    }

    public void setColor(TrafficLightColor color) {
        this.color = color;
    }
}
