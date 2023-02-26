package io.github.jakstepn.Models;

public class SecureChest {
    public User owner;
    public Location location;
    public String security;

    public SecureChest() {}

    public SecureChest(User owner, Location location, Security security) {
        this.owner = owner;
        this.location = location;
        this.security = security.toString();
    }

    public Security getSecurity() { return Security.valueOf(security); }
}
