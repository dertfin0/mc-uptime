package ru.dfhub;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SiteCheckThread extends Thread{

    @Override
    public void run() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleWithFixedDelay(() -> {
            JSONArray sites = Config.getConfig().getJSONArray("sites");
            List<String> sitesDown = new ArrayList<>();

            for (int i = 0; i < sites.length(); i++) {
                JSONObject site = sites.getJSONObject(i);
                if (!check(site.getString("url"))) {
                    sitesDown.add(site.getString("id"));
                }
            }

            if (!sitesDown.isEmpty()) {
                GameNotifier.notify(String.join("§c, §b", sitesDown));
            } else {
                GameNotifier.statusOk();
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
