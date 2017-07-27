package br.com.zone.controle.financeiro.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author daniel
 */
public class PasswordUtil {

    public static String encriptar(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        
        return hexString.toString();

    }
        
}
