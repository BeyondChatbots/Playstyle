package com.wsquarepa.playstyle.util;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class StoreManager {
    private static StoreManager INSTANCE;
    private static Storage storage;
    private JavaPlugin plugin;

    private StoreManager() {

    }

    public static StoreManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StoreManager();
        }
        return INSTANCE;
    }

    public static Storage getStorage() {
        return storage;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        // load storage from file
        File configFile = new File(plugin.getDataFolder(), "storage.json");

        if (!configFile.exists()) {
            storage = new Storage();
            save();
        }

        try {
            String json = Files.readString(configFile.toPath());

            Gson gson = new Gson();
            storage = gson.fromJson(json, Storage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        // use Gson to save storage to file
        File configFile = new File(plugin.getDataFolder(), "storage.json");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
        }

        // save storage to file
        Gson gson = new Gson();
        String json = gson.toJson(storage);

        try {
            FileWriter writer = new FileWriter(configFile);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
