package me.SrirachaMan.Shadows.Constructors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.SrirachaMan.Shadows.DataManager;
import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Tasks.CountToVoteTask;
import me.SrirachaMan.Shadows.Tasks.GameCountDownTask;

public class Game {
	
	private int maxPlayers, minPlayers;
	private World World;
	private List<Location> spawnpoints;
	private boolean isTeamGame;
	private String displayName;
	private Location lobbyLoc;
	private Location stalkerSpawn;
	private List<ItemStack> normalItems;
	private List<ItemStack> rareItems;
	
	private List<GamePlayer> players, spectators;
	private GameState gameState = GameState.LOBBY;
	private Map<GamePlayer, Location> gamePlayerToSpawnPoint = new HashMap<>();
	private Set<Chest> opened;
	private List<gameMap> gameMaps;
	private gameMap activeMap;
	private boolean voted;
	
	public Game(String gameName){
		FileConfiguration fc = DataManager.getInstance().getGameInfo();
		this.displayName = fc.getString("games." + gameName + ".displayName");
		this.maxPlayers  = fc.getInt("games." + gameName + ".maxPlayers");
		this.minPlayers  = fc.getInt("games." + gameName + ".minPlayers");
		this.gameMaps = Shadows.getInstance().getMaps();
		this.voted = false;
		
		
		if(Shadows.getInstance().getMaps().size() >0)
		this.activeMap = Shadows.getInstance().getMap(0);

		
		
		
		this.opened = new HashSet<>();
		
		
		try{
			String[] lob = fc.getString("games." + gameName + ".lobbyLoc").split(",");
			double x = Double.parseDouble(lob[0].split(":")[1]);
			double y = Double.parseDouble(lob[1].split(":")[1]);
			double z = Double.parseDouble(lob[2].split(":")[1]);
			String world = lob[3].split(":")[1];
			lobbyLoc = new Location(Bukkit.getWorld(world), x, y, z);			
		}catch(Exception e){
			Shadows.getInstance().getLogger().severe("Failed to load lobby location for " + gameName + "!");
		}
		if(activeMap != null)
		this.stalkerSpawn = activeMap.getStalkerSpawn();
		
		this.spawnpoints = new ArrayList<>();
		
	
		
		this.normalItems = new ArrayList<>();
		this.rareItems = new ArrayList<>();
		
		for(String item: fc.getStringList("games." + gameName + ".normalItems")){
			try{
				Random rand = new Random();
			Material mat = Material.valueOf(item);
			int count = 1;
			if(mat == Material.ARROW){
				count = 5;
			}
			ItemStack in = new ItemStack(mat, count);
			//Add Item Meta
			this.normalItems.add(in);
			}catch(Exception ex){
				Shadows.getInstance().getLogger().severe("Invalid id in normal items for " + gameName + "!" + "(item)");
				ex.printStackTrace();
			}
		}
		for(String item: fc.getStringList("games." + gameName + ".rareItems")){
			try{
				String[] temp = item.split(",");

				String[] enchants = new String[0];
			//Add Item Meta
			//ID:#,ItemName:"",Count:#,Lore:"",Enchants:[Id:Level,ID:Level]
			String id = temp[0].split(":")[1];
			String name = temp[1].split(":")[1];
			int count = Integer.parseInt(temp[2].split(":")[1]);
			List<String> lore = new ArrayList<>();
			for(String add: temp[3].split(":")[1].split(",")){
			lore.add(add);}
			try{
			enchants = temp[4].split(":")[1].split(",");
			}catch(ArrayIndexOutOfBoundsException e){
				Shadows.getInstance().getLogger().severe("Item without enchant || " + name + " in " + gameName);
			}
			
			ItemStack mat = new ItemStack(Material.valueOf(id), count);
			mat.getItemMeta().setUnbreakable(true);
			
			for(String en:enchants){
				try{
				mat.addEnchantment(Enchantment.getByName(en.split(":")[0]), Integer.parseInt(en.split(":")[1]));
				}catch(Exception e){
					Shadows.getInstance().getLogger().severe("Invalid enchant id on item " + name + " in " + gameName);
					e.printStackTrace();
				}
			}
			
			mat.getItemMeta().setDisplayName(name);
			mat.getItemMeta().setLore(lore);
			
			
			this.rareItems.add(mat);
			}catch(Exception ex){
				Shadows.getInstance().getLogger().severe("Invalid id in rare items for " + gameName + "!" + "(" +item + ")");
				ex.printStackTrace();
			}
		}
		
		this.isTeamGame = fc.getBoolean("games." + gameName + "isTeamGame");
		this.players = new ArrayList<>();
		this.spectators = new ArrayList<>();
		
		
		
		
		
	}
	
	
	
	
	public boolean joinGame(GamePlayer p){

		
		if(p.isTeamClass() && !isTeamGame){
			return false;
		}
		
		
		if(isState(GameState.LOBBY) || isState(GameState.STARTING)){
			if(getPlayers().size() == maxPlayers){
				p.sendMessage("Game full!");
				return false;
			}
			getPlayers().add(p);
			p.getPlayer().setGameMode(GameMode.ADVENTURE);
			p.teleport(isState(GameState.LOBBY) ? lobbyLoc : null);
			sendMessage("&6Welcome to the game " + p.getName() + "!  -- " + getPlayers().size() + "/" + maxPlayers);
			
			if(getPlayers().size() == minPlayers && !isState(GameState.STARTING)){
				setState(GameState.STARTING);
				sendMessage("Game starting");
				markStalker();
				new CountToVoteTask(this).runTaskTimer(Shadows.getInstance(), 0, 20);
			}
			
			return true;
		}
		else{
			getSpectators().add(p);
			//
			return true;
		}
		
	}
	public void teleportToStart(){
		
		spawnpoints = this.getActiveMap().getSpawnPoints();
		int id = 0;
		
		for(GamePlayer gp: getPlayers()){
			if(!gp.isStalker()){
				try{
					gamePlayerToSpawnPoint.put(gp, spawnpoints.get(id));
					gp.teleport(spawnpoints.get(id));
					id ++;
				}catch(IndexOutOfBoundsException e){
					Shadows.getInstance().getLogger().warning("Add more spawn points to " + getDisplayName() + "!");
				}
			}else{
				gamePlayerToSpawnPoint.put(gp, stalkerSpawn);
				gp.sendMessage("Prepare stalker...");
			}
			
		}
		
		
	}
	
