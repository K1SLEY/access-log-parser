class UserAgent {
    private final String os;
    private final String browser;
    private String rawUserAgent;

    public UserAgent(String userAgentString) {

        if (userAgentString.contains("Windows")) this.os = "Windows";
        else if (userAgentString.contains("Mac OS")) this.os = "macOS";
        else if (userAgentString.contains("Linux")) this.os = "Linux";
        else this.os = "Other";

        if (userAgentString.contains("Edg")) this.browser = "Edge";
        else if (userAgentString.contains("Firefox")) this.browser = "Firefox";
        else if (userAgentString.contains("Chrome")) this.browser = "Chrome";
        else if (userAgentString.contains("Opera")) this.browser = "Opera";
        else this.browser = "Other";

        this.rawUserAgent = (userAgentString == null || userAgentString.isEmpty()) ? "" : userAgentString.toLowerCase();
    }


    public boolean isBot() {
        return this.rawUserAgent.contains("bot");
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }
}
