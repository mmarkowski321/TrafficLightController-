package pl.markowski.milosz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SimulationResponse {
    @JsonProperty("stepStatuses")
    private List<StepStatus> stepStatuses;

    public SimulationResponse() {}

    public SimulationResponse(List<StepStatus> stepStatuses) {
        this.stepStatuses = stepStatuses;
    }

    public List<StepStatus> getStepStatuses() {
        return stepStatuses;
    }

    public void setStepStatuses(List<StepStatus> stepStatuses) {
        this.stepStatuses = stepStatuses;
    }
} 