package me.SrirachaMan.Shadows.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class JoinCommand extends SubCommand{

	
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			if(Shadows.getInstance().isSingleServerMode()){
				p.sendMessage(ChatUtil.format("Shadows >> Command is disabled when single server mode is on!"));
				return;
			}
			if(args.length == 0){
				p.sendMessage(ChatUtil.format("Shadows >> /shadows join <game>"));
			}else{
				for (Game game : Shadows.getInstance().getGames()) {
                    for (GamePlayer gamePlayer : game.getPlayers()) {
                            if (gamePlayer.getPlayer() == p) {
                                p.sendMessage(ChatUtil.format("&9Skywars &7>> &cYou're in a game."));
                                return;
                            }
                        }
                    }
			
			Game game = Shadows.getInstance().getGame(args[0]);
			if(game == null){
				p.sendMessage(ChatUtil.format("That game doesnt exist"));
				return;
			}
			Shadows.getInstance().setGame(p, game);
			game.joinGame(new GamePlayer(p));
		}
			
		
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
}
