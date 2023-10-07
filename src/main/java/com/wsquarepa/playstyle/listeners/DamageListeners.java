package com.wsquarepa.playstyle.listeners;

import com.wsquarepa.playstyle.core.Mode;
import com.wsquarepa.playstyle.util.StoreManager;
import com.wsquarepa.playstyle.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class DamageListeners implements Listener {
    private HashMap<UUID, Long> notifyCooldown = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Mode mode = StoreManager.getStorage().getUser(player.getUniqueId()).getMode();
        if (mode.equals(Mode.PEACEFUL) || mode.equals(Mode.RETAIN)) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);

            event.setDroppedExp(0);
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damaging) {
            Mode damagingMode = StoreManager.getStorage().getUser(damaging.getUniqueId()).getMode();

            if (damagingMode.equals(Mode.PEACEFUL) && !Util.isPeacefulMob(event.getEntity().getType())) {
                event.setCancelled(true);

                if (notifyCooldown.containsKey(damaging.getUniqueId())) {
                    long lastNotify = notifyCooldown.get(damaging.getUniqueId());
                    if (System.currentTimeMillis() - lastNotify < 1000 * 5) return;
                }

                notifyCooldown.put(damaging.getUniqueId(), System.currentTimeMillis());
                damaging.sendMessage(Component.text("You are in peaceful mode! You cannot attack other entities.", NamedTextColor.RED));
            }

            if (damagingMode.equals(Mode.RETAIN) && event.getEntity() instanceof Player) {
                event.setCancelled(true);

                if (notifyCooldown.containsKey(damaging.getUniqueId())) {
                    long lastNotify = notifyCooldown.get(damaging.getUniqueId());
                    if (System.currentTimeMillis() - lastNotify < 1000 * 5) return;
                }

                notifyCooldown.put(damaging.getUniqueId(), System.currentTimeMillis());
                damaging.sendMessage(Component.text("You are in retain mode! You cannot attack other players.", NamedTextColor.RED));
            }
        } else if (event.getEntity() instanceof Player damaged) {
            Mode damagedMode = StoreManager.getStorage().getUser(damaged.getUniqueId()).getMode();

            if (damagedMode.equals(Mode.PEACEFUL)) {
                event.setCancelled(true);

                if (event.getDamager() instanceof Player damaging) {
                    if (notifyCooldown.containsKey(damaging.getUniqueId())) {
                        long lastNotify = notifyCooldown.get(damaging.getUniqueId());
                        if (System.currentTimeMillis() - lastNotify < 1000 * 5) return;
                    }

                    notifyCooldown.put(damaging.getUniqueId(), System.currentTimeMillis());
                    damaging.sendMessage(Component.text("This player is in peaceful mode! You cannot attack them.", NamedTextColor.RED));
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            Mode mode = StoreManager.getStorage().getUser(player.getUniqueId()).getMode();
            if (mode.equals(Mode.DIFFICULT)) {
                event.setDamage(event.getDamage() * 1.5);
            }
        }
    }
}
