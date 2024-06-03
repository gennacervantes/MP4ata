/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.captcha_webapp.utils;

/**
 *
 * @author giogen
 */
public interface CryptoInterface {
    
    byte[] encrypt(String message);
    
    String decrypt(byte[] message);
    
}