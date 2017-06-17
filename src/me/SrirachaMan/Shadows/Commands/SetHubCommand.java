package me.SrirachaMan.Shadows.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class SetHubCommand extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			 Shadows.getInstance().getConfig().set("lobby-point.x", p.getLocation().getBlockX());
			 Shadows.getInstance().getConfig().set("lobby-point.y", p.getLocation().getBlockY());
			 Shadows.getInstance().getConfig().set("lobby-point.z", p.getLocation().getBlockZ());
			 Shadows.getInstance().getConfig().set("lobby-point.world", p.getLocation().getWorld().getName());
			 Shadows.getInstance().saveConfig();
			 s.sendMessage(ChatUtil.format("Hub Set"));
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
}
