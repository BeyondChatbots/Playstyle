package com.wsquarepa.playstyle.listeners;

import com.wsquarepa.playstyle.core.Mode;
import com.wsquarepa.playstyle.util.StoreManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityListeners implements Listener {
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player player) {
            if (StoreManager.getStorage().getUser(player.getUniqueId()).getMode().equals(Mode.PEACEFUL)) {
                event.setCancelled(true);
            }
        }
    }
}
