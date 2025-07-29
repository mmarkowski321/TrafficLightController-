package pl.projekt.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Road {
    private final String id;
    private TrafficLight trafficLight;
    private PedestrianLight pedestrianLight;
    private Location location;
    private Queue<Car> cars;
    private ArrayList<Pedestrian> pedestrians;

    public Road(String id, Location location, TrafficLight trafficLight, PedestrianLight pedestrianLight) {
        this.id = id;
        this.location = location;
        this.trafficLight = trafficLight;
        this.pedestrianLight = pedestrianLight;
        this.cars = new LinkedList<>();
        this.pedestrians = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    public PedestrianLight getPedestrianLight() {
        return pedestrianLight;
    }

    public void setPedestrianLight(PedestrianLight pedestrianLight) {
        this.pedestrianLight = pedestrianLight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Queue<Car> getCars() {
        return cars;
    }

    public void setCars(Queue<Car> cars) {
        this.cars = cars;
    }

    public ArrayList<Pedestrian> getPedestrians() {
        return pedestrians;
    }

    public void setPedestrians(ArrayList<Pedestrian> pedestrians) {
        this.pedestrians = pedestrians;
    }

    public void addCar(Car car) {
        cars.offer(car);
    }

    public Car peekFirstCar() {
        return cars.peek();
    }

    public Car getFirstCar() {
        return cars.poll();
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public boolean hasCars() {
        return !cars.isEmpty();
    }

    public int getCarCount() {
        return cars.size();
    }

    public void clearCars() {
        cars.clear();
    }

    public void addPedestrian(Pedestrian pedestrian) {
        pedestrians.add(pedestrian);
    }

    public void removePedestrian(Pedestrian pedestrian) {
        pedestrians.remove(pedestrian);
    }

    public boolean hasPedestrians() {
        return !pedestrians.isEmpty();
    }

    public int getPedestrianCount() {
        return pedestrians.size();
    }

    public void clearPedestrians() {
        pedestrians.clear();
    }
}
