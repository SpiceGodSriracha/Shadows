package me.SrirachaMan.Shadows.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;
import me.SrirachaMan.Shadows.Tasks.CountToEndTask;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;

public class PlayerLeave implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Shadows.getInstance().getLogger().warning("marker");
		Player p = e.getPlayer();
		Game game = Shadows.getInstance().getGame(p);
		if(game != null && game.getGamePlayer(p) != null){
			
			handle(e, game);
			game.removePlayer(game.getGamePlayer(p));
			Shadows.getInstance().getLogger().warning("marker2");
		}
	
	
	
	
	}
	
	private void handle(PlayerQuitEvent e, Game game){

		Player p = e.getPlayer();
		GamePlayer gp = game.getGamePlayer(p);
		
		
		if(gp.isStalker()){
			gp.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		
		
		gp.teleport(Shadows.getInstance().getLobbyPoint());
		if(game.isState(GameState.ACTIVE)){
		
		
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
}}
	}
}
	

