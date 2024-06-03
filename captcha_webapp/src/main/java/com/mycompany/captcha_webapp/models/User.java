/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.captcha_webapp.models;

import com.mycompany.captcha_webapp.utils.AESCrypto;

/**
 *
 * @author giogen
 */
public class User {

    private String username;
    private String password;
    private String department;
    private String usertype;

    public User(String username, String password, String department, String usertype){
        this.username = username;
        this.password = password;
        this.department = department;
        this.usertype = usertype;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
    
    public byte[] getEncryptedPassword() {
        AESCrypto crypto = new AESCrypto();
        byte[] encryptedPassword = crypto.encrypt(password);
        return encryptedPassword;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getUsertype() {
        return this.usertype;
    }

}
