package io.github.jakstepn.Items;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {
    public static boolean hasEmptySlot(Inventory inventory) {
        return inventory.firstEmpty() != -1;
    }

    public static int getEmptySlotCount(Inventory inventory) {
        int count = 0;
        for (ItemStack is: inventory.getStorageContents()) {
            if(is == null || is.getType() == Material.AIR) count++;
        }
        return count;
    }
}
