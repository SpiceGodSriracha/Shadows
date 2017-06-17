package me.SrirachaMan.Shadows.Commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class getNormalItems extends SubCommand{
	@Override
	public void execute(CommandSender s, String[] args){
		if(s instanceof Player){
			Player p = (Player)s;
			if(args.length == 0){
				p.sendMessage(ChatUtil.format("Shadows >> /shadows getNormalItems <game>"));
				p.sendMessage(ChatUtil.format("Shadows >> WARNING! -- Clears your inventory! --"));
			}else{
			
			Game game = Shadows.getInstance().getGame(args[0]);
			
			if(game == null){
				p.sendMessage(ChatUtil.format("That game doesnt exist"));
				return;
			}
			p.sendMessage(ChatUtil.format("-- Clearing your inventory! --"));
			p.getInventory().clear();
			for(ItemStack i:game.getNormalItems()){
				p.getInventory().addItem(i);
			}

		}
			
		
	}else{
		s.sendMessage(ChatUtil.format("You're not a player!"));
		return;
	}
	}
}
