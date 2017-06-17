package me.SrirachaMan.Shadows.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class AddRareItem extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			if(args.length == 0){
				p.sendMessage(ChatUtil.format("Shadows >> /shadows setRareItems <game>"));
			}else{
			
			Game game = Shadows.getInstance().getGame(args[0]);
			
			if(game == null){
				p.sendMessage(ChatUtil.format("That game doesnt exist"));
				return;
			}
			game.setRareItems(p.getInventory());
			p.sendMessage(ChatUtil.format("Rare rewards for " + game.getDisplayName() + " updated!"));
		}
			
		
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
}
