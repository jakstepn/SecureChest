package io.github.jakstepn.Models.Items;

import io.github.jakstepn.Factory.MainFactory;
import io.github.jakstepn.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Item {
    public Material materialType;
    public int amount;
    public Map<String, Integer> enchantments;
    public String[] lore;
    public String displayName;
    public Map<String, PersistentDataValues> data;

    public Item() {}

    public Item(ItemStack item) {
        this.materialType = item.getType();
        this.amount = item.getAmount();
        ItemMeta meta = item.getItemMeta();

        if(meta != null) {
            Map<Enchantment, Integer> enchants = meta.getEnchants();
                enchantments = new HashMap<>();

                for (Map.Entry<Enchantment, Integer> entry: enchants.entrySet()) {
                    enchantments.put(entry.getKey().getKey().getKey(), entry.getValue());
                }

            if(meta.getLore() != null) {
                this.lore = meta.getLore().toArray(new String[0]);
            }
            this.displayName = meta.getDisplayName();
            this.data = new HashMap<>();

            for (NamespacedKey key : meta.getPersistentDataContainer().getKeys()) {
                String keyVal = key.getKey();
                if(meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                    Integer intValue = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
                    data.put(keyVal, new PersistentDataValues(PersistentDataTypes.INT, intValue.toString()));
                } else if(meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER_ARRAY)) {
                    int[] intArrValue = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER_ARRAY);
                    String[] stringArr = Arrays.stream(intArrValue).mapToObj(String::valueOf).toArray(String[]::new);
                    data.put(keyVal, new PersistentDataValues(PersistentDataTypes.INT_ARR,
                            String.join(getPersistentDataTypesArraySeparator(), stringArr)));
                }
            }

        }
    }

    public static String getPersistentDataTypesArraySeparator() {
        return "_";
    }

    @Nullable
    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(materialType, amount);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return null;

        if(displayName != null) {
            meta.setDisplayName(displayName);
        }

        if(lore != null) {
            meta.setLore(Arrays.stream(lore).toList());
        }

        Main plugin = MainFactory.getPlugin();
        if(plugin == null) return null;

        for (Map.Entry<String, PersistentDataValues> entry : data.entrySet()) {
            NamespacedKey key = new NamespacedKey(plugin, entry.getKey());
            switch (entry.getValue().type) {
                case INT -> {
                    meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER,
                            new IntConverter().convert(entry.getValue().value));
                }
                case INT_ARR -> {
                    meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER_ARRAY,
                            new IntArrConverter().convert(entry.getValue().value));
                }
            }
        }

        if(enchantments != null) {
            for (Map.Entry<String, Integer> entry: enchantments.entrySet()) {
                Enchantment en = Enchantment.getByKey(NamespacedKey.minecraft(entry.getKey()));
                if(en == null) {
                    continue;
                }
                meta.addEnchant(en, entry.getValue(), true);
            }
        }

        item.setItemMeta(meta);
        return item;
    }
}
