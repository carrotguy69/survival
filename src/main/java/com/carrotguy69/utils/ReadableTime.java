package com.carrotguy69.utils;

public class ReadableTime {
    public static String convert(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Seconds must be a non-negative value.");
        }

        int weeks = seconds / (7 * 24 * 60 * 60);
        seconds %= (7 * 24 * 60 * 60);

        int days = seconds / (24 * 60 * 60);
        seconds %= (24 * 60 * 60);

        int hours = seconds / (60 * 60);
        seconds %= (60 * 60);

        int minutes = seconds / 60;
        seconds %= 60;

        StringBuilder formattedTime = new StringBuilder();

        if (weeks > 0) {
            formattedTime.append(weeks).append(" week").append(weeks > 1 ? "s" : "").append(" ");
        }

        if (days > 0) {
            formattedTime.append(days).append(" day").append(days > 1 ? "s" : "").append(" ");
        }

        if (hours > 0) {
            formattedTime.append(hours).append(" hour").append(hours > 1 ? "s" : "").append(" ");
        }

        if (minutes > 0) {
            formattedTime.append(minutes).append(" minute").append(minutes > 1 ? "s" : "").append(" ");
        }

        if (seconds > 0) {
            formattedTime.append(seconds).append(" second").append(seconds > 1 ? "s" : "").append(" ");
        }

        return formattedTime.toString().trim();
    }
}
