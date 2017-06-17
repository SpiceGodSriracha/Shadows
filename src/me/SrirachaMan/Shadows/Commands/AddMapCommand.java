package me.SrirachaMan.Shadows.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.SrirachaMan.Shadows.DataManager;
import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.gameMap;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class AddMapCommand extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			if(args.length == 0){
				p.sendMessage(ChatUtil.format("Shadows >> /shadows addmap <MapName>"));
			}else{
			
			gameMap map = Shadows.getInstance().getMap(args[0]);
			if(map != null){
				p.sendMessage(ChatUtil.format("A map of that name already exists!"));
				return;
			}

			
			try{
				FileConfiguration fc = DataManager.getInstance().getGameInfo();
				fc.set("maps." + args[0] + ".mapName", args[0]);			
				List<String> temp1 = new ArrayList<>();
				fc.set("maps." + args[0] + ".spawnPoints", temp1);
				String temp = "X:" + p.getLocation().getBlockX() + ", Y:" + p.getLocation().getBlockY() + ", Z:" + p.getLocation().getBlockZ();
				fc.set("maps." + args[0] + ".worldName", args[0]);
				fc.set("maps." + args[0] + ".stalkerSpawn", temp);
				fc.set("maps." + args[0] + ".description", "default");
				fc.set("maps." + args[0] + ".voteItemId", "WOOL");
				DataManager.getInstance().saveGameInfo();
				
				p.sendMessage(ChatUtil.format("New Map " + args[0] + " created!"));
				}catch(Exception e){
					Shadows.getInstance().getLogger().severe("Could not create game!");
					e.printStackTrace();
				}
			map = new gameMap(args[0]);
			Shadows.getInstance().registerMap(map);

		}
			
		
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
	

}