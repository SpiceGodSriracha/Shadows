package me.SrirachaMan.Shadows.Constructors;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Utils.ChatUtil;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_12_R1.PlayerConnection;



public class GamePlayer {
	private Player player = null;
	private GameTeam team = null;
	private GamePlayerState state;
	private Location spawnPoint;
	private boolean isStalker = false;
	public boolean hasVoted = false;
	
	
	
	
	public GamePlayer(Player p){
		this.player = p;
	}
	
	public GamePlayer(GameTeam t){
		this.team = t;
	}
	
	public void setVoted(boolean bo){
		hasVoted = bo;
	}
	public boolean hasVoted(){
		return hasVoted;
	}
	
	public boolean isTeamClass(){
		return team != null && player == null;
	}
	
	public void sendVoteScreen(){
		Inventory I = Shadows.getInstance().getMapInv();
		player.openInventory(I);
	}
	public GameTeam getTeam(){
		return team;
	}
	
	public Player getPlayer(){
		return player;
	}
	public String getName(){
		if(isTeamClass()){
			return getTeam().getName();
		}
		return player.getName();
	}
	
	public enum GamePlayerState{
		
	}
	public void clearMark(){
		this.isStalker = false;
	}
	
	public void sendMessage(String msg){
		if(isTeamClass()){
			getTeam().sendMessage(msg);
		}else{
			player.sendMessage(ChatUtil.format(msg));
		}
	}
	public void teleport(Location l){
		if(l != null){
		if(isTeamClass()){
			getTeam().teleport(l);
		}else
			getPlayer().teleport(l);
	  }
	else{
		sendMessage("Location not set");
	}
	}
	
	public boolean isStalker(){
		return isStalker;
	}
	public void setStalker(){
		isStalker = true;
	}
	
	public void titleMessage(String msg, String sub){
		sendTitle(player, msg, sub, 5, 5, 5);
	}
	
	public void actionBarMessage(String msg){
		sendActionBar(player, msg);
	}
	public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = ChatSerializer.a("{\"text\": \"" + title +"\"}");
        IChatBaseComponent subtitleJSON = ChatSerializer.a("{\"text\": \"" + subtitle +"\"}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }

	public static void sendTabHF(Player player, String header, String footer){
		
	    CraftPlayer craftplayer = (CraftPlayer) player;
	    PlayerConnection connection = craftplayer.getHandle().playerConnection;
	    IChatBaseComponent headerJSON = ChatSerializer.a("{\"text\": \"" + header +"\"}");
	    IChatBaseComponent footerJSON = ChatSerializer.a("{\"text\": \"" + footer +"\"}");
	    PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
	  
	    try {
	        Field headerField = packet.getClass().getDeclaredField("a");
	        headerField.setAccessible(true);
	        headerField.set(packet, headerJSON);
	        headerField.setAccessible(!headerField.isAccessible());
	      
	        Field footerField = packet.getClass().getDeclaredField("b");
	        footerField.setAccessible(true);
	        footerField.set(packet, footerJSON);
	        footerField.setAccessible(!footerField.isAccessible());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    connection.sendPacket(packet);
	   
		
	}
	
	
	
	public static void sendActionBar(Player p, String message){
		p.sendTitle("", message, 3, 15, 3);
		//IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message +"\"}");
        //PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,2);
        //((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
}
}
