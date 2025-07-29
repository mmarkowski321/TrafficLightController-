package pl.projekt.models;

abstract class Light {
    private final String id;
    private Location location;

    public Light(String id, Location carLocation) {
        this.id = id;
        this.location = carLocation;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location carLocation) {
        this.location = carLocation;
    }
}
