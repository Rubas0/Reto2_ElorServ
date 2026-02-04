package com.elorrieta.encriptado;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Criptografia Simetrica (Clave Secreta)
 * 
 * Esta clase permite cifrar credenciales de email mediante una clave secreta y
 * las guarda en un fichero. La unica forma de descifrar es mediante dicha
 * clave, que tanto el emisor como el receptor la deben conocer.
 * 
 * Algoritmo AES

 */
public class CryptAES {

    // Fijate que el String es de exactamente 16 bytes
    private static byte[] salt = "esta es la salt!".getBytes();

    private static final String FILE_PATH = "src/main/resources/config/mail_config.txt";

    /**
     * Cifra las credenciales con AES, modo CBC y padding PKCS5Padding (simetrica)
     * y las guarda en un fichero
     * 
     * @param clave       La clave del usuario
     * @param credenciales El mensaje a cifrar (username|||password)
     * @return Mensaje cifrado
     */
    public String cifrarCredenciales(String clave, String credenciales) {
        String ret = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        FileOutputStream fileOutputStream = null;
        try {

            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encodedMessage = cipher.doFinal(credenciales.getBytes()); // Mensaje cifrado !!!
            byte[] iv = cipher.getIV(); // vector de inicializacion por modo CBC

            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combined = concatArrays(iv, encodedMessage);

            // Crear directorio si no existe
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            fileOutputStream = new FileOutputStream(FILE_PATH);
            fileOutputStream.write(combined);

            ret = new String(encodedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Descifra las credenciales con AES, modo CBC y padding PKCS5Padding
     * (simetrica) y las retorna
     * 
     * @param clave La clave del usuario
     */
    public String descifrarCredenciales(String clave) {
        String ret = null;
        try {
            // Fichero leido
            File fichero = new File(FILE_PATH);
            byte[] fileContent = Files.readAllBytes(fichero.toPath());
            KeySpec keySpec = null;
            SecretKeyFactory secretKeyFactory = null;

            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16)); // La IV esta aqui
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Retorna una concatenacion de ambos arrays
     * 
     * @param array1
     * @param array2
     * @return Concatenacion de ambos arrays
     */
    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    public static void main(String[] args) {
        CryptAES cryptAES = new CryptAES();
        
        // Credenciales reales
        String username = "yeray.fermosomo@elorrieta-errekamari.com";
        String password = "dcug nupc tgty rrko";
        String credenciales = username + "|||" + password;
        
        System.out.println("Credenciales Originales! -> " + credenciales);
        System.out.println("-----------");
        String credencialesCifradas = cryptAES.cifrarCredenciales("Clave", credenciales);
        System.out.println("Credenciales Cifradas! -> " + credencialesCifradas);
        System.out.println("-----------");
        System.out.println("Credenciales Descifradas! -> " + cryptAES.descifrarCredenciales("Clave"));
        System.out.println("-----------");
    }
}