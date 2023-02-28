package io.github.jakstepn.Listeners;

import io.github.jakstepn.Events.ChestOpenEvent;
import io.github.jakstepn.Main;
import io.github.jakstepn.Models.Location;
import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Serialization.Files.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerEventHandler implements Listener {

    private final Main main;
    public PlayerEventHandler(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onChestInteraction(PlayerInteractEvent e) {
        Block b = e.getClickedBlock();
        Player p = e.getPlayer();
        if(b == null) return;

        Location loc = new Location(b.getX(), b.getY(), b.getZ());
        if(b.getBlockData().getMaterial() == Material.CHEST &&
                e.getAction() == Action.RIGHT_CLICK_BLOCK &&
                FileManager.chestExistsAtLocation(loc, main.getFolderPath())) {

            SecureChest chest = FileManager.deserializeChest(loc, main.getFolderPath());

            if(chest == null) {
                p.sendMessage(ChatColor.RED + "Chest deserialization error!");
            }

            if(Objects.equals(chest.owner.uid, p.getUniqueId().toString())) {
                p.sendMessage(ChatColor.GREEN + "This is your chest :)");
                p.sendMessage(ChatColor.YELLOW + "Protection: " + chest.security.toLowerCase());
            } else {
                p.sendMessage(ChatColor.RED + "This is not your chest!");
                p.sendMessage(ChatColor.RED + "Protection: " + chest.security.toLowerCase());
            }
            e.setCancelled(true);
            Bukkit.getPluginManager().callEvent(new ChestOpenEvent(p, chest));
        }
    }
}
