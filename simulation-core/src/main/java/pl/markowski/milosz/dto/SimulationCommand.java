package pl.markowski.milosz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimulationCommand {
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("vehicleId")
    private String vehicleId;
    
    @JsonProperty("startRoad")
    private String startRoad;
    
    @JsonProperty("endRoad")
    private String endRoad;

    public SimulationCommand() {}

    public SimulationCommand(String type) {
        this.type = type;
    }

    public SimulationCommand(String type, String vehicleId, String startRoad, String endRoad) {
        this.type = type;
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStartRoad() {
        return startRoad;
    }

    public void setStartRoad(String startRoad) {
        this.startRoad = startRoad;
    }

    public String getEndRoad() {
        return endRoad;
    }

    public void setEndRoad(String endRoad) {
        this.endRoad = endRoad;
    }
} 