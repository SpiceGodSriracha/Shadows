package me.SrirachaMan.Shadows.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class PlayerJoin implements Listener {
	
	
	@EventHandler
	
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(Shadows.getInstance().isSingleServerMode()){
			p.setGameMode(GameMode.ADVENTURE);
			p.setHealth(20);
			p.getInventory().clear();
			p.setFoodLevel(25);

			
			Game game = Shadows.getInstance().getGamesList().get(0);
			if(game == null){
				p.sendMessage(ChatUtil.format("No game has been made!"));
				return;
			}
			Shadows.getInstance().setGame(p, game);
			game.joinGame(new GamePlayer(p));
			return;
		}
		int x = 0,y = 0,z = 0;
			try{
				x = Shadows.getInstance().getConfig().getInt("lobby-point.x");
				y = Shadows.getInstance().getConfig().getInt("lobby-point.y");
				z = Shadows.getInstance().getConfig().getInt("lobby-point.z");
				String world = Shadows.getInstance().getConfig().getString("lobby-point.world");
				
				p.teleport(new Location(Bukkit.getWorld(world), x, y, z));
				
			}catch(Exception ex){
				Shadows.getInstance().getLogger().severe("Lobby point failed!");
				ex.printStackTrace();
			}
	
		
		
	}

}
