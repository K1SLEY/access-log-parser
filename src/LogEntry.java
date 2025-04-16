import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


enum HttpMethod {
    GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH, CONNECT, TRACE
}

class LogEntry {
    private final String ip;
    private final LocalDateTime dateTime;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int dataSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String logLine) {
        String[] parts = logLine.split("\"");

        this.ip = logLine.split(" ")[0];


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        String dateTimeString = logLine.split("\\[")[1].split("]")[0];
        this.dateTime = LocalDateTime.parse(dateTimeString, formatter);

        String[] requestParts = parts[1].split(" ");
        this.method = HttpMethod.valueOf(requestParts[0]);
        this.path = requestParts[1];

        this.responseCode = Integer.parseInt(parts[2].trim().split(" ")[0]);
        this.dataSize = parts[2].contains("-") ? 0 : Integer.parseInt(parts[2].trim().split(" ")[1]);

        this.referer = parts[3].trim();
        this.userAgent = new UserAgent(parts[5].trim());
    }


    public String getIp() {
        return ip;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getDataSize() {
        return dataSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}
