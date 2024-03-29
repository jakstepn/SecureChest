package io.github.jakstepn.Events;

import io.github.jakstepn.Models.SecureChest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ChestOpenEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final SecureChest chest;

    public ChestOpenEvent(Player player, SecureChest chest) {
        this.player = player;
        this.chest = chest;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    @NonNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public SecureChest getChest() { return chest; }
}
