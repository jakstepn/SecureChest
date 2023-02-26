package io.github.jakstepn.Listeners;

import io.github.jakstepn.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class ChestEventHandler implements Listener {
    private final Main main;
    public ChestEventHandler(Main main) {
        this.main = main;
    }
}
