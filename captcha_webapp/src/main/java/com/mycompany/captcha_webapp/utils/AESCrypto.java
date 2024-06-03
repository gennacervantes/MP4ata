/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.captcha_webapp.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author giogen
 */
public class AESCrypto implements CryptoInterface {

    private SecretKey secretKey;
    private IvParameterSpec spec;

    public AESCrypto() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            secretKey = keyGenerator.generateKey();
            spec = new IvParameterSpec(new byte[16]);
        } catch (Exception e) {
            
        }
    }


    public byte[] encrypt(String message) {
        IvParameterSpec spec = new IvParameterSpec(new byte[16]);
        byte[] encryptedByte = null;

        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            encryptedByte = cipher.doFinal(message.getBytes(Charset.defaultCharset()));

        } catch (Exception e) {

        }
        
        String encryptedString = new String(encryptedByte, StandardCharsets.UTF_8);
        return encryptedByte;

    }

    public String decrypt(byte[] message) {
        byte[] decryptedBytes = null;
        
        try {
            Cipher dCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            dCipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            decryptedBytes = dCipher.doFinal(message);

        } catch (Exception e) {

        }
        
        String decryptedMessage = new String(decryptedBytes, Charset.defaultCharset());
        return decryptedMessage;
    }
}
