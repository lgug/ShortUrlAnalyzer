package shorturlanalyzer;

import java.util.Map;

public class UrlInfo {

    private boolean isReachable;
    private boolean hasRedirecting;
    private boolean PageFound;
    private String originalUrl;
    private String finalUrl;
    private Map<Integer, String> intermediateHop; // it always contains the final url as a hop.

    public UrlInfo() {
    }

    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean reachable) {
        isReachable = reachable;
    }

    public boolean hasRedirecting() {
        return hasRedirecting;
    }

    public void setHasRedirecting(boolean hasRedirecting) {
        this.hasRedirecting = hasRedirecting;
    }

    public boolean isPageFound() {
        return PageFound;
    }

    public void setPageFound(boolean pageFound) {
        PageFound = pageFound;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public void setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
    }

    public Map<Integer, String> getIntermediateHop() {
        return intermediateHop;
    }

    public void setIntermediateHop(Map<Integer, String> intermediateHop) {
        this.intermediateHop = intermediateHop;
    }
}
