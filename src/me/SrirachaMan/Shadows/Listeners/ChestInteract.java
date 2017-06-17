package me.SrirachaMan.Shadows.Listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;


public class ChestInteract implements Listener{

	
	@EventHandler
	public void onChestOpen(PlayerInteractEvent event){
		
		Player p = event.getPlayer();
		
		for(Game game: Shadows.getInstance().getGames()){
			for(GamePlayer gp: game.getPlayers()){
				if(gp.getPlayer() == p && !gp.isStalker()){
					handle(event, game);
				}
			}
		}
		

	}

	private void handle(PlayerInteractEvent event, Game game){
		if(event.hasBlock() && event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Chest){
			Chest chest = (Chest) event.getClickedBlock().getState();
			if(!game.getChests().contains(chest)){
			float chance;
			Random rand = new Random();

			for(int x = 0; x < 24; x++){
				chance = rand.nextFloat();
				if(chance <= 0.05){
					if(game.getRareItems().size() > 0)
					chest.getBlockInventory().addItem(game.getRareItems().get(rand.nextInt(game.getRareSize())));
				}else{
					if(chance <= 0.1){
						if(game.getNormalItems().size() > 0)
						chest.getBlockInventory().addItem(game.getNormalItems().get(rand.nextInt(game.getNormalSize())));
					}if(chance > 0.1){
						chest.getBlockInventory().addItem(new ItemStack(Material.AIR));
					}
				}
			}
			
			game.openChest(chest);
		}else return;
		}
	}
}
			
