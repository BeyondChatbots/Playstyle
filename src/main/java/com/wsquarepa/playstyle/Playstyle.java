package com.wsquarepa.playstyle;

import co.aikar.commands.PaperCommandManager;
import com.wsquarepa.playstyle.commands.MainCommand;
import com.wsquarepa.playstyle.core.Mode;
import com.wsquarepa.playstyle.core.User;
import com.wsquarepa.playstyle.listeners.DamageListeners;
import com.wsquarepa.playstyle.listeners.EntityListeners;
import com.wsquarepa.playstyle.util.StoreManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class Playstyle extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        StoreManager.getInstance().setPlugin(this);

        PaperCommandManager manager = new PaperCommandManager(this);

        manager.getCommandCompletions().registerAsyncCompletion("modes", c -> Mode.getNames());

        manager.registerCommand(new MainCommand());

        // register events
        getServer().getPluginManager().registerEvents(new DamageListeners(), this);
        getServer().getPluginManager().registerEvents(new EntityListeners(), this);

        // set a repeating async task
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            // save storage to file
            StoreManager.getInstance().save();
        }, 0, 20 * 60 * 5);

        // set a repeating sync task
        getServer().getScheduler().runTaskTimer(this, () -> {
            // loop through all players
            getServer().getOnlinePlayers().forEach(player -> {
                // get user
                User user = StoreManager.getStorage().getUser(player.getUniqueId());

                // check if user is in retain mode
                if (user.getMode().equals(Mode.PEACEFUL)) {
                    PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING, 20 * 60, 0, true, false);

                    // apply glowing effect
                    player.addPotionEffect(effect);
                }
            });
        }, 0, 20 * 30);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        StoreManager.getInstance().save();
    }
}
