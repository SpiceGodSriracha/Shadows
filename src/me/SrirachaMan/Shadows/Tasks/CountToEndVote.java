package me.SrirachaMan.Shadows.Tasks;

import org.bukkit.scheduler.BukkitRunnable;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;

public class CountToEndVote extends BukkitRunnable {
	private int time = 20;
	private Game game;
	public CountToEndVote(Game game){
		this.game = game;
		this.game.setState(GameState.VOTING);
	}
	
	
	@Override
	public void run(){
		time -= 1;	
		
		if(time == 0 || game.getVoted()){
			//start
			

			cancel();
			
			for(GamePlayer p:game.getPlayers()){
				p.setVoted(true);
			}
			game.selectMap();
			
			game.startCountDown();
		}else{
			if (time == 15 || time == 10 || time == 5){
				game.sendActionBar("The voting will end in " + time + " seconds!");
			}
		}
	}
}
