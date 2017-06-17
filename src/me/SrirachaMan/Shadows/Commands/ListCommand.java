package me.SrirachaMan.Shadows.Commands;

import org.bukkit.command.CommandSender;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class ListCommand extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		for(Game game: Shadows.getInstance().getGames()){
			s.sendMessage(ChatUtil.format("Shadows >> " +  game.getDisplayName() + " - " + game.getPlayers().size() + " (alive) players"));
		}
	}

}
