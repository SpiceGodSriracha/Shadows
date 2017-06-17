package me.SrirachaMan.Shadows.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.SrirachaMan.Shadows.DataManager;
import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.gameMap;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class RemoveSpawnPointCommand extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			if(args.length == 0){
				p.sendMessage(ChatUtil.format("Shadows >> /shadows removeSpawnPoint <game> <id>"));
			}else{
			
			gameMap game = Shadows.getInstance().getMap(args[0]);
			
			if(game == null){
				p.sendMessage(ChatUtil.format("That game doesnt exist"));
				return;
			}
			
			List<String> temp = DataManager.getInstance().getGameInfo().getStringList("map." + game.getName() + ".spawnPoints");
			try{
				temp.remove(args[1]);
			}catch(IndexOutOfBoundsException e){
				p.sendMessage("Invalid Spawn Id! Use /shadows listSpawnPoints <map> to get a list!");
				e.printStackTrace();
			}
			
			DataManager.getInstance().getGameInfo().set("map." + game.getName() + ".spawnPoints", temp);
			DataManager.getInstance().saveGameInfo();
		}
			
		
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
	

}