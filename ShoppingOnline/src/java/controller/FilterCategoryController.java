/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Category;
import model.Product;

/**
 *
 * @author Admin
 */
public class FilterCategoryController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        ArrayList<Category> listCategory = new CategoryDAO().getAllCategory();
        req.setAttribute("listCate", listCategory);
        
        int id = Integer.parseInt(req.getParameter("categoryId"));
        ArrayList<Product> listProduct = new ProductDAO().getProductsByCategoryId(id);
        req.setAttribute("listPro", listProduct);
        
        req.getRequestDispatcher("home.jsp").forward(req, resp);
    }
    
}
