package pl.markowski.milosz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SimulationRequest {
    @JsonProperty("commands")
    private List<SimulationCommand> commands;

    public SimulationRequest() {}

    public SimulationRequest(List<SimulationCommand> commands) {
        this.commands = commands;
    }

    public List<SimulationCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<SimulationCommand> commands) {
        this.commands = commands;
    }
} 