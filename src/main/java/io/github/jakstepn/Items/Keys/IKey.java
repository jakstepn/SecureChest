package io.github.jakstepn.Items.Keys;

import io.github.jakstepn.Main;
import io.github.jakstepn.Models.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface IKey {
    Material getMaterial();
    Location getChestLocation();
    ItemStack getItem(Main main);
}
