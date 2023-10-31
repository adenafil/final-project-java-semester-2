package com.project.passwordSecurity.myUtil;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PasswordGenerator {
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz", UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", NUMBER_CHARACTERS = "0123456789";
    private static String symbol_Characters = "!@#$%^&*()-=_+[]{}|;:.<>?";

    /**
     * Method yang berfungsi memasukan symbols yang diinginkan
     * @param symbol_Characters berisi inputan symbols dari user
     */
    public static void setSymbol_Characters(String symbolChanged) {
        symbol_Characters = symbolChanged;
    }

    /**
     * Method yang berfungsi menggenerator password
     * @param length adalah jumlah dari length password
     * @param isLowercaseAllowed berfungsi apakah ingin memasukan lowerCase pada proses penggeneratoran password
     * @param isUppercaseAllowed berfungsi apakah ingin memasukan upperCase pada proses penggeneratoran password
     * @param isNumberAllowed berfungsi apakah ingin memasukan Number pada proses penggeneratoran password
     * @param isSymbolAllowed berfungsi apakah ingin memasukan symbol pada proses penggeneratoran password
     * @return sebuah password yang sudah digenerate
     */
    public static String generatePassword(int length, boolean isLowercaseAllowed ,  boolean isUppercaseAllowed, boolean isNumberAllowed ,boolean isSymbolAllowed) {
        StringBuilder password = new StringBuilder();
        String characters = "";
        
        if (isLowercaseAllowed) characters += LOWERCASE_CHARACTERS;
        if (isUppercaseAllowed) characters += UPPERCASE_CHARACTERS;
        if (isNumberAllowed) characters += NUMBER_CHARACTERS;
        if (isSymbolAllowed) characters += symbol_Characters;

        Random random = new Random();
        Set<Character> usedCharacters = new HashSet<>();

        while (password.length() < length) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            // Validasi karakter ganda
            if (usedCharacters.contains(randomChar)) {
                continue;
            }
            // Validasi karakter berikutnya tidak sama
            if (password.length() > 0 && password.charAt(password.length() - 1) == randomChar) {
                continue;
            }
            password.append(randomChar);
            usedCharacters.add(randomChar);
        }
        return password.toString();
    }

}
