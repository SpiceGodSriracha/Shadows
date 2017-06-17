package me.SrirachaMan.Shadows;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.SrirachaMan.Shadows.Commands.ShadowsCommand;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.gameMap;
import me.SrirachaMan.Shadows.Listeners.BlockInteract;
import me.SrirachaMan.Shadows.Listeners.ChestInteract;
import me.SrirachaMan.Shadows.Listeners.EntitySpawn;
import me.SrirachaMan.Shadows.Listeners.FoodLevelChange;
import me.SrirachaMan.Shadows.Listeners.PlayerDamage;
import me.SrirachaMan.Shadows.Listeners.PlayerDeath;
import me.SrirachaMan.Shadows.Listeners.PlayerJoin;
import me.SrirachaMan.Shadows.Listeners.PlayerLeave;
import me.SrirachaMan.Shadows.Listeners.inventoryClick;



public class Shadows extends JavaPlugin{

    private static Shadows instance;

    
    private Set<Game> games = new HashSet<>();
    private int gameLimit = 0;
    private Map<Player, Game> pMap = new HashMap<>();
    private List<Game> gamelist = new ArrayList<>();
    private List<gameMap> mapList = new ArrayList<>();
    private Inventory mapInv;
    
    @Override
    public void onEnable() {
    	
        instance = this;

        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveDefaultConfig();
        
        if(getConfig().getBoolean("single-server")){
        	gameLimit = 1;
        }else{
        	gameLimit = getConfig().getInt("max-games");
        }
        if(isSingleServerMode()){
        	for(Player p: Bukkit.getOnlinePlayers()){
        		p.setGameMode(GameMode.ADVENTURE);
        	}
        }
        if(DataManager.getInstance().getGameInfo().getConfigurationSection("games") != null){
        for(String gameName: DataManager.getInstance().getGameInfo().getConfigurationSection("games").getKeys(false)){
        	Game game = new Game(gameName);
        	this.registerGame(game);
        }
        }else{
        	//no games made
        	getLogger().warning("No games have been made!");
        }
        if(DataManager.getInstance().getGameInfo().getConfigurationSection("maps") != null){
        for(String mapName: DataManager.getInstance().getGameInfo().getConfigurationSection("maps").getKeys(false)){
        	gameMap map = new gameMap(mapName);
        	this.registerMap(map);
        }
        }else{
        	//no games made
        	getLogger().warning("No maps have been made!");
        }
        int tempsize = 9;
        if(mapList.size() >9)
        	tempsize = 18;
        if(mapList.size() >18){
        	tempsize = 27;
        }
        mapInv = Bukkit.getServer().createInventory(null, tempsize, "Map voting");
        if(mapList.size() > 0){
        	for(gameMap m: mapList){
        		ItemStack i = m.getVoteItem();
        		ItemMeta x = i.getItemMeta();
        		x.setDisplayName(m.getName());
        		x.setLore(m.getDescription());
        		i.setItemMeta(x);
        		if(i != null)
        		mapInv.addItem(i);
        	}
        }
        
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChange(), this);
        getServer().getPluginManager().registerEvents(new BlockInteract(), this);
        getServer().getPluginManager().registerEvents(new EntitySpawn(), this);
        getServer().getPluginManager().registerEvents(new ChestInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new inventoryClick(), this);
        getCommand("shadows").setExecutor(new ShadowsCommand());
        
        
        if (isSingleServerMode()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setGameMode(GameMode.ADVENTURE);
            }
}
        
    }

    @Override
    public void onDisable() {
        for (Map.Entry<Player, Game> entry : pMap.entrySet()) {
            entry.getKey().teleport(getLobbyPoint());
            entry.getKey().getInventory().clear();
            entry.getKey().getInventory().setArmorContents(null);
            entry.getKey().setHealth(20);
            entry.getKey().setGameMode(GameMode.SURVIVAL);
        }

        if (isSingleServerMode()) {
            for (Player player : getServer().getOnlinePlayers()) {
                player.teleport(getLobbyPoint());
            }
        }

        // Rollback every game

        for (Game game : getGames()) {
            for (Player player : game.getWorld().getPlayers()) {
                player.teleport(getLobbyPoint());
            }
        }

        instance = null;
    }


    public Inventory getMapInv(){
    	return mapInv;
    }
    public static Shadows getInstance() {
        return instance;
    }
    
    public Location getLobbyPoint(Game g){
    
    		
    		try{
    			int x = Shadows.getInstance().getConfig().getInt("lobby-point.x");
    			int y = Shadows.getInstance().getConfig().getInt("lobby-point.y");
    			int z = Shadows.getInstance().getConfig().getInt("lobby-point.z");
    			String world = Shadows.getInstance().getConfig().getString("lobby-point.world");
    			return new Location(Bukkit.getWorld(world), x, y, z);
    		}catch(Exception e){
    		return g.getLobbyPoint();	 		
    		}
    }
    
    public List<Game> getGamesList(){
    	return gamelist;
    }
    public Location getLobbyPoint(){
    	try{
			int x = Shadows.getInstance().getConfig().getInt("lobby-point.x");
			int y = Shadows.getInstance().getConfig().getInt("lobby-point.y");
			int z = Shadows.getInstance().getConfig().getInt("lobby-point.z");
			String world = Shadows.getInstance().getConfig().getString("lobby-point.world");
			return new Location(Bukkit.getWorld(world), x, y, z);
		}catch(Exception e){
        int x = 0;
        int y = 0;
        int z = 0;
String world = "world";
return new Location(Bukkit.getWorld(world), x, y, z);
		}
    }
    
    
    public boolean registerGame(Game game){
    	if(games.size() == gameLimit && games.size() != -1){
    		return false;
    	}
    	
    	games.add(game);
    	gamelist.add(game);
    	return true;
    }
    
    public boolean registerMap(gameMap map){
    	if(mapList.size() == gameLimit && mapList.size() != -1){
    		return false;
    	}
    	
    	mapList.add(map);
    	return true;
    }

    public boolean isSingleServerMode(){
    	return getConfig().getBoolean("single-server");
    }
    public Set<Game> getGames(){
    	return games;
    }
    
    public Game getGame(String gameName){
    	for(Game game: games){
    		if(game.getDisplayName().equalsIgnoreCase(gameName))
    			return game;
    	}
    	getLogger().warning("No game found!");
    	return null;
    }
    
    public Game getGame(Player p){
    return this.pMap.get(p);
    }
    public gameMap getMap(int id){
    	return mapList.get(id);
    }
    public gameMap getMap(String name){
    	for(gameMap g: mapList){
    		if(name.equalsIgnoreCase(g.getName())){
    			return g;
    		}
    	}
    	return null;
    }
    
    public List<gameMap> getMaps(){
    	return mapList;
    }
    
    public void setGame(Player p, Game g){
    if(g == null){
    	this.pMap.remove(p);
    }else{
    	this.pMap.put(p, g);
    }
    }
}



/*todo
Stalker balance
Map Voting/use
Chat beautification
*/

