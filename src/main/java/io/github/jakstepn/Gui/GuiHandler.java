package io.github.jakstepn.Gui;

import io.github.jakstepn.Events.*;
import io.github.jakstepn.Items.InventoryManager;
import io.github.jakstepn.Items.Keys.IKey;
import io.github.jakstepn.Items.Keys.StickKey;
import io.github.jakstepn.Main;
import io.github.jakstepn.Models.Items.Enchant;
import io.github.jakstepn.Models.Items.Item;
import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Passwords.PasswordManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuiHandler implements Listener {

    private final String inventoryName = "Chest pin";
    private final static String chestInvName = "Chest inventory";
    private final static int chestSize = 27;
    private static final List<IGui> activeGuis = new ArrayList<>();
    private final Main main;

    public GuiHandler(Main main) { this.main = main; }

    @EventHandler
    public void onChestOpen(ChestOpenEvent e) {
        Player p = e.getPlayer();
        Inventory inv = createInventory(p, e.getChest());
        p.openInventory(inv);
    }

    @EventHandler
    public void onChestPlace(ChestPlaceEvent e) {
        SecureChest chest = e.getChest();
        Player p = e.getPlayer();
        Inventory inv = createInventory(p, chest);
        p.openInventory(inv);
        IKey key = new StickKey(chest);
        ItemStack keyItem = key.getItem(main);
        if(InventoryManager.hasEmptySlot(p.getInventory())) {
            p.getInventory().addItem(keyItem);
        } else {
            p.getWorld().dropItem(p.getLocation(), keyItem);
        }
    }

    @EventHandler
    public void onChestGuiOpen(OpenChestGuiEvent e) {
        SecureChest chest = e.getChest();
        Player p = e.getPlayer();
        IGui gui = new ChestGui();
        gui.setChest(chest);
        gui.setOwner(p);

        activeGuis.add(gui);
        openChestInventory(chest, p);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        InventoryView view = e.getView();
        Inventory inventory = e.getClickedInventory();
        if(inventory == null) return;

        InventoryHolder holder = e.getClickedInventory().getHolder();
        InventoryType type = view.getType();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType().isAir()) return;

        if (type != InventoryType.CHEST ||
                holder == null ||
                !view.getTitle().equals(inventoryName))
            return;

        e.setCancelled(true);

        final Player p = (Player) e.getWhoClicked();
        IGui playerGui = getUserGui(p);
        if(playerGui == null) return;

        playerGui.nextElementAt(e.getRawSlot(), e.getClickedInventory());
        p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 0.4f, 0.4f);
    }

    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder != null && e.getView().getTitle().equals(inventoryName)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(final InventoryCloseEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder != null) {
            if(e.getView().getTitle().equals(inventoryName)) {
                IGui userGuid = getUserGui(e.getPlayer());
                if (userGuid == null) return;

                SecureChest chest = userGuid.getChest();
                int[] inputPassword = userGuid.getCombination();

                // Remove Gui
                deactivateGui(e.getPlayer());

                if (chest.password == null) {
                    Bukkit.getServer().getPluginManager()
                            .callEvent(new PasswordCreateEvent(chest, inputPassword,
                                    userGuid.getCombinationMaterials(),
                                    (Player) e.getPlayer()));
                } else if (PasswordManager.isPasswordValid(chest, inputPassword)) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Bukkit.getServer().getPluginManager()
                                        .callEvent(new PasswordCorrectEvent(chest, (Player) e.getPlayer()));
                            }
                        }.runTaskLater(main, 5);
                } else {
                    Bukkit.getServer().getPluginManager()
                            .callEvent(new PasswordWrongEvent(chest, (Player) e.getPlayer()));
                }
                e.getPlayer().sendMessage(ChatColor.GREEN + "Closed pin gui");
            }
            else if(e.getView().getTitle().equals(chestInvName)) {
                IGui userGuid = getUserGui(e.getPlayer());
                if (userGuid == null) return;

                SecureChest chest = userGuid.getChest();

                Inventory inv = e.getInventory();
                ItemStack[] items = inv.getStorageContents();

                if(items.length > chestSize) {
                    return;
                }

                chest.items.clear();
                for(int i = 0; i < items.length; i++) {
                    ItemStack its = items[i];
                    if(items[i] != null && its.getItemMeta() != null) {
                        chest.items.put(i, new Item(its));
                    }
                }

                // Remove Gui
                deactivateGui(e.getPlayer());

                Bukkit.getServer().getPluginManager().callEvent(new ChestSaveEvent(chest));
                e.getPlayer().sendMessage(ChatColor.GREEN + "Closed chest");
            }
        }
    }

    public void openChestInventory(SecureChest chest, Player p) {
        Inventory inv = Bukkit.createInventory(p, chestSize, chestInvName);
        for (Item item : chest.items.values().stream().toList()) {
            ItemStack is = item.toItemStack();
            if(is == null) continue;
            inv.addItem(is);
        }
        p.openInventory(inv);
    }

    private Inventory createInventory(Player owner, SecureChest chest) {
        Inventory inv = Bukkit.createInventory(owner, 9, inventoryName);
        inv.setMaxStackSize(1);
        initializeItems(inv, chest, owner);
        return inv;
    }

    public void initializeItems(Inventory inv, SecureChest chest, HumanEntity guiOwner) {
        IGui gui = new WoolGui();
        gui.setOwner(guiOwner);
        gui.setChest(chest);
        gui.fillInventory(inv);
        activeGuis.add(gui);
    }

    public void deactivateGui(HumanEntity guiOwner) {
        activeGuis.removeIf(gui -> gui.getOwner().getUniqueId() == guiOwner.getUniqueId());
    }

    private IGui getUserGui(HumanEntity guiOwner) {
        return activeGuis.stream()
                .filter(gui -> gui.getOwner().getUniqueId() == guiOwner.getUniqueId())
                .findFirst().orElse(null);
    }
}
