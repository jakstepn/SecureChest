package io.github.jakstepn.Items.Chests.Recipes;

import io.github.jakstepn.Models.Security;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class NormalChestRecipe implements IRecipe {
    @Override
    public void createRecipe() {
        ItemStack item = ItemGenerator.generateChest(Security.NORMAL);
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("chest_normal"), item);
        sr.shape("WWW", "WNW", "WWW");
        sr.setIngredient('W', Material.CHEST);
        sr.setIngredient('N', Material.DIAMOND);
        Bukkit.getServer().addRecipe(sr);
    }
}
