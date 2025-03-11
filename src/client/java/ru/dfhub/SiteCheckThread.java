package ru.dfhub;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SiteCheckThread extends Thread{

    @Override
    public void run() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleWithFixedDelay(() -> {
            JSONArray sites = Config.getConfig().getJSONArray("sites");
            for (int i = 0; i < sites.length(); i++) {
                JSONObject site = sites.getJSONObject(i);
                if (!check(site.getString("url"))) {
                    // TODO: send notification
                    //MCUptime.LOGGER.error("Site %s is down!".formatted(site.getString("id")));
                } else {
                    //MCUptime.LOGGER.info("Site %s works!".formatted(site.getString("id")));
                }
            }
        }, 0, Config.getConfig().getInt("cooldown"), TimeUnit.SECONDS);
    }

    private static boolean check(String url) {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> res = client.send(HttpRequest
                    .newBuilder()
                    .HEAD()
                    .uri(URI.create(url))
                    .build()
            , HttpResponse.BodyHandlers.ofString());
            return res.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
