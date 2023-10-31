package com.project.passwordSecurity.myUtil;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Character;

public class MenuGeneratorPassword {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Mehod yang berfungsi mengantipasi ketika user seharusnya memasukan angka, tetapi huruf.
     * @return angka;
     */
    public static int setDigit() {
        char i = scanner.next().charAt(0);
        while (true) {
            if (Character.isDigit(i)) {
                return Character.getNumericValue(i);
            }
            System.out.println("Input hanya boleh number");
            i = scanner.next().charAt(0);
        }
    }

    /**
     * Method yang berfungsi untuk mendapatkan panjang password
     * @return length of password
     */
    static int getPasswordLength() {
        int length;
        do {
            System.out.print("Masukkan jumlah input length: ");
            length = setDigit();
            if (length == 0) {
                System.out.println("Input tidak boleh 0! Silakan coba lagi.");
            }
        } while (length == 0);
        return length;
    }

    /**
     * Method yang berfungsi mengecek apakah jawaban sudah benar (y/n)
     * @return true or false
     */
    static boolean isValidChoice() {
        while (true) {
            String ans = scanner.next();
            if (ans.equals ("y")) return true;
            if (ans.equals ("n")) return false;
            System.out.println ("Input hanya boleh \"y\" atau \"n\"!, silahkan coba lagi.");
            System.out.print("> ");
        }
    }

    /**
     * Method yang berfungsi untuk menanyakan apakah mengizinkan lowercase saat menggenerator password
     * @return true or false
     */
    static boolean isLowercaseAllowed() {
        System.out.print("Apakah Anda mengizinkan lowercase sebagai output? (y/n) : ");
        return isValidChoice();
    }

    /**
     * Method yang berfungsi untuk menanyakan apakah mengizinkan uppercase saat menggenerator password
     * @return true or false
     */
    static boolean isUppercaseAllowed() {
        System.out.print("Apakah anda mengizinkan uppercase sebagai output? (y/n) : ");
        return isValidChoice();
    }

        /**
     * Method yang berfungsi untuk menanyakan apakah mengizinkan number saat menggenerator password
     * @return true or false
     */
    static boolean isNumberAllowed() {
        System.out.print("Apakah anda mengizinkan number sebagai output? (y/n) : ");
        return isValidChoice();
    }

    /**
     * Method yang berfungsi untuk menanyakan apakah mengizinkan symbols saat menggenerator password
     * @return true or false
     */
    static boolean isSymbolsAllowed() {
        System.out.print("Apakah anda mengizinkan symbols sebagai output? (y/n) : ");
        return isValidChoice();
    }

    /**
     * Method yang berfungsi untuk mengecek apakah input dari user sudah symbols atau bukan
     * @param symbols adalah parameter yang akan dimasukan user
     * @return true or false
     */
    static boolean isSymbols(String symbols) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(symbols);
        boolean isStringContainsSpecialCharacter = matcher.find();
        if (isStringContainsSpecialCharacter) return true;
        return false;
    }

    /**
     * Method yang berfungsi untuk user memasukan input symbols
     * @return symbols
     */
    static String setSymbols() {
        System.out.print("Masukan input symbols yang anda inginkan : ");
        String symbols = scanner.nextLine();
        while(!isSymbols(symbols)) {
            System.out.println("Input hanya boleh symbols!!");
            symbols = scanner.nextLine();
        }
        return symbols;
    }

    /**
     * Method yang berfungsi untuk menanyakan ke user apakah ingin memodifikasi symbols
     * @return true or false
     */
    static boolean isSymbolChanged() {
        System.out.print("Apakah anda ingin memodifikasi Symbols : (y/n) ");
        return isValidChoice();
    }

    /**
     * Metod yang menampilkan menu generator password
     * @return password
     */
    public static String passwordGeneratorMenu() {
        int length;
        String symbols = "null", password;
        boolean isLowercase, isUppercase, isNumbers, isSymbols, isSymbolChanged = false;
        System.out.println("> masukan input dengan benar pada pertanyaan berikut ini!!");
        length = getPasswordLength();
        isLowercase = isLowercaseAllowed();
        isUppercase = isUppercaseAllowed();
        isNumbers = isNumberAllowed();
        isSymbols = isSymbolsAllowed();
        if (isSymbols) isSymbolChanged = isSymbolChanged();
        if (isSymbolChanged) {
            symbols = setSymbols();
            PasswordGenerator.setSymbol_Characters(symbols);
            password = PasswordGenerator.generatePassword(length, isLowercase, isUppercase, isNumbers, isSymbols);
        } else {
            password = PasswordGenerator.generatePassword(length, isLowercase, isUppercase, isNumbers, isSymbols);
        }
        System.out.println("Password sukses digenerate = " + password);
        return password;
    }

}
