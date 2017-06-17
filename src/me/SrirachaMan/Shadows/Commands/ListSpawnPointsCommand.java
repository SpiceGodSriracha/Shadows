package me.SrirachaMan.Shadows.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.SrirachaMan.Shadows.DataManager;
import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.gameMap;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class ListSpawnPointsCommand extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			if(args.length == 0){
				p.sendMessage(ChatUtil.format("Shadows >> /shadows listSpawnPoints <map>"));
			}else{
			
			gameMap game = Shadows.getInstance().getMap(args[0]);
			
			if(game == null){
				p.sendMessage(ChatUtil.format("That game doesnt exist"));
				return;
			}
			
			List<String> temp = DataManager.getInstance().getGameInfo().getStringList("maps." + game.getName() + ".spawnPoints");
				p.sendMessage(ChatUtil.format("Spawn Points for " + game.getName() + " ----------------"));
				for(int x = 0; x <temp.size(); x ++){
					p.sendMessage(ChatUtil.format("Spawn point [" + x + "] = " + temp.get(x)));
			}
		}
			
		
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
	

}