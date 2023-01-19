/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;

/**
 *
 * @author Admin
 */
public class OrderDAO {

    public int createAndReturnId(Order order) {
        try {
            String sql = "INSERT INTO [dbo].[Orders]\n"
                    + "           ([account_id]\n"
                    + "           ,[totalPrice]\n"
                    + "           ,[note]\n"
                    + "           ,[created_date]\n"
                    + "           ,[shipping_id])\n"
                    + "     VALUES\n"
                    + "           (?, ?, ?, ?, ?)";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getAccount().getId());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getNote());
            ps.setDate(4, Date.valueOf(order.getDate()));
            ps.setInt(5, order.getShip().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
