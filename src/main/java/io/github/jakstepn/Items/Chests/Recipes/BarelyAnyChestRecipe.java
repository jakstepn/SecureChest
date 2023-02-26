package io.github.jakstepn.Items.Chests.Recipes;

import io.github.jakstepn.Models.Security;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class BarelyAnyChestRecipe implements IRecipe{
    @Override
    public void createRecipe() {
        ItemStack item = ItemGenerator.generateChest(Security.BARELY_ANY);
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("chest_barely"), item);
        sr.shape("WWW", "WRW", "WWW");
        sr.setIngredient('W', Material.CHEST);
        sr.setIngredient('R', Material.REDSTONE);
        Bukkit.getServer().addRecipe(sr);
    }
}
