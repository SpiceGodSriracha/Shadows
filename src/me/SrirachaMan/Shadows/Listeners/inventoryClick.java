package me.SrirachaMan.Shadows.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;
import me.SrirachaMan.Shadows.Constructors.gameMap;

public class inventoryClick implements Listener{
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player){
			Player p = (Player)e.getWhoClicked();
			if(Shadows.getInstance().getGame(p) != null && Shadows.getInstance().getGame(p).isState(GameState.VOTING)){
				Game game = Shadows.getInstance().getGame(p);
			if(game.isState(GameState.VOTING) && e.getClickedInventory().getName().contains("1Voting")){
				for(gameMap m:Shadows.getInstance().getMaps()){
					if(e.getCurrentItem().getType() == m.getVoteItem().getType() && !game.getGamePlayer(p).hasVoted()){
					p.closeInventory();
					m.vote();
					p.sendMessage("You voted for " + m.getName() + "!");
					Shadows.getInstance().getGame(p).getGamePlayer(p).setVoted(true);
					if(m.getVotes() > 5){
						game.setActiveMap(m.getName());
						game.setVoted(true);
					}
					}
				}
			}
			}
		}
		
	}

}
