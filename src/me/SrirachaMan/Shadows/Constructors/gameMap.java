package me.SrirachaMan.Shadows.Constructors;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import me.SrirachaMan.Shadows.DataManager;
import me.SrirachaMan.Shadows.Shadows;
import net.md_5.bungee.api.ChatColor;

public class gameMap {
	FileConfiguration fc = DataManager.getInstance().getGameInfo();
	private List<Location> spawnpoints = new ArrayList<>();
	private String mapName;
	private Location stalkerSpawn;
	private String worldName;
	private String description;
	private World gameWorld;
	private int votes;
	private ItemStack voteItem;
	
	public gameMap(String name){
		this.mapName = name;
		this.worldName = fc.getString("maps." + mapName + "worldName");
		
		this.gameWorld = Bukkit.createWorld(new WorldCreator(fc.getString("maps." + mapName + ".worldName")));
		this.description = fc.getString("maps." + mapName + ".description");
		this.voteItem = new ItemStack(Material.getMaterial((fc.getString("maps." + mapName + ".voteItemId"))));
	
		try{
			String[] vals = fc.getString("maps." + mapName + ".stalkerSpawn").split(",");
			double x = Double.parseDouble(vals[0].split(":")[1]);
			double y = Double.parseDouble(vals[1].split(":")[1]);
			double z = Double.parseDouble(vals[2].split(":")[1]);
			this.stalkerSpawn = new Location(gameWorld, x, y, z);
					
		}catch(Exception e){
			Shadows.getInstance().getLogger().severe("Failed to load stalkerSpawn for " + mapName + "!");
		}
		
	for(String point: fc.getStringList("maps." + mapName + ".spawnPoints")){
		try{
			String[] vals = point.split(",");
			double x = Double.parseDouble(vals[0].split(":")[1]);
			double y = Double.parseDouble(vals[1].split(":")[1]);
			double z = Double.parseDouble(vals[2].split(":")[1]);
			Location loc = new Location(gameWorld, x, y, z);
			spawnpoints.add(loc);
					
		}catch(Exception e){
			Shadows.getInstance().getLogger().severe("Failed to load spawnpoints for " + mapName + "!");
		}
	}
	}
	
	
	public String getName(){
		return this.mapName;
	}
	
	public ItemStack getVoteItem(){
		return voteItem;
	}
	
	public List<String> getDescription(){
		List<String> temp = new ArrayList<>();
		temp.add(ChatColor.AQUA + "___________");
		temp.add(description);
		temp.add(ChatColor.AQUA + "___________");
		return temp;
	}
	
	public List<Location> getSpawnPoints(){
		return spawnpoints;
	}
	public void addSpawnPoint(Location loc){
		FileConfiguration fc = DataManager.getInstance().getGameInfo();
		List<String> temp = fc.getStringList("maps." + this.mapName + ".spawnPoints");
		temp.add("X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ());

		fc.set("maps." + this.mapName + ".spawnPoints", temp);
		DataManager.getInstance().saveGameInfo();
	}
	
	public int getVotes(){
		return votes;
	}
	
	public void vote(){
		votes++;
	}
	public void resetVotes(){
		votes = 0;
	}

	public Location getStalkerSpawn(){
		return stalkerSpawn;
	}

	public void setStalkerSpawn(Location location) {
		FileConfiguration fc = DataManager.getInstance().getGameInfo();
		stalkerSpawn = location;
		fc.set("maps." + mapName + ".stalkerSpawn", "X:" + location.getBlockX() + ", Y:" + location.getBlockY() + ", Z:" + location.getBlockZ());
		DataManager.getInstance().saveGameInfo();
	}

	

}

