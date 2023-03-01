package io.github.jakstepn.Models.Items;

public class IntConverter implements IConverter<Integer> {
    @Override
    public Integer convert(String value) {
        try {
            return Integer.parseInt(value);
        } catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
