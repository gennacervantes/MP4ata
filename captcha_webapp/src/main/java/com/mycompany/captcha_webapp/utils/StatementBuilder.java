/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.captcha_webapp.utils;

/**
 *
 * @author giogen
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public interface StatementBuilder {

    public abstract HashMap<String, PreparedStatement> buildPreparedStatement(Connection conn) throws SQLException;
}