package File;

import io.github.jakstepn.Models.Location;
import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Models.Security;
import io.github.jakstepn.Models.User;
import io.github.jakstepn.Serialization.Files.NameGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NameGeneratorTests {

    @Test
    public void nameGeneratorTest() {
        SecureChest chest = new SecureChest(
                new User("test1", "testuid2"),
                new Location(1, 2, 3),
                Security.EXTREME
        );

        Assertions.assertEquals("1-2-3", NameGenerator.generateName(chest.location));
    }
}
