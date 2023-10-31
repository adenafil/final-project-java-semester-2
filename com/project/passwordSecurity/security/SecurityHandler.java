package com.project.passwordSecurity.security;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class SecurityHandler {
    private static final String SECRET_KEY = "projectPemrogramanJava";
    private static final String SALT = "informatikaUISI";

    /**
     * Method yang berfungsi untuk mengenkripsi data di database
     * @param strToEncrypt data yang akan dienkripsi
     * @return hasil enkripsi
     */
    public static String ToEncrypt(String strToEncrypt) {
        try {
            //Generate a random IV 
            byte[] iv = new byte[16]; // 16 bytes for AES-128, change to 32 for AES-256
            // result = [16 arrays]
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);
           // System.out.println(Arrays.toString(iv));
            // result = [16 arrays but filled random number]
            IvParameterSpec ivspec = new IvParameterSpec(iv); // iv that has been filled with random array will be put in Ivspec

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] combinedBytes = new byte[iv.length + encryptedBytes.length];
            
            System.arraycopy(iv, 0, combinedBytes, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combinedBytes, iv.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combinedBytes);
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
     * Method yang berfungsi untuk mengdekripsi data di database
     * @param strToEncrypt data yang akan dekripsi
     * @return hasil dekripsi
     */
    public static String ToDecrypt(String strToDecrypt) {
        try {
            byte[] combinedBytes = Base64.getDecoder().decode(strToDecrypt);
            byte[] iv = new byte[16]; // 16 bytes for AES-128, change to 32 for AES-256
            byte[] encryptedBytes = new byte[combinedBytes.length - iv.length];
            System.arraycopy(combinedBytes, 0, iv, 0, iv.length);
            System.arraycopy(combinedBytes, iv.length, encryptedBytes, 0, encryptedBytes.length);

            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
}
