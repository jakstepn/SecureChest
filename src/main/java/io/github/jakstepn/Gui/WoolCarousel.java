package io.github.jakstepn.Gui;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WoolCarousel implements IItemCarousel{

    private final Map<Integer, Material> materials = new HashMap<>();
    private int currentMaterial;

            public WoolCarousel() {
                this.currentMaterial = 0;
                fillMaterials();
            }

            private void fillMaterials() {
                materials.put(0, Material.WHITE_WOOL);
                materials.put(1, Material.BLACK_WOOL);
                materials.put(2, Material.GRAY_WOOL);
                materials.put(3, Material.RED_WOOL);
                materials.put(4, Material.BLUE_WOOL);
                materials.put(5, Material.CYAN_WOOL);
                materials.put(6, Material.BROWN_WOOL);
                materials.put(7, Material.GREEN_WOOL);
                materials.put(8, Material.LIGHT_BLUE_WOOL);
                materials.put(9, Material.LIGHT_GRAY_WOOL);
                materials.put(10, Material.LIME_WOOL);
                materials.put(11, Material.MAGENTA_WOOL);
                materials.put(12, Material.ORANGE_WOOL);
                materials.put(13, Material.PINK_WOOL);
                materials.put(14, Material.PURPLE_WOOL);
                materials.put(15, Material.YELLOW_WOOL);
            }

    @Override
    public ItemStack createGuiItem() {
        return new ItemStack(materials.get(currentMaterial), 1);
    }

    @Override
    public void nextItem(Inventory inventory, int itemGuiIndex) {
                currentMaterial++;
                currentMaterial = currentMaterial % materials.size();
                inventory.setItem(itemGuiIndex, createGuiItem());
    }

    @Override
    public void setRandomMaterial(Random random) {
        currentMaterial = random.nextInt(0, materials.size());
    }

    @Override
    public int getCurrentMaterialId() {
                return currentMaterial;
    }

    @Override
    public Material getCurrentMaterial() {
        return materials.get(currentMaterial);
    }
}
