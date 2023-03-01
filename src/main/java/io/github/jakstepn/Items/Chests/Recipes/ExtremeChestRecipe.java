package io.github.jakstepn.Items.Chests.Recipes;

import io.github.jakstepn.Models.Security;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ExtremeChestRecipe implements IRecipe{
    @Override
    public void createRecipe() {
        ItemStack item = ItemGenerator.generateChest(Security.EXTREME);
        if(item == null) return;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("chest_extreme"), item);
        sr.shape("WWW", "WDW", "WWW");
        sr.setIngredient('W', Material.CHEST);
        sr.setIngredient('D', Material.DIAMOND_BLOCK);
        Bukkit.getServer().addRecipe(sr);
    }
}
