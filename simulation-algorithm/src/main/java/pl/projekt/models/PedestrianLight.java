package pl.projekt.models;

public class PedestrianLight extends Light {
    private PedestrianLightColor color;

    public PedestrianLight(String id, Location location, PedestrianLightColor color) {
        super(id, location);
        this.color = color;
    }

    public enum PedestrianLightColor {
        GREEN,
        RED;
    }

    public PedestrianLightColor getColor() {
        return color;
    }

    public void setColor(PedestrianLightColor color) {
        this.color = color;
    }
}
