package me.SrirachaMan.Shadows.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.SrirachaMan.Shadows.DataManager;
import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class CreateCommand extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			if(args.length == 0){
				p.sendMessage(ChatUtil.format("Shadows >> /shadows create <game name>"));
			}else{
			if(Shadows.getInstance().isSingleServerMode() && Shadows.getInstance().getGames().size() > 1){
				p.sendMessage(ChatUtil.format("Shadows >> Server is on single server-mode with a game already made!"));
				return;
			}
			if(Shadows.getInstance().getConfig().getInt("max-games") >= Shadows.getInstance().getGames().size()){
				p.sendMessage(ChatUtil.format("Max games made!"));
				return;
			}
			Game game = Shadows.getInstance().getGame(args[0]);
			
			if(game != null){
				p.sendMessage(ChatUtil.format("That game already exists!"));
				return;
			}
			

			try{
			FileConfiguration fc = DataManager.getInstance().getGameInfo();
			fc.set("games." + args[0] + ".displayName", args[0]);			
			fc.set("games." + args[0] + ".maxPlayers", 8);
			fc.set("games." + args[0] + ".minPlayers", 3);
			fc.set("games." + args[0] + ".isTeamGame", false);
			List<String> temp1 = new ArrayList<>();
			String temp = "X:" + p.getLocation().getBlockX() + ", Y:" + p.getLocation().getBlockY() + ", Z:" + p.getLocation().getBlockZ() + ", World:" + p.getLocation().getWorld();
			fc.set("games." + args[0] + ".lobbyLoc", temp);
			fc.set("games." + args[0] + ".rareItems", temp1);
			fc.set("games." + args[0] + ".normalItems", temp1);
						Game newGame = new Game(args[0]);
						Shadows.getInstance().registerGame(newGame);
			DataManager.getInstance().saveGameInfo();
			
			p.sendMessage(ChatUtil.format("New Game " + args[0] + " created with a lobby point at your location!"));
			}catch(Exception e){
				Shadows.getInstance().getLogger().severe("Could not create game!");
				e.printStackTrace();
			}
		}
			
			
		
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
	

}