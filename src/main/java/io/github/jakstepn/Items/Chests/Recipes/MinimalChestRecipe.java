package io.github.jakstepn.Items.Chests.Recipes;

import io.github.jakstepn.Models.Security;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class MinimalChestRecipe implements IRecipe{
    @Override
    public void createRecipe() {
        ItemStack item = ItemGenerator.generateChest(Security.MINIMAL);
        if(item == null) return;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("chest_minimal"), item);
        sr.shape("WWW", "WIW", "WWW");
        sr.setIngredient('W', Material.CHEST);
        sr.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(sr);
    }
}
