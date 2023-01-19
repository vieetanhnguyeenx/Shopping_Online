/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Order;

/**
 *
 * @author Admin
 */
public class OrderDetailDAO {

    public void saveCart(Order order, Map<Integer, Cart> carts) {
        try {
            String sql = "INSERT INTO [dbo].[OrderDetail]\n"
                    + "           ([order_id]\n"
                    + "           ,[productName]\n"
                    + "           ,[productImage]\n"
                    + "           ,[productPrice]\n"
                    + "           ,[quantity])\n"
                    + "     VALUES\n"
                    + "           (?, ?, ?, ?, ?)";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            for (Map.Entry<Integer, Cart> entry : carts.entrySet()) {
                Integer key = entry.getKey();
                Cart cart = entry.getValue();
                ps.setInt(1, order.getId());
                ps.setString(2, cart.getProduct().getName());
                ps.setString(3, cart.getProduct().getImageUrl());
                ps.setDouble(4, cart.getProduct().getPrice());
                ps.setInt(5, cart.getQuantity());
                ps.executeUpdate();
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
