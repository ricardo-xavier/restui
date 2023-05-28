package restui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Caller {
    private HttpResponse<String> httpResponse;
    private long timeMs;

    public void call(String method, String url, String body) {
        try {
            long startTime = System.currentTimeMillis();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(url));
            switch (method) {
                case "GET":
                    requestBuilder.GET();
                    break;
                case "POST":
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
                    break;
            }
            HttpRequest request = requestBuilder.build();
            HttpClient client = HttpClient.newBuilder().build();
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            timeMs = System.currentTimeMillis() - startTime;
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getBody() {
        String json = httpResponse.body();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(json);
        return gson.toJson(jsonElement);
    }

    public int getStatusCode() {
        return httpResponse.statusCode();
    }

    public HttpHeaders getHeaders() {
        return httpResponse.headers();
    }

    public long getTimeMs() {
        return timeMs;
    }
}
