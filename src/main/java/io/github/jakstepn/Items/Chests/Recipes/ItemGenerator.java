package io.github.jakstepn.Items.Chests.Recipes;

import io.github.jakstepn.Factory.MainFactory;
import io.github.jakstepn.Main;
import io.github.jakstepn.Models.Security;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemGenerator {
    @Nullable
    public static ItemStack generateChest(Security security) {
        Main plugin = MainFactory.getPlugin();
        if(plugin == null) return null;

        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;

        meta.setDisplayName("Secure chest");
        List<String> lore = new ArrayList<>();
        lore.add("Chest with protection level: ");
        lore.add(security.toString());

        NamespacedKey key = new NamespacedKey(plugin, "secure-chest");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, security.getValue());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
