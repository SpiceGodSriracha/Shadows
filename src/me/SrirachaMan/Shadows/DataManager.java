package me.SrirachaMan.Shadows;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class DataManager {
	    private static DataManager ourInstance = new DataManager();
	    public static DataManager getInstance() {
	        return ourInstance;
	    }
	    
	    
	    private DataManager() {
	    	
	        this.gameInfoFile = new File(Shadows.getInstance().getDataFolder(), "gameInfo.yml");
	        if (!this.gameInfoFile.exists()) {
	            try {
	                this.gameInfoFile.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        this.gameInfo = YamlConfiguration.loadConfiguration(this.gameInfoFile);
	    
	    
	    
	   
    }

	    private File gameInfoFile;
	    private FileConfiguration gameInfo;
	  

	    public FileConfiguration getGameInfo() {
	        return gameInfo;
	    }

	    public void saveGameInfo() {
	        try {
	            this.gameInfo.save(this.gameInfoFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    

	}
