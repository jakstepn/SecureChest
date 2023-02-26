package io.github.jakstepn.Gui;

import io.github.jakstepn.Events.ChestOpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PinGui implements Listener {
    private final Inventory inv;

    public PinGui() {
        inv = Bukkit.createInventory(null, 9, "Chest pin");
        initializeItems();
    }

    public void initializeItems() {
        inv.addItem(createGuiItem(Material.WHITE_WOOL));
        inv.addItem(createGuiItem(Material.BLACK_WOOL));
        inv.addItem(createGuiItem(Material.GRAY_WOOL));
    }

    protected ItemStack createGuiItem(final Material material) {
        final ItemStack item = new ItemStack(material, 1);
        return item;
    }

    public void openInventory(final HumanEntity entity) {
        entity.openInventory(inv);
    }

    @EventHandler
    public void onChestOpen(ChestOpenEvent e) {
        openInventory(e.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
    e.getClickedInventory().getType().name()
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}
