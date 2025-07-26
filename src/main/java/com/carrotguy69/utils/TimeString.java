package com.carrotguy69.utils;

public class TimeString {

    public static int convert(String timeString) {
        if (timeString == null || timeString.isEmpty()) {
            throw new IllegalArgumentException("Time string cannot be null or empty.");
        }

        int seconds;
        char unit = timeString.charAt(timeString.length() - 1);

        try {
            int value = Integer.parseInt(timeString.substring(0, timeString.length() - 1));

            switch (unit) {
                case 's':
                    seconds = value;
                    break;
                case 'm':
                    seconds = value * 60;
                    break;
                case 'h':
                    seconds = value * 60 * 60;
                    break;
                case 'd':
                    seconds = value * 24 * 60 * 60;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid unit in time string.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format of time string.", e);
        }

        return seconds;
    }


}