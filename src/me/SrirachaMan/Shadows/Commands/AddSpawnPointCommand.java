package me.SrirachaMan.Shadows.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.gameMap;
import me.SrirachaMan.Shadows.Utils.ChatUtil;



public class AddSpawnPointCommand extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			if(args.length == 0){
				p.sendMessage(ChatUtil.format("Shadows >> /shadows addspawn <map>"));
			}else{
			
			gameMap game = Shadows.getInstance().getMap(args[0]);
			
			if(game == null){
				p.sendMessage(ChatUtil.format("That map doesnt exist"));
				return;
			}
			
			game.addSpawnPoint(p.getLocation());
			p.sendMessage(ChatUtil.format("Point added!"));
		}
			
		
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
	

}