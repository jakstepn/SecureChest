package io.github.jakstepn.Serialization.Files;

import io.github.jakstepn.Exceptions.StringIsNotLocationException;
import io.github.jakstepn.Models.Location;

public class NameGenerator {

    private static final String separator = ",";

    // Location is always unique for each chest
    public static String generateName(Location loc) {
        return loc.x + separator + loc.y + separator + loc.z;
    }

    public static Location mapToLocation(String value) throws StringIsNotLocationException {

        String[] position = value.split(separator);
        if (position.length != 3) throw new StringIsNotLocationException(value);

        int x, y, z;
        try {
            x = Integer.parseInt(position[0]);
            y = Integer.parseInt(position[1]);
            z = Integer.parseInt(position[2]);
        } catch(NumberFormatException e) {
            throw new StringIsNotLocationException(value);
        }

        Location loc = new Location(x, y, z);

        return loc;
    }
}
