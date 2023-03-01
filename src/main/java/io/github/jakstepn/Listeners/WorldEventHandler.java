package io.github.jakstepn.Listeners;

import io.github.jakstepn.Events.ChestPlaceEvent;
import io.github.jakstepn.Items.InventoryManager;
import io.github.jakstepn.Main;
import io.github.jakstepn.Models.*;
import io.github.jakstepn.Models.Items.Item;
import io.github.jakstepn.Serialization.Files.FileManager;
import io.github.jakstepn.Serialization.Files.NameGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class WorldEventHandler implements Listener {

    private final Main main;
    public WorldEventHandler(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        ItemMeta meta = item.getItemMeta();

        NamespacedKey key = new NamespacedKey(main, "secure-chest");
        Integer val;

        if(meta != null && (val = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER)) != null) {
            Security security = Security.get(val);

            e.getPlayer().sendMessage(ChatColor.BOLD + "" + ChatColor.ITALIC + "" +
                    ChatColor.YELLOW + "Set Chest with " + security.toString().toLowerCase());

            Player p = e.getPlayer();
            Block b = e.getBlock();

            SecureChest chest = new SecureChest(
                    new User(p.getName(), p.getUniqueId().toString()),
                    new Location(b.getX(), b.getY(), b.getZ()),
                    security
            );

            e.setCancelled(true);
            Bukkit.getServer().getPluginManager().callEvent(new ChestPlaceEvent(chest, p));
            int itemCount = item.getAmount();
            itemCount--;
            if(itemCount == 0) {
                p.getInventory().remove(item);
            } else {
                item.setAmount(itemCount);
            }
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

                // Check if the player is holding a key to the chest
                ItemStack itemInHand = p.getInventory().getItemInMainHand();

                ItemMeta itemMeta = itemInHand.getItemMeta();
                if(itemMeta == null) {
                    e.setCancelled(true);
                    wrongItemHeldMessage(p);
                    return;
                }

                NamespacedKey key = new NamespacedKey(main, NameGenerator.generateName(chest.location));
                int[] locationData = itemMeta.getPersistentDataContainer().get(key,
                        PersistentDataType.INTEGER_ARRAY);

                if(locationData != null &&
                locationData[0] == chest.location.x &&
                locationData[1] == chest.location.y &&
                locationData[2] == chest.location.z) {

                    p.getInventory().remove(itemInHand);

                    int playerEmptySlotCount = InventoryManager.getEmptySlotCount(p.getInventory());

                    Item[] chestItemStacks = chest.items.values().toArray(new Item[0]);

                    if(chestItemStacks.length < playerEmptySlotCount) playerEmptySlotCount = chestItemStacks.length;

                    for (int j = 0; j < playerEmptySlotCount; j++) {
                        Item item = chestItemStacks[j];
                        ItemStack is = item.toItemStack();
                        if(is == null) continue;

                        p.getInventory().addItem(is);
                    }

                    for (int j = 0; j < chestItemStacks.length - playerEmptySlotCount; j++) {
                        Item item = chestItemStacks[playerEmptySlotCount + j];
                        ItemStack is = item.toItemStack();
                        if(is == null) continue;

                        p.getWorld().dropItem(new
                                org.bukkit.Location(p.getWorld(), chest.location.x, chest.location.y, chest.location.z),
                                is);
                    }

                    FileManager.deleteFile(loc, main.getFolderPath());
                    p.sendMessage(ChatColor.GREEN + "Removed chest!");
                } else {
                    e.setCancelled(true);
                    wrongItemHeldMessage(p);
                }

            } else {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "This is not your chest!");
            }
        }
    }

    private void wrongItemHeldMessage(Player p) {
        p.sendMessage(ChatColor.RED + "You must be holding a key to the chest you want to destroy!");
    }
}
