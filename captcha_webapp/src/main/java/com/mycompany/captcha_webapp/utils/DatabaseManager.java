/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.captcha_webapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.mycompany.captcha_webapp.models.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giogen
 */
public class DatabaseManager {

    private String mySQLHost = "localhost"; //localhost
    private String mySQLPort = "3306";
    private Connection conn = null;
    private HashMap<String, PreparedStatement> pStatements;

    public DatabaseManager(String username, String password, String vendor, StatementBuilder builder) throws SQLException {
        String url = String.format("jdbc:%s://%s:%s?useSSL=false&allowPublicKeyRetrieval=TRUE", vendor, mySQLHost, mySQLPort);
        
        //?useSSL=false&amp&allowPublicKeyRetrieval=TRUE
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
//        }catch(Exception e){
//            System.out.println(e);
//        }
        conn = DriverManager.getConnection(url, username, password);

        this.pStatements = builder.buildPreparedStatement(conn);
    }

    public User getUser(String username) throws SQLException {
        User user = null;
        PreparedStatement getUser = pStatements.get("getUser");
        getUser.setString(1, username);
        ResultSet rs = getUser.executeQuery();
        if (rs.next()) {
            user = new User(rs.getString("username"), rs.getString("password"), rs.getString("department"), rs.getString("usertype"));
        }
        return user;
    }
    
    public List<User> getUsers() throws SQLException{
        List<User> allUsers = new ArrayList();
        PreparedStatement getUsers = pStatements.get("getUsers");
        ResultSet rs = getUsers.executeQuery();
        while(rs.next()){
            User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("department"), rs.getString("usertype"));
            allUsers.add(user);
        }
        return allUsers;
    }
    
    public boolean addUser(String username, String password, String usertype, String department) throws SQLException{
        int inserted = 0;
        
        User checkUser = getUser(username);
        
        if(checkUser != null){
            return false;
        }
        
        PreparedStatement addUser = pStatements.get("addUser");
        addUser.setString(1, username);
        addUser.setString(2, password);
        addUser.setString(3, usertype);
        addUser.setString(4, department);
        
        inserted = addUser.executeUpdate();
        
        return inserted > 0;
    }
    
    public boolean deleteUser(String username) throws SQLException{
        int deleted = 0;
        
        User checkUser = getUser(username);
        
        if(checkUser == null){
            return false;
        }
        
        PreparedStatement deleteUser = pStatements.get("deleteUser");
        deleteUser.setString(1, username);
        
        deleted = deleteUser.executeUpdate();
        
        return deleted > 0;
    }
}
