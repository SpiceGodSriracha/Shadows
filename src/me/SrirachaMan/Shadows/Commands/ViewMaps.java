package me.SrirachaMan.Shadows.Commands;

import org.bukkit.command.CommandSender;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.gameMap;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class ViewMaps extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		for(gameMap game: Shadows.getInstance().getMaps()){
			s.sendMessage(ChatUtil.format("Shadows >> " +  game.getName()));
		}
	}

}
