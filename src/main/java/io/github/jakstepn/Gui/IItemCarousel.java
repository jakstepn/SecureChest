package io.github.jakstepn.Gui;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public interface IItemCarousel {
    ItemStack createGuiItem();
    void nextItem(Inventory inventory, int itemGuiIndex);
    void setRandomMaterial(Random random);
    int getCurrentMaterialId();
    Material getCurrentMaterial();
}
