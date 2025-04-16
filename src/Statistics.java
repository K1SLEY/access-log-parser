import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public void addEntry(LogEntry entry) {
        if (entry.getDataSize() > 0) {
            totalTraffic += entry.getDataSize();
        }

        if (minTime == null || entry.getDateTime().isBefore(minTime)) {
            minTime = entry.getDateTime();
        }

        if (maxTime == null || entry.getDateTime().isAfter(maxTime)) {
            maxTime = entry.getDateTime();
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null || totalTraffic == 0) return 0;

        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) return totalTraffic;

        return (double) totalTraffic / hours;
    }
}