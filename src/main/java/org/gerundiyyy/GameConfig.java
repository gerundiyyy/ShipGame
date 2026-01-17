package org.gerundiyyy;

import java.io.InputStream;
import java.util.Properties;

public class GameConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = GameConfig.class
                .getResourceAsStream("/config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties не найден!");
            }

            props.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки конфига", e);
        }
    }

    //Ship
    public static int getShipCoordX() {
        return getInt("shipmotion.coordx", 0);
    }

    public static int getShipCoordY() {
        return getInt("shipmotion.coordy", 0);
    }

    public static int getShipSpeedX() {
        return getInt("shipmotion.speedx", 0);
    }

    public static int getShipSpeedY() {
        return getInt("shipmotion.speedy", 0);
    }

    //Ball
    public static int getBallSpeedX() {
        return getInt("ballmotion.speedx", 0);
    }

    public static int getBallSpeedY() {
        return getInt("ballmotion.speedy", 0);
    }

    public static int getBallTicksToDelete() {
        return getInt("ballmotion.tickstodelete", 200);
    }

    private static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            System.err.println("Ошибка чтения параметра: " + key);
            return defaultValue;
        }
    }

    private static double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(props.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            System.err.println("Ошибка чтения параметра: " + key);
            return defaultValue;
        }
    }

    private static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(props.getProperty(key, String.valueOf(defaultValue)));
    }

    private static String getString(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}