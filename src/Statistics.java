import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;

class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final HashSet<String> existingPages = new HashSet<>();
    private final HashMap<String, Integer> osStats = new HashMap<>();


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
        if (entry.getResponseCode() == 200) {
            existingPages.add(entry.getPath());
        }
        String os = entry.getUserAgent().getOs();
        osStats.put(os, osStats.getOrDefault(os, 0) + 1);
    }

    public HashSet<String> getExistingPages() {
        return new HashSet<>(existingPages);
    }

    public HashMap<String, Double> getOsStatistics() {
        HashMap<String, Double> result = new HashMap<>();
        int totalRequests = osStats.values().stream().mapToInt(Integer::intValue).sum();

        if (totalRequests == 0) return result;

        double totalRequestsDouble = (double) totalRequests;
        osStats.forEach((os, count) -> result.put(os, (double) count / totalRequestsDouble));
        return result;
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null || totalTraffic == 0) return 0;

        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) return totalTraffic;

        return (double) totalTraffic / hours;
    }


}