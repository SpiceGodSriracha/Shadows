package me.SrirachaMan.Shadows.Tasks;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;

public class StalkerCountdown extends BukkitRunnable {
	private int time = 10;
	private Game game;
	public StalkerCountdown(Game game){
		this.game = game;
		this.game.setState(GameState.PREPERATION);
		game.sendActionBar("The stalker will be released in " + time + " seconds!");
	}
	
	
	@Override
	public void run(){
		
		if(time == 0){
			//start
			game.sendTitle("Hes here...", "Run.....");
			
			game.getStalker().teleport(game.getStalkerSpawn());
			game.getStalker().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9000, 2));
			this.cancel();
			this.game.setState(GameState.ACTIVE);
			
		}else{
			time--;
				game.sendActionBar("The stalker will be released in " + time + " seconds!");
		}
	}
}