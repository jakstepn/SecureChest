package io.github.jakstepn.Models;

import org.bukkit.Material;

public class Item {
    public Material materialType;
    public int amount;

    public Item() {}

    public Item(Material materialType, int amount) {
        this.materialType = materialType;
        this.amount = amount;
    }
}
