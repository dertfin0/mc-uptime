package ru.dfhub;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SiteCheckThread extends Thread{

    @Override
    public void run() {
        while (true) {
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

            try {
                Thread.sleep(Config.getConfig().optInt("cooldown", 60) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
