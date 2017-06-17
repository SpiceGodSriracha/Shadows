package me.SrirachaMan.Shadows.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;

public class PlayerDamage implements Listener{

	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			
			Game g = Shadows.getInstance().getGame(p);
			
			if(g != null){
				if(g.isState(GameState.ENDING) || g.isState(GameState.LOBBY) || g.isState(GameState.STARTING) ||  g.isState(GameState.PREPERATION) ){
					e.setCancelled(true);
				}
			}else{
				if(Shadows.getInstance().isSingleServerMode()){
					e.setCancelled(true);
				}
			}
		}
	}
}
