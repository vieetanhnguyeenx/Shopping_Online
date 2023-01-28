/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author Admin
 */
public class AccountDAO {

    public Account login(String username, String password) {
        try {
            String sql = "SELECT id, username, password, displayName, address, "
                    + "email, phone FROM Account WHERE username = ?"
                    + " AND password = ?";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUserName(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setDisplayName(rs.getString("displayName"));
                account.setAddress(rs.getString("address"));
                account.setEmail(rs.getString("email"));
                account.setPhone(rs.getString("phone"));
                return account;
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void main(String[] args) {
        Account acc = new AccountDAO().login("admin", "123456");
        System.out.println(acc);
    }
}
