package com.wsquarepa.playstyle.util;

import com.wsquarepa.playstyle.core.User;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Storage {
    private final HashMap<UUID, User> players;

    public Storage() {
        this.players = new HashMap<>();
    }

    public User getUser(UUID uuid) {
        players.putIfAbsent(uuid, new User(uuid));

        return players.get(uuid);
    }
}
