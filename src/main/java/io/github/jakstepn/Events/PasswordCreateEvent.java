package io.github.jakstepn.Events;

import io.github.jakstepn.Models.SecureChest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PasswordCreateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SecureChest chest;
    private final int[] password;
    private final String[] passwordMaterialNames;
    private final Player player;

    public PasswordCreateEvent(SecureChest chest, int[] password, String[] passwordMaterialNames, Player player) {
        this.chest = chest;
        this.password = password;
        this.player = player;
        this.passwordMaterialNames = passwordMaterialNames;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    @NonNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public SecureChest getSecuredChest() {
        return chest;
    }

    public int[] getPassword() {
        return password;
    }

    public String[] getPasswordMaterialNames() {
        return passwordMaterialNames;
    }

    public Player getPlayer() { return player; }
}
