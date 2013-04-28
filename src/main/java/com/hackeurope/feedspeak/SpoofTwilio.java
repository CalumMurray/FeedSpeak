/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackeurope.feedspeak;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Neil
 */
@WebServlet(name = "SpoofTwilio", urlPatterns = {"/SpoofTwilio"})
public class SpoofTwilio extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request.setAttribute("From", "+447835218616");
        FeedSpeakServlet feed = new FeedSpeakServlet();
        feed.doPost(request, response);
    }
}