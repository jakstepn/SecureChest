package io.github.jakstepn.Gui;

import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Models.Security;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public interface IGui {
    void setChest(SecureChest chest);
    void setOwner(HumanEntity entity);
    HumanEntity getOwner();
    SecureChest getChest();
    void fillInventory(Inventory inventory);
    void nextElementAt(int index, Inventory inventory);
    int[] getCombination();
    String[] getCombinationMaterials();
}
