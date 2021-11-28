package com.battleship.game.colorpack;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorPack {

    public static Color enemyHitColor = Color.RED;
    public static Color playerHitColor = Color.BLACK;
    public static Color playerTurnColor = Color.BLUE;

    private static Map<String, Color> colorMap  = new HashMap<>() {{
        put("Black", Color.BLACK);
        put("Blue", Color.BLUE);
        put("Cyan", Color.CYAN);
        put("Green", Color.GREEN);
        put("Magenta", Color.MAGENTA);
        put("Red", Color.RED);
        put("Orange", Color.ORANGE);
        put("Pink", Color.PINK);
        put("Yellow", Color.YELLOW);
    }};

    public static Map<String, Color> getAllColors() {
        return colorMap;
    }

    public static void setEnemyHitColor(String colorKey) {
        enemyHitColor = colorMap.getOrDefault(colorKey, Color.RED);
    }

    public static void setPlayerHitColor(String colorKey) {
        playerHitColor = colorMap.getOrDefault(colorKey, Color.BLACK);
    }

    public static void setPlayerTurnColor(String colorKey) {
        playerTurnColor = colorMap.getOrDefault(colorKey, Color.BLUE);
    }

}