	public void selectMap(){
		int maxv = 0;
		gameMap map = Shadows.getInstance().getMap(0);
		for(gameMap g: Shadows.getInstance().getMaps()){
			if(g.getVotes() > maxv){
				maxv = g.getVotes();
				map = g;
				g.resetVotes();
			}
		}
		activeMap = map;
	}
	public void removePlayer(GamePlayer p){
		if(getPlayers().contains(p)){
			getPlayers().remove(p);
			return;
		}
		if(getSpectators().contains(p)){
			getSpectators().remove(p);
		}
	}
	public boolean hasMap(){
		return activeMap != null;
	}
	
	public gameMap getActiveMap(){
		return activeMap;
	}
	
	public void setActiveMap(int id){
		activeMap = Shadows.getInstance().getMap(id);
	}
	public void setActiveMap(String name){
		activeMap = Shadows.getInstance().getMap(name);
	}
	
	public boolean isState(GameState s){
		return getState() == s;
	}
	
	public int getRareSize(){
		return DataManager.getInstance().getGameInfo().getStringList("games." + getDisplayName() + ".rareItems").size();
	}
	
	public int getNormalSize(){
		return DataManager.getInstance().getGameInfo().getStringList("games." + getDisplayName() + ".normalItems").size();
	}
	
	
	public void setNormalItems(Inventory e){
		List<String> temp = new ArrayList<>();
		for(ItemStack i: e){
			if(i != null){
				temp.add(i.getType().toString());
			}
		}
		DataManager.getInstance().getGameInfo().set("games." + getDisplayName() + ".normalItems", temp);
		DataManager.getInstance().saveGameInfo();
	}
	
	
	public void setRareItems(Inventory e){
		List<String> temp = new ArrayList<>();
		String ench = "Enchants:[";
		for(int x = 0; x < e.getSize(); x++){
				
			if(e.getItem(x) != null){
				ItemStack item = e.getItem(x);
				try{
				//ID:#,ItemName:"",Count:#,Lore:"",Enchants:[Id:Level,ID:Level]
				ench = "Enchants:[";
				for(Enchantment enchant: item.getItemMeta().getEnchants().keySet()){
					ench += enchant.toString() + ":" + item.getItemMeta().getEnchants().get(enchant) + ",";
					
				}
				ench += "remove,]";
				ench.replaceFirst(",remove,", "");
				ench.replaceFirst("remove,", "");
				temp.add("ID:" + item.getType().toString() + ",ItemName:" + item.getItemMeta().getDisplayName() + ",Count:" + item.getAmount() + ",Lore:" + item.getItemMeta().getLore() + ":" + ench);
				
				
				}catch(Exception exc){
					Shadows.getInstance().getLogger().severe("Error setting rare items for " + getDisplayName() + "!");
					exc.printStackTrace();
				}
			}
		}
		DataManager.getInstance().getGameInfo().set("games." + getDisplayName() + ".rareItems", temp);
		DataManager.getInstance().saveGameInfo();
	}
	
	public void activateSpectatorSettings(Player player) {
        GamePlayer gamePlayer = getGamePlayer(player);

        player.setHealth(20);
        player.setGameMode(GameMode.SPECTATOR);

        if (gamePlayer != null) {
            switchToSpectator(gamePlayer);
        }
}
	public World getWorld(){
		return World;
	}
	
	
	private GameState getState(){
		return gameState;
	}
	
	public Location getStalkerSpawn(){
		return activeMap.getStalkerSpawn();
	}
	
