package File;

import io.github.jakstepn.Models.Location;
import io.github.jakstepn.Models.SecureChest;
import io.github.jakstepn.Models.Security;
import io.github.jakstepn.Models.User;
import io.github.jakstepn.Serialization.Files.FileManager;
import io.github.jakstepn.Serialization.Files.NameGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileTests {

    private static final String folderPath = "C:\\Users\\Kuba\\IdeaProjects\\SecureChest\\testfolder\\chests\\";

    @Test
    public void testFileCreation() {
        SecureChest chest = new SecureChest(
                new User("test", "testuid"),
                new Location(1, 2, 34),
                Security.BARELY_ANY
        );

        FileManager.serializeChest(chest, folderPath);

        SecureChest res = FileManager.deserializeChest(chest.location, folderPath);
        String resName = NameGenerator.generateName(res.location);

        Assertions.assertEquals(resName, NameGenerator.generateName(chest.location));
        Assertions.assertEquals(res.owner.nickname, chest.owner.nickname);
        Assertions.assertEquals(res.owner.uid, chest.owner.uid);
        Assertions.assertEquals(res.location.x, chest.location.x);
        Assertions.assertEquals(res.location.y, chest.location.y);
        Assertions.assertEquals(res.location.z, chest.location.z);
        Assertions.assertEquals(res.security, chest.security);
    }

    @Test
    public void testChestExists() {
        SecureChest chest = new SecureChest(
                new User("test2", "testuid2"),
                new Location(1, 2, 35),
                Security.EXTREME
        );

        FileManager.serializeChest(chest, folderPath);

        // Non existing chest
        SecureChest chest2 = new SecureChest(
                new User("test3", "testuid3"),
                new Location(1, 2, 36),
                Security.EXTREME
        );

        Assertions.assertTrue(FileManager.chestExistsAtLocation(chest.location, folderPath));
        Assertions.assertFalse(FileManager.chestExistsAtLocation(chest2.location, folderPath));
    }
}
