/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.ShippingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import model.Account;
import model.Cart;
import model.Order;
import model.Shipping;

/**
 *
 * @author Admin
 */
public class CheckOutController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession();
            Map<Integer, Cart> carts = (Map<Integer, Cart>) session.getAttribute("carts");
            if (carts == null) {
                carts = new LinkedHashMap<>();
            }
            
            // caculate total
            double totalMoney = 0;
            for (Map.Entry<Integer, Cart> entry : carts.entrySet()) {
                Integer key = entry.getKey();
                Cart cart = entry.getValue();
                totalMoney += cart.getQuantity() * cart.getProduct().getPrice();
            }
            request.setAttribute("totalMoney", (double) Math.round(totalMoney * 10000) / 10000);
            request.setAttribute("carts", carts);
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String note = request.getParameter("note");
        // Save in data base
        // 1. Shipping
        Shipping shipping = new Shipping();
        shipping.setName(name);
        shipping.setPhone(phone);
        shipping.setAddress(address);
        ShippingDAO shippingDao = new ShippingDAO();
        int shippingId = shippingDao.createAndReturnId(shipping);
        shipping.setId(shippingId);
        
        // 2. Save Order
        Order order = new Order();
        Account account = new Account();
        account.setId(1);
        order.setAccount(account);
        // caculate totoal price 
        HttpSession session = request.getSession();
            Map<Integer, Cart> carts = (Map<Integer, Cart>) session.getAttribute("carts");
            if (carts == null) {
                carts = new LinkedHashMap<>();
            }
            
            // caculate total
            double totalPrice = 0;
            for (Map.Entry<Integer, Cart> entry : carts.entrySet()) {
                Integer key = entry.getKey();
                Cart cart = entry.getValue();
                totalPrice += cart.getQuantity() * cart.getProduct().getPrice();
            }
        order.setTotalPrice(totalPrice);
        order.setNote(note);
        order.setDate(LocalDate.now());
        order.setShip(shipping);
        int orderId = new OrderDAO().createAndReturnId(order);
        order.setId(orderId);
        
        // Save orderdetail
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        orderDetailDAO.saveCart(order, carts);
        response.sendRedirect("thanks-for-purchase");
        session.removeAttribute("carts");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
