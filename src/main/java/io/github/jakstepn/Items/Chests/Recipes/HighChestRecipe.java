package io.github.jakstepn.Items.Chests.Recipes;

import io.github.jakstepn.Models.Security;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class HighChestRecipe implements IRecipe{
    @Override
    public void createRecipe() {
        ItemStack item = ItemGenerator.generateChest(Security.HIGH);
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("chest_high"), item);
        sr.shape("WWW", "WGW", "WWW");
        sr.setIngredient('W', Material.CHEST);
        sr.setIngredient('G', Material.GOLD_BLOCK);
        Bukkit.getServer().addRecipe(sr);
    }
}
