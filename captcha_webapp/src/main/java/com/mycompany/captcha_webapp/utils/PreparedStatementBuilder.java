/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.captcha_webapp.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author giogen
 */
public class PreparedStatementBuilder implements StatementBuilder {
    
    public HashMap<String, PreparedStatement> buildPreparedStatement(Connection conn) throws SQLException {
        
        HashMap<String, PreparedStatement> pStatements = new HashMap<>();
        
//        String getStudentSql = "SELECT * from ics2609.student where student_no = ?";
//        PreparedStatement getStudent = conn.prepareStatement(getStudentSql);
//        pStatements.put("getStudent", getStudent);
        
        // get users
        String getUsersSql = "SELECT * from mp4.users";
        PreparedStatement getUsers = conn.prepareStatement(getUsersSql);
        pStatements.put("getUsers", getUsers);
        
        // get user
        String getUserSql = "SELECT * from mp4.users where username = ?";
        PreparedStatement getUser = conn.prepareStatement(getUserSql);
        pStatements.put("getUser", getUser);
        
        // add user
        String addUserSql = "INSERT INTO mp4.users  VALUES(?, ?, ?, ?)";
        PreparedStatement addUser = conn.prepareStatement(addUserSql);
        pStatements.put("addUser", addUser);
        
        // delete user
        String deleteUserSql = "DELETE FROM mp4.users WHERE username = ?";
        PreparedStatement deleteUser = conn.prepareStatement(deleteUserSql);
        pStatements.put("deleteUser", deleteUser);
        
        return pStatements;
    }
}
