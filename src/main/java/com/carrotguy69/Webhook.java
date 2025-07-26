package com.carrotguy69;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;

public class Webhook {
    public static String url = "https://discord.com/api/webhooks/1129624782623744050/MKEPX08GGrXgFZ_GmbIYxX2Wvp7D-zffGVUUrm2j6kxSbAm9qHL4P-KEtbShm9IRcWxC";
    public static WebhookClient client = WebhookClient.withUrl(url);
    public static String staffurl = "https://discord.com/api/webhooks/1129624580785446985/zuwKODmI3eXOhij_jppuOnHAuSdwpRY-ySGJCWVFTG0SXYTEXNcgKMDVqDbO9FU_2Twm";
    public static WebhookClient staffclient = WebhookClient.withUrl(staffurl);

    public static void main(String content) {
        client.send(content);
    }

    public static void main(String title, String description, int color) {
        WebhookEmbed embed = new WebhookEmbedBuilder()
            .setTitle(new WebhookEmbed.EmbedTitle(title, null))
            .setDescription(description)
            .setColor(color)
            .build();

        client.send(embed);
    }

    public static void staff(String content) {
        staffclient.send(content);
    }

    public static void staff(String title, String description, int color) {
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setTitle(new WebhookEmbed.EmbedTitle(title, null))
                .setDescription(description)
                .setColor(color)
                .build();

        staffclient.send(embed);
    }
}
