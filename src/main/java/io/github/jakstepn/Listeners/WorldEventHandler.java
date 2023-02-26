package io.github.jakstepn.Listeners;

import io.github.jakstepn.Main;
import io.github.jakstepn.Models.Location;
import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Models.Security;
import io.github.jakstepn.Models.User;
import io.github.jakstepn.Serialization.Files.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class WorldEventHandler implements Listener {

    private final Main main;
    public WorldEventHandler(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemMeta meta = e.getItemInHand().getItemMeta();
        if(meta != null && meta.hasCustomModelData()) {
            Security security = Security.get(e.getItemInHand().getItemMeta().getCustomModelData());

            e.getPlayer().sendMessage("Set Chest with " + security.toString().toLowerCase());

            Player p = e.getPlayer();
            Block b = e.getBlock();

            SecureChest chest = new SecureChest(
                    new User(p.getName(), p.getUniqueId().toString()),
                    new Location(b.getX(), b.getY(), b.getZ()),
                    security
            );

            if(main == null) {
                p.sendMessage("Main is null!");
                return;
            }

            FileManager.serializeChest(chest, main.getFolderPath());
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();
        Location loc = new Location(b.getX(), b.getY(), b.getZ());
        if(e.getBlock().getBlockData().getMaterial() == Material.CHEST
        && FileManager.chestExistsAtLocation(loc, main.getFolderPath())) {
            SecureChest chest = FileManager.deserializeChest(loc, main.getFolderPath());

            if(chest == null) {
                p.sendMessage(ChatColor.RED + "Chest deserialization error!");
            }

            if(Objects.equals(chest.owner.uid, p.getUniqueId().toString())) {
                p.sendMessage(ChatColor.GREEN + "This is your chest!");
                FileManager.deleteFile(loc, main.getFolderPath());
                p.sendMessage(ChatColor.RED + "Removed chest!");
            } else {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "This is not your chest!");
            }
        }
    }
}
