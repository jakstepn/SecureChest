package io.github.jakstepn.Models;

import io.github.jakstepn.Models.Items.Item;

import java.util.HashMap;
import java.util.Map;

public class SecureChest {
    public User owner;
    public Location location;
    public String security;

    // Password consists of numbers (representing block indexes in carousel) devided with ','
    public String password;

    public Map<Integer, Item> items;

    public SecureChest() {}

    public SecureChest(User owner, Location location, Security security) {
        this.owner = owner;
        this.location = location;
        this.security = security.toString();
        this.items = new HashMap<>();
    }

    public Security getSecurity() { return Security.valueOf(security); }

}