	public Location getLobbyPoint(){
		return lobbyLoc;
	}
	public List<Location> getSpawnPoints(){
		return activeMap.getSpawnPoints();
	}
	
	public List<ItemStack> getRareItems(){
		return rareItems;
	}
	public List<ItemStack> getNormalItems(){
		return normalItems;
	}
	
    public void assignSpawnPositions() {
    	spawnpoints = this.activeMap.getSpawnPoints();
        int id = 0;
        for (GamePlayer gamePlayer : getPlayers()) {
            try {
                gamePlayerToSpawnPoint.put(gamePlayer, spawnpoints.get(id));
                gamePlayer.teleport(spawnpoints.get(id));
                id += 1;
                gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
                gamePlayer.getPlayer().setHealth(20);
            } catch (IndexOutOfBoundsException ex) {
                Shadows.getInstance().getLogger().severe("Not enough spawn points to satisfy game needs (Game is " + getDisplayName() + ")");
            }
        }
}
	
	public Set<Chest> getChests(){
		return opened;
	}
	
	
	public void setlobbyPoint(Location loc, Game game){
		FileConfiguration fc = DataManager.getInstance().getGameInfo();
		String temp = "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ() + ", World:" + loc.getWorld().getName();

		fc.set("games." + game.getDisplayName() + ".lobbyLoc", temp);
		DataManager.getInstance().saveGameInfo();
	}
	
	
	
	public void endGame(){
		this.setVoted(false);
		this.setActiveMap(0);

		
		for(gameMap gm: Shadows.getInstance().getMaps()){
			gm.resetVotes();
		}
		List<GamePlayer> temp = getPlayers();
		temp.addAll(getSpectators());
		for(GamePlayer gp: temp){
			gp.teleport(getLobbyPoint());
			gp.getPlayer().setGameMode(GameMode.ADVENTURE);
			gp.getPlayer().setHealth(20);
			gp.getPlayer().getInventory().clear();
			gp.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
			gp.setVoted(false);
			if(gp.isStalker()){
				gp.clearMark();
			}
		}
		rollBack();
		kickPlayers();
		setState(GameState.LOBBY);
	}
	public void kickPlayers(){
		players.clear();
		spectators.clear();
	}
	
	
	public void openChest(Chest c){
		opened.add(c);
	}
	
	public void rollBack(){
		for(Chest c: opened){
			c.getBlockInventory().clear();
		}
		opened.clear();
	}
	
	public int getMaxPlayers(){
		return maxPlayers;
	}
	public void setState(GameState s){
		this.gameState = s;
	}
	public int getMinPlayers(){
		return minPlayers;
	}
	public Set<Player> getSurvivors(){
		Set<Player> tempo = new HashSet<>();
		for(GamePlayer p: getPlayers()){
			if(!p.isStalker()){
				tempo.add(p.getPlayer());
			}
		}
		return tempo;
	}
	public String getDisplayName(){
		return displayName;
	}
	public List<GamePlayer> getPlayers(){
		return players;
	}
	public List<GamePlayer> getSpectators(){
		return spectators;
	}
	public GamePlayer getStalker(){
		for(GamePlayer gp: getPlayers()){
			if(gp.isStalker()){
				return gp;
			}
		}
		return null;
	}
	
	
	
	public void switchToSpectator(GamePlayer gp){
		getPlayers().remove(gp);
		getSpectators().add(gp);
	}
	

	
	public GamePlayer getGamePlayer(Player p){
		for(GamePlayer gp: getPlayers()){
			if(gp.getPlayer() == p){
				return gp;
			}
		}
		
		for(GamePlayer gp: getSpectators()){
			if(gp.getPlayer() == p){
				return gp;
			}
		}
		
		return null;
	}
	
	public void sendMessage(String msg){
		for(GamePlayer g: getPlayers()){
			g.sendMessage(msg);
		}
	}
	public void setVoted(boolean b){
		voted = b;
	}
	
	public boolean getVoted(){
		return voted;
	}
	
	public void sendTitle(String msg,String sub){
		for(GamePlayer g: getPlayers()){
			g.titleMessage(msg, sub);
		}
	}
	
	public void sendActionBar(String msg){
		for(GamePlayer g: getPlayers()){
			g.actionBarMessage(msg);
		}
	}
	public void startCountDown(){
		
		new GameCountDownTask(this).runTaskTimer(Shadows.getInstance(), 0, 20);
	}
	
	public void startVoting(){
		new CountToVoteTask(this).runTaskTimer(Shadows.getInstance(),0 ,20);
	}
	
	public void markStalker(){
		int id = 0;
		Random rand = new Random();
		int point = rand.nextInt(getPlayers().size()) - 1;
		for(GamePlayer gp: getPlayers()){
			if(id == point){
				gp.setStalker();
			}
			id ++;
		}
	}
	
	public enum GameState{
		LOBBY, VOTING, STARTING,PREPERATION, ACTIVE, FINAL, ENDING
	}
}
