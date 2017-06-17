package me.SrirachaMan.Shadows.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Utils.ChatUtil;

public class ShadowsCommand implements CommandExecutor {
	private JoinCommand joinCommand;
	private ListCommand listCommand;
	private SetPointCommand setPointCommand;
	private GetRareItemsCommand getRareItemsCommand;
	private CreateCommand createCommand;
	private AddNormalItemCommand setNormalItems;
	private AddRareItem setRareItems;
	private AddSpawnPointCommand addSpawnPointCommand;
	private ListSpawnPointsCommand listSpawnPointsCommand;
	private SetStalkerSpawn setStalkerSpawn;
	private RemoveSpawnPointCommand removeSpawnPointCommand;
	private getNormalItems getNormalItems;
	private SetHubCommand setHubCommand;
	private ViewMaps ViewMaps;
	private AddMapCommand AddMapCommand;
	
	public ShadowsCommand(){
		this.joinCommand = new JoinCommand();
		this.listCommand = new ListCommand();
		this.setPointCommand = new SetPointCommand();
		this.getRareItemsCommand = new GetRareItemsCommand();
		this.createCommand = new CreateCommand();
		this.getNormalItems = new getNormalItems();
		this.setNormalItems = new AddNormalItemCommand();
		this.setRareItems = new AddRareItem();
		this.addSpawnPointCommand = new AddSpawnPointCommand();
		this.listSpawnPointsCommand = new ListSpawnPointsCommand();
		this.setStalkerSpawn = new SetStalkerSpawn();
		this.removeSpawnPointCommand = new RemoveSpawnPointCommand();
		this.setHubCommand = new SetHubCommand();
		this.ViewMaps = new ViewMaps();
		this.AddMapCommand = new AddMapCommand();

	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args){
		if(args.length == 0){
			if(!Shadows.getInstance().isSingleServerMode()) {
			s.sendMessage(ChatUtil.format("Shadows >> /shadows join - Join a Shadows game"));}
			 if(s.hasPermission("Shadows.admin")){
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows ListSpawnPoints - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows create <name>  - create a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows list - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows setRareItems - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows getRareitems - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows setStalkerSpawn - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows removespawnpoint - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows setlobby - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows setNormalItems - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows getNormalItems - Join a Shadows game"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows sethub - sets the hub point"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows viewmaps - lists current maps"));
				 s.sendMessage(ChatUtil.format("Shadows >> /shadows addmap <name> - create a new map"));
			 }
		}else{
			String argument = args[0];
			
			List<String> newArgs = new ArrayList<>();

            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    continue;
                }
            
            
            newArgs.add(args[i]);
            
            }
		
            if(argument.equalsIgnoreCase("join")){
            	System.out.println("ssmode " + Shadows.getInstance().isSingleServerMode());
            	if(!Shadows.getInstance().isSingleServerMode()){
            		this.joinCommand.execute(s, newArgs.toArray(new String[0]));
            	}
            }else if(argument.equalsIgnoreCase("list")){
            	this.listCommand.execute(s, newArgs.toArray(new String[0]));
            }else if(argument.equalsIgnoreCase("setLobby")){
            	this.setPointCommand.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("getRareItems")){
            	this.getRareItemsCommand.execute(s, newArgs.toArray(new String[0]));
            }else if(argument.equalsIgnoreCase("create")){
            	this.createCommand.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("setnormalitems")){
            	this.setNormalItems.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("setRareItems")){
            	this.setRareItems.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("addSpawnPoint")){
            	this.addSpawnPointCommand.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("ListSpawnPoints")){
            	this.listSpawnPointsCommand.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("setStalkerSpawn")){
            	this.setStalkerSpawn.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("removeSpawnPoint")){
            	this.removeSpawnPointCommand.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("getNormalItems")){
            	this.getNormalItems.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("sethub")){
            	this.setHubCommand.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("viewMaps")){
            	this.ViewMaps.execute(s, newArgs.toArray(new String[0]));
            }
            else if(argument.equalsIgnoreCase("addmap")){
            	this.AddMapCommand.execute(s, newArgs.toArray(new String[0]));
            }
            else{
            	s.sendMessage(ChatUtil.format("Shadows >> Unknown command!"));
            }
		
		
		}
		return true;
		
	}

}
