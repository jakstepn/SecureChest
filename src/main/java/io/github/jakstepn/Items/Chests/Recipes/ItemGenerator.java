package io.github.jakstepn.Items.Chests.Recipes;

import io.github.jakstepn.Models.Security;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemGenerator {
    public static ItemStack generateChest(Security security) {
        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Secure chest");
        List<String> lore = new ArrayList<>();
        lore.add("Chest with protection level: ");
        lore.add(security.toString());
        meta.setCustomModelData(security.getValue());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
