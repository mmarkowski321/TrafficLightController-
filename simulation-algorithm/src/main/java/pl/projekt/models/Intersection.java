package pl.projekt.models;


public class Intersection {
    private Road[] roads;

    public Intersection(Road[] roads) {
        this.roads = roads;
    }

    public Road[] getRoads() {
        return roads;
    }
}
