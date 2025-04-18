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
    private final HashSet<String> notFoundPages = new HashSet<>();
    private final HashMap<String, Integer> browserStats = new HashMap<>();
    private int validVisits = 0;
    private int errorRequests = 0;
    private final HashSet<String> uniqueValidIPs = new HashSet<>();

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
        if (entry.getResponseCode() == 404)
            notFoundPages.add(entry.getPath());

        if (entry.getResponseCode() == 200) {
            existingPages.add(entry.getPath());
        }
        String os = entry.getUserAgent().getOs();
        osStats.put(os, osStats.getOrDefault(os, 0) + 1);

        String browser = entry.getUserAgent().getBrowser();
        browserStats.put(browser, browserStats.getOrDefault(browser, 0) + 1);
        processAdvancedMetrics(entry);
    }

    private void processAdvancedMetrics(LogEntry entry) {
        boolean isBot = entry.getUserAgent().isBot();

        if (entry.getResponseCode() >= 400 && entry.getResponseCode() < 600) {
            errorRequests++;
        }
        if (!isBot) {
            validVisits++;
            uniqueValidIPs.add(entry.getIp());
        }
    }

    public double getAverageVisitsPerHour() {
        if (minTime == null || maxTime == null || validVisits == 0) return 0.0;

        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        return hours > 0 ? (double) validVisits / hours : validVisits;
    }

    public double getAverageErrorsPerHour() {
        if (minTime == null || maxTime == null || errorRequests == 0) return 0.0;

        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        return hours > 0 ? (double) errorRequests / hours : errorRequests;
    }

    public double getAverageVisitsPerUser() {
        if (uniqueValidIPs.isEmpty()) return 0.0;
        return (double) validVisits / uniqueValidIPs.size();
    }

    public HashSet<String> getExistingPages() {
        return new HashSet<>(existingPages);
    }

    public HashSet<String> getNotFoundPages() {
        return new HashSet<>(notFoundPages);
    }

    public HashMap<String, Double> getBrowserStats() {
        HashMap<String, Double> resultBrowser = new HashMap<>();
        int totalRequests = browserStats.values().stream().mapToInt(Integer::intValue).sum();
        if (totalRequests == 0) return resultBrowser;
        double totalRequestsDouble = (double) totalRequests;
        browserStats.forEach((browser, count) -> resultBrowser.put(browser, (double) count / totalRequestsDouble));
        return resultBrowser;
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