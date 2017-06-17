package me.SrirachaMan.Shadows.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;
import me.SrirachaMan.Shadows.Tasks.CountToEndTask;

public class PlayerDeath implements Listener{

	@EventHandler
public void onDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		Game game = Shadows.getInstance().getGame(p);
		if(game != null && game.getGamePlayer(p) != null){
		handle(e, game);
		}
	}
	
	
	
	private void handle(PlayerDeathEvent e, Game game){
		if(!game.isState(GameState.ACTIVE)){
			return;
		}
		Player p = e.getEntity();
		GamePlayer gp = game.getGamePlayer(p);
		e.setDeathMessage(null);
		if(gp.isStalker())
			gp.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
		game.activateSpectatorSettings(p);
		
try{
		if(game.getPlayers().size() <= 1 || gp.isStalker()){
			if(game.getPlayers().get(0).isStalker()){
				game.sendMessage("The Stalker has won!");
			}else{
				game.sendMessage("The Surivors have won!");
			}
			game.sendMessage("Game finished!");
			
			
			CountToEndTask task = new CountToEndTask(game);
			task.runTaskTimer(Shadows.getInstance(),0 ,20);
		}

}catch(IndexOutOfBoundsException ignore){
	ignore.printStackTrace();
}
	}
}
