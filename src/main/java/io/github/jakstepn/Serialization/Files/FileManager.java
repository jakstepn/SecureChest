package io.github.jakstepn.Serialization.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.jakstepn.Exceptions.StringIsNotLocationException;
import io.github.jakstepn.Models.Location;
import io.github.jakstepn.Models.SecureChest;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileManager {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<String> getExisitingChestNames(String folderPath) {
        File folder = new File(folderPath);

        File[] files = folder.listFiles();
        if(files != null)
            return Arrays.stream(files).map(File::getName).toList();
        else return null;
    }

    public static boolean chestExistsAtLocation(Location loc, String folderPath) {
        List<Location> chestLocation = Objects.requireNonNull(getExisitingChestNames(folderPath)).stream().map(name -> {
            try {
                return NameGenerator.mapToLocation(name);
            } catch (StringIsNotLocationException e) {
                e.printStackTrace();
            }
            return null;
        }).toList();

        return chestLocation.stream().anyMatch(cl ->
                cl.x == loc.x &&
                cl.y == loc.y &&
                cl.z == loc.z);
    }

    public static void serializeChest(SecureChest chest, String folderPath) {
        try {
            String json = gson.toJson(chest);

            Path path = createFile(chest, folderPath);
            writeToFile(path.toString(), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(Location loc, String folderPath) {
        try {
            Files.delete(new File(folderPath + "/" + NameGenerator.generateName(loc)).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SecureChest deserializeChest(Location loc, String folderPath) {
        try {
            String filePath = folderPath + NameGenerator.generateName(loc);
            String json = readFromFile(filePath);
            return gson.fromJson(json, SecureChest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Path createFile(SecureChest chest, String folderPath) {
        String filePath = folderPath + NameGenerator.generateName(chest.location);
        Path newFilePath = Paths.get(filePath);
        try {
            return Files.createFile(newFilePath);
        } catch (FileAlreadyExistsException e) {
            return newFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void writeToFile(String filePath, String value) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] strToBytes = value.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }

    private static String readFromFile(String filePath) throws IOException {
        DataInputStream reader = new DataInputStream(new FileInputStream(filePath));
        int nBytesToRead = reader.available();
        if(nBytesToRead > 0) {
            byte[] bytes = new byte[nBytesToRead];
            int read = reader.read(bytes);
            if(read < 0) {
                reader.close();
                throw new IOException();
            }

            reader.close();
            return new String(bytes);
        } else {
            reader.close();
            throw new IOException();
        }
    }
}
