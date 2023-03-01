package io.github.jakstepn.Models.Items;

public class PersistentDataValues {
    PersistentDataTypes type;
    String value;

    public PersistentDataValues() {}

    public PersistentDataValues(PersistentDataTypes type, String value) {
        this.type = type;
        this.value = value;
    }
}
