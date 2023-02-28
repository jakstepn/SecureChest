package io.github.jakstepn.Passwords;

import io.github.jakstepn.Models.SecureChest;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PasswordManager {

    private static final String separator = ",";

    public static boolean isPasswordValid(SecureChest chest, int[] input) {
        String[] password = chest.password.split(separator);
        if(password.length != input.length) return false;

        for (int i = 0; i < password.length; i++) {
            if(Integer.parseInt(password[i]) != input[i]) return false;
        }

        return true;
    }

    public static String mapToString(int[] password) {
        String[] pass = Arrays.stream(password).mapToObj(String::valueOf).toArray(String[]::new);
        return String.join(",", pass);
    }
}
