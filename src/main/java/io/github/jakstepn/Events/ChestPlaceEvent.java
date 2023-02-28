package io.github.jakstepn.Events;

import io.github.jakstepn.Models.SecureChest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ChestPlaceEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SecureChest chest;
    private final Player player;

    public ChestPlaceEvent(SecureChest chest, Player player) {
        this.chest = chest;
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    @NonNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public SecureChest getChest() {
        return chest;
    }

    public Player getPlayer() {
        return player;
    }
}
