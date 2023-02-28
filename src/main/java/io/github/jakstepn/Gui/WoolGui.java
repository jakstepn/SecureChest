package io.github.jakstepn.Gui;

import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Models.Security;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class WoolGui implements IGui {

    private Map<Integer, IItemCarousel> guiElements = new HashMap<>();
    private HumanEntity owner;
    private SecureChest chest;

    @Override
    public void fillInventory(Inventory inventory) {
        if(chest == null) return;

        Security security = chest.getSecurity();
        int iterator = security.getValue();
        Random random = new Random();
        while(iterator-- > 0) {
            WoolCarousel carousel = new WoolCarousel();
            carousel.setRandomMaterial(random);
            guiElements.put(iterator, carousel);
            inventory.setItem(iterator, carousel.createGuiItem());
        }
    }

    @Override
    public void nextElementAt(int index, Inventory inventory) {
        guiElements.get(index).nextItem(inventory, index);
    }

    @Override
    public int[] getCombination() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < guiElements.size(); i++) {
            values.add(guiElements.get(i).getCurrentMaterialId());
        }
        return values.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public String[] getCombinationMaterials() {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < guiElements.size(); i++) {
            values.add(guiElements.get(i).getCurrentMaterial().name());
        }
        return values.toArray(new String[0]);
    }

    @Override
    public void setOwner(HumanEntity entity) {
        owner = entity;
    }

    @Override
    public void setChest(SecureChest chest) {
        this.chest = chest;
    }

    @Override
    public HumanEntity getOwner() {
        return owner;
    }

    @Override
    public SecureChest getChest() { return chest; }
}
