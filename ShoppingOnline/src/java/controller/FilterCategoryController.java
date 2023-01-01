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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        ArrayList<Category> listCategory = new CategoryDAO().getAllCategory();
        request.setAttribute("listCate", listCategory);
        
        int page = 1;
        final int PAGE_SIZE = 6;
        String rawPage = request.getParameter("page");
        if (rawPage != null) {
            page = Integer.parseInt(rawPage);
        }
        
        int id = Integer.parseInt(request.getParameter("categoryId"));
        int allPage = new ProductDAO().getNumberPageById(id);
        request.setAttribute("page", allPage);
        ArrayList<Product> listProduct = new ProductDAO().getProductWithPagingById(id, page, PAGE_SIZE);
        request.setAttribute("listPro", listProduct);
        String link = "filter-category?categoryId=" + id + "&";
        request.setAttribute("link", link);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
    
}
