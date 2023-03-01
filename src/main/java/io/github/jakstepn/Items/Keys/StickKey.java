package io.github.jakstepn.Items.Keys;

import io.github.jakstepn.Main;
import io.github.jakstepn.Models.Location;
import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Serialization.Files.NameGenerator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class StickKey implements IKey{
    private final Location location;
    private final Material material;

    public StickKey(SecureChest chest) {
        this.location = chest.location;
        this.material = Material.STICK;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Location getChestLocation() {
        return location;
    }

    @Override
    public ItemStack getItem(Main main) {
        String name = ChatColor.ITALIC + "" + ChatColor.LIGHT_PURPLE + "SecureChest key";
        String[] lore = new String[] {
          ChatColor.RED + "x: " + location.x,
          ChatColor.RED + "y: " + location.y,
          ChatColor.RED + "z: " + location.z,
        };
        Enchantment enchantment = Enchantment.LUCK;

        return createStick(name, lore, enchantment, main);
    }

    private ItemStack createStick(String name, String[] lore, Enchantment enchantment, Main main) {
        ItemStack item = new ItemStack(this.material, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;

        meta.setDisplayName(name);
        meta.setLore(Arrays.stream(lore).toList());
        meta.addEnchant(enchantment, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        String locationString = NameGenerator.generateName(location);
        NamespacedKey key = new NamespacedKey(main, locationString);
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER_ARRAY,
                new int[] {location.x, location.y, location.z});

        item.setItemMeta(meta);
        item.setAmount(1);

        return item;
    }
}
