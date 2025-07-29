package pl.markowski.milosz.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MonitoringService {

    private final AtomicLong totalSimulations = new AtomicLong(0);
    private final AtomicLong totalVehicles = new AtomicLong(0);
    private final AtomicInteger activeSimulations = new AtomicInteger(0);

    public void startSimulation() {
        totalSimulations.incrementAndGet();
        activeSimulations.incrementAndGet();
    }

    public void endSimulation() {
        activeSimulations.decrementAndGet();
    }

    public void addVehicles(int count) {
        totalVehicles.addAndGet(count);
    }

    public SimulationStats getStats() {
        return new SimulationStats(
            totalSimulations.get(),
            totalVehicles.get(),
            activeSimulations.get()
        );
    }

    public static class SimulationStats {
        private final long totalSimulations;
        private final long totalVehicles;
        private final int activeSimulations;

        public SimulationStats(long totalSimulations, long totalVehicles, int activeSimulations) {
            this.totalSimulations = totalSimulations;
            this.totalVehicles = totalVehicles;
            this.activeSimulations = activeSimulations;
        }

        public long getTotalSimulations() {
            return totalSimulations;
        }

        public long getTotalVehicles() {
            return totalVehicles;
        }

        public int getActiveSimulations() {
            return activeSimulations;
        }
    }
} 