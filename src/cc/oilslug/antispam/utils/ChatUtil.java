package cc.oilslug.antispam.utils;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String colour(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static boolean isCommonWord(String word){
        return word.length() <= 3 || word.equalsIgnoreCase("like");
    }
}
