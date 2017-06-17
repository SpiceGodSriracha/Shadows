package me.SrirachaMan.Shadows.Tasks;

import org.bukkit.scheduler.BukkitRunnable;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;

public class CountToEndTask extends BukkitRunnable {
	private int time = 20;
	private Game game;
	public CountToEndTask(Game game){
		this.game = game;
		this.game.setState(GameState.ENDING);
	}
	
	
	@Override
	public void run(){
		time -= 1;	
		
		if(time == 0){
			//start
			
			game.sendTitle("Game ended!", "Thank you for playing!");
			game.endGame();
			cancel();
		}else{
			if (time == 15 || time == 10 || time == 5){
				game.sendActionBar("You will be teleported to the Lobby in " + time + " seconds!");
			}
		}
	}
}