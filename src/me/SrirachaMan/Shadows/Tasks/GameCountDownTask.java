package me.SrirachaMan.Shadows.Tasks;

import org.bukkit.scheduler.BukkitRunnable;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;

public class GameCountDownTask extends BukkitRunnable {
	private int time = 20;
	private Game game;
	public GameCountDownTask(Game game){
		this.game = game;
		this.game.setState(GameState.STARTING);
	}
	
	
	@Override
	public void run(){
		time -= 1;	
		
		if(time == 0){
			//start
			
			game.sendTitle("Starting!", "Teleporting to your positions!");
			game.teleportToStart();
			cancel();
			
			StalkerCountdown run = new StalkerCountdown(game);
			run.runTaskTimer(Shadows.getInstance(), 0, 20);
		}else{
			if (time == 15 || time == 10 || time == 5){
				game.sendActionBar("The game will begin in " + time + " seconds!");
			}
		}
	}
}
