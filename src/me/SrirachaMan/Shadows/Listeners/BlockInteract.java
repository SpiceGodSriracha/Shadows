package me.SrirachaMan.Shadows.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.SrirachaMan.Shadows.Shadows;
import me.SrirachaMan.Shadows.Constructors.Game;
import me.SrirachaMan.Shadows.Constructors.GamePlayer;
import me.SrirachaMan.Shadows.Constructors.Game.GameState;

public class BlockInteract implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        handle(event, player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        handle(event, player);
    }

    private void handle(Cancellable event, Player player) {
        Game game = Shadows.getInstance().getGame(player);
        if (game != null) {
            if (game.isState(Game.GameState.LOBBY) || game.isState(GameState.VOTING) || game.isState(Game.GameState.PREPERATION) || game.isState(Game.GameState.ENDING) || game.isState(Game.GameState.STARTING)) {
                event.setCancelled(true); // Cancel, game isn't active
                return;
            }

            GamePlayer gamePlayer = game.getGamePlayer(player);
            if (gamePlayer != null) {

                    if (gamePlayer.getPlayer() == player) {
                        if (!game.getPlayers().contains(gamePlayer)) {
                            event.setCancelled(true);
                        
                    }
                }
            }
        } else {
            if (Shadows.getInstance().isSingleServerMode()) {
                event.setCancelled(true);
            }
        }
    }

}


