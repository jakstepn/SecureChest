package io.github.jakstepn.Passwords.Listeners;

import io.github.jakstepn.Events.OpenChestGuiEvent;
import io.github.jakstepn.Events.PasswordCorrectEvent;
import io.github.jakstepn.Events.PasswordCreateEvent;
import io.github.jakstepn.Events.PasswordWrongEvent;
import io.github.jakstepn.Main;
import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Passwords.PasswordManager;
import io.github.jakstepn.Serialization.Files.FileManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PasswordEventHandler implements Listener {
    private final Main main;
    public PasswordEventHandler(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPasswordCreate(PasswordCreateEvent e) {
        SecureChest chest = e.getSecuredChest();
        Player p = e.getPlayer();
        chest.password = PasswordManager.mapToString(e.getPassword());
        FileManager.serializeChest(chest, main.getFolderPath());

        World w = p.getWorld();
        Location loc = new Location(w, chest.location.x, chest.location.y, chest.location.z);
        loc.getBlock().setType(Material.CHEST);

        p.sendMessage(ChatColor.GREEN + "Password created! " + ChatColor.YELLOW +
                String.join(", ", e.getPasswordMaterialNames())
                + ".");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPasswordCorrect(PasswordCorrectEvent e) {
        Player p = e.getPlayer();
        SecureChest securedChest = e.getChest();
        p.sendMessage(ChatColor.BOLD + "" + ChatColor.ITALIC + "Password correct!");
        Bukkit.getServer().getPluginManager().callEvent(new OpenChestGuiEvent(securedChest, p));
    }

    @EventHandler
    public void onPasswordWrong(PasswordWrongEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(ChatColor.RED + "Password incorrect!");
        p.damage(2f);
    }
}
