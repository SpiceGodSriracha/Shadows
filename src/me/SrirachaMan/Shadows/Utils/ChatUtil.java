package me.SrirachaMan.Shadows.Utils;

import org.bukkit.ChatColor;

public class ChatUtil {
	public static String format(String msg){
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
