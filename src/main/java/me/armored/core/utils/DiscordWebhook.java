package me.armored.core.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static me.armored.core.utils.JsonUtils.toJson;

public class DiscordWebhook {

    public static void sendMessage(String content) throws IOException {
        URL url = new URL("https://discord.com/api/webhooks/1114826398511681646/cSM80PiHGxaCYrk2hYOy0jquiYGILYC4CzzBKmrqqCktAyj9gWEiWctH-C74gr0WtYFS");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        Map<String, String> jsonPayload = new HashMap<>();
        jsonPayload.put("content", content);

        String json = toJson(jsonPayload);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("Message sent successfully!");
        } else {
            System.out.println("Failed to send message. Response code: " + responseCode);
        }

        connection.disconnect();
    }

    public static void sendEmbedMessage(String title, String description, String color) throws IOException {
        URL url = new URL("https://discord.com/api/webhooks/1114826398511681646/cSM80PiHGxaCYrk2hYOy0jquiYGILYC4CzzBKmrqqCktAyj9gWEiWctH-C74gr0WtYFS");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", title);
        embed.put("description", description);

        // Parse the color string to an integer
        int colorInt = Integer.parseInt(color.replace("#", ""), 16);
        embed.put("color", colorInt);

        Map<String, Object> jsonPayload = new HashMap<>();
        jsonPayload.put("embeds", new Object[]{embed});

        String json = toJson(jsonPayload);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("Embedded message sent successfully!");
        } else {
            System.out.println("Failed to send embedded message. Response code: " + responseCode);
        }

        connection.disconnect();
    }

    // image
    public static void sendEmbedMessage(String title, String description, String color, String image) throws IOException {
        URL url = new URL("https://discord.com/api/webhooks/1114826398511681646/cSM80PiHGxaCYrk2hYOy0jquiYGILYC4CzzBKmrqqCktAyj9gWEiWctH-C74gr0WtYFS");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", title);
        embed.put("description", description);
        embed.put("image", image);

        // Parse the color string to an integer
        int colorInt = Integer.parseInt(color.replace("#", ""), 16);
        embed.put("color", colorInt);

        Map<String, Object> jsonPayload = new HashMap<>();
        jsonPayload.put("embeds", new Object[]{embed});

        String json = toJson(jsonPayload);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("Embedded message sent successfully!");
        } else {
            System.out.println("Failed to send embedded message. Response code: " + responseCode);
        }

        connection.disconnect();
    }
}