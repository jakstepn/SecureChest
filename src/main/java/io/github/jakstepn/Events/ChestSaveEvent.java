package io.github.jakstepn.Events;

import io.github.jakstepn.Models.SecureChest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ChestSaveEvent extends Event {
    private final SecureChest chest;
    private static final HandlerList HANDLERS = new HandlerList();

    public ChestSaveEvent(SecureChest chest) {
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

    public SecureChest getChest() { return chest; }
}
