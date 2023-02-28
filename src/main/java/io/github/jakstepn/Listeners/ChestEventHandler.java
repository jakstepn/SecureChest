package io.github.jakstepn.Listeners;

import io.github.jakstepn.Events.ChestSaveEvent;
import io.github.jakstepn.Main;
import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Serialization.Files.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChestEventHandler implements Listener {
    private final Main main;
    public ChestEventHandler(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onChestSave(ChestSaveEvent e) {
        SecureChest chest = e.getChest();
        FileManager.serializeChest(chest, main.getFolderPath());
    }
}
