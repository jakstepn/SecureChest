package io.github.jakstepn.Items;

import io.github.jakstepn.Items.Chests.Recipes.*;

public class ItemManager {
    public static void addChestRecipes() {
        new ExtremeChestRecipe().createRecipe();
        new HighChestRecipe().createRecipe();
        new NormalChestRecipe().createRecipe();
        new MinimalChestRecipe().createRecipe();
        new BarelyAnyChestRecipe().createRecipe();
    }
}
