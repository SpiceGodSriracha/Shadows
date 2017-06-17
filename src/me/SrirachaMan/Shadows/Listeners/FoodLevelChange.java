package me.SrirachaMan.Shadows.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;

public class FoodLevelChange implements Listener{

	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			for(Game game: Shadows.getInstance().getGames()){
				for(GamePlayer gp: game.getPlayers()){
					if(gp.getPlayer() == p){
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
}
