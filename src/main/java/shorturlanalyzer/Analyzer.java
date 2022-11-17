package shorturlanalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Analyzer {

    private String url;

    public Analyzer(String url) {
        this.url = url;
    }

    private String getRealDestination(URL u) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestMethod("HEAD");

        httpURLConnection.setRequestProperty("Accept", "*/*");
        httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        httpURLConnection.setRequestProperty("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
//        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpURLConnection.connect();

//        List<String> lines = new ArrayList<>();
//        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

//        String line;
//        while ((line = br.readLine()) != null) {
//            lines.add(line);
//        }
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        httpURLConnection.disconnect();

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode >= 300 && responseCode < 399) {
            AtomicReference<String> location = new AtomicReference<>("Location");
            headerFields.keySet().forEach(s -> {
                if (s != null && s.equalsIgnoreCase("location")) location.set(s);
            });
            if (headerFields.containsKey(location.get()) && headerFields.get(location.get()).size() > 0) {
                return headerFields.get(location.get()).get(0);
            } else {
                throw new NoShortURLException();
            }
        } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new FileNotFoundException();
        } else if (responseCode == HttpURLConnection.HTTP_OK) {
            throw new NoShortURLException();
        }

        return null;
    }

    public UrlInfo getAllInfo() {
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.setOriginalUrl(url);

        try {
            Map<Integer, String> intermediateHops = new HashMap<>();
            int hops = 0;
            URL u = new URL(url);
            String finalUrl = getRealDestination(u);
            hops++;
            intermediateHops.put(hops, finalUrl);
            if (finalUrl != null) {
                URL fu = new URL(finalUrl);
                int count = 0;
                URL current = u;
                while (current != null && count < 7 && (fu.getHost().equals(current.getHost()) &&
                        fu.getPath().equals(current.getPath()) &&
                        fu.getProtocol().equals("https") && current.getProtocol().equals("http"))) {
                    finalUrl = getRealDestination(fu);
                    current = finalUrl != null ? new URL(finalUrl) : null;
                    hops++;
                    intermediateHops.put(hops, finalUrl);
                    count++;
                }

                urlInfo.setReachable(true);
                urlInfo.setHasRedirecting(true);
                urlInfo.setPageFound(true);
                urlInfo.setFinalUrl(finalUrl);
                urlInfo.setIntermediateHop(intermediateHops);
            }
        } catch (FileNotFoundException e1) {
            urlInfo.setReachable(true);
            urlInfo.setPageFound(false);
            urlInfo.setHasRedirecting(false);
        } catch (NoShortURLException e2) {
            urlInfo.setReachable(true);
            urlInfo.setPageFound(true);
            urlInfo.setHasRedirecting(false);
        } catch (SocketTimeoutException | ConnectException e3) {
            urlInfo.setReachable(false);
            urlInfo.setPageFound(false);
            urlInfo.setHasRedirecting(false);
        } catch (IOException e3) {
            return null;
        }


        return urlInfo;
    }
}
