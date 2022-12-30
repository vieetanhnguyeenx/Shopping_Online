/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Product;

/**
 *
 * @author Admin
 */
public class ProductDAO {

    /*
    *@desc Used to return a list of categories contained in the database
    *@param No param
    *@returns Return a list of categories
     */
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> productList = new ArrayList<>();
        try {
            String sql = "select id, [name], quantity, price, [description], imageUrl, created_date, category_id from Product";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // get all product and add to product list
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setQuantity(rs.getInt("quantity"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                p.setImageUrl(rs.getString("imageUrl"));
                p.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());

                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                p.setCategory(category);
                productList.add(p);
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }

    public ArrayList<Product> getProductsByCategoryId(int id) {
        ArrayList<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Product WHERE category_id = ?";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setQuantity(rs.getInt("quantity"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                p.setImageUrl(rs.getString("imageUrl"));
                p.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());

                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                p.setCategory(category);
                productList.add(p);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }

    public int getNumberPage() {
        try {
            String sql = "SELECT COUNT(*) FROM Product";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int total = rs.getInt(1);
                int countPage = 0;
                countPage = total / 6;
                if (total % 6 != 0) {
                    countPage++;
                }
                return countPage;
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Product> getProductWithPaging(int page, int size) {
        ArrayList<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Product\n"
                    + "ORDER BY id\n"
                    + "OFFSET ? ROW\n"
                    + "FETCH FIRST ? ROWS ONLY;";
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            int index = (page - 1) * 6;
            ps.setInt(1, index);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();
            // get all product and add to product list
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setQuantity(rs.getInt("quantity"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                p.setImageUrl(rs.getString("imageUrl"));
                p.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());

                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                p.setCategory(category);
                productList.add(p);
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }

    public static void main(String[] args) {
        ArrayList<Product> p = new ProductDAO().getProductWithPaging(1, 6);
        for (Product pro : p) {
            System.out.println(pro.toString());
        }
    }

}
