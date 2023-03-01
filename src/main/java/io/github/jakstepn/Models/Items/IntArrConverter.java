package io.github.jakstepn.Models.Items;

import java.util.Arrays;

public class IntArrConverter implements IConverter<int[]> {
    @Override
    public int[] convert(String value) {
        try {
            return Arrays.stream(value.split(Item.getPersistentDataTypesArraySeparator())).mapToInt(Integer::valueOf)
                    .toArray();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
