package io.github.jakstepn.Gui;

import io.github.jakstepn.Models.SecureChest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class ChestGui implements IGui {
    private SecureChest chest;
    private HumanEntity owner;

    @Override
    public void setChest(SecureChest chest) {
        this.chest = chest;
    }

    @Override
    public void setOwner(HumanEntity entity) {
        this.owner = entity;
    }

    @Override
    public HumanEntity getOwner() {
        return owner;
    }

    @Override
    public SecureChest getChest() {
        return chest;
    }

    @Override
    public void fillInventory(Inventory inventory) {
        return;
    }

    @Override
    public void nextElementAt(int index, Inventory inventory) {
        return;
    }

    @Override
    public int[] getCombination() {
        return new int[0];
    }

    @Override
    public String[] getCombinationMaterials() {
        return null;
    }
}
