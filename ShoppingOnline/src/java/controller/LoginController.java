/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

/**
 *
 * @author Admin
 */
public class LoginController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean remember = Boolean.parseBoolean(request.getParameter("remember"));

        // check username and password 
        Account account = new AccountDAO().login(username, password);
        // correct -> save in session
        if (account != null) {
            // remember
            if (remember) {
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(60*60*24*2);
                Cookie passwordCookie = new Cookie("password", password);
                passwordCookie.setMaxAge(60*60*24*2);
                response.addCookie(passwordCookie);
                response.addCookie(usernameCookie);
            }
            // non remember
            request.getSession().setAttribute("account", account);
            response.sendRedirect("home");
            
            
        // not correct -> 
        } else {
            request.setAttribute("error", "Username or password inccorect");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
