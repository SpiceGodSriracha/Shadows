package me.SrirachaMan.Shadows.Tasks;

import org.bukkit.scheduler.BukkitRunnable;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;

public class CountToVoteTask extends BukkitRunnable {
	private int time = 20;
	private Game game;
	public CountToVoteTask(Game game){
		this.game = game;
		this.game.setState(GameState.LOBBY);
	}
	
	
	@Override
	public void run(){
		time -= 1;	
		
		if(time == 0){
			//start
			
			game.sendTitle("Starting!", "Please vote on a map!");

			cancel();
			
			for(GamePlayer p:game.getPlayers()){
				p.sendVoteScreen();
			}
			
			CountToEndVote run = new CountToEndVote(game);
			run.runTaskTimer(Shadows.getInstance(), 0, 20);
		}else{
			if (time == 15 || time == 10 || time == 5){
				game.sendActionBar("The game will begin in " + time + " seconds!");
			}
		}
	}
}
