/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackeurope.feedspeak;

import com.twilio.sdk.verbs.TwiMLException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Twilio imports
import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.Verb;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calum
 */
@WebServlet(name = "FeedSpeakServlet", urlPatterns = {"/tweets"})
public class FeedSpeakServlet extends HttpServlet {

    public static final String ACCOUNT_SID = "AC.....";
    public static final String AUTH_TOKEN = ".......";
    private HashMap<String, String> callers = new HashMap<String, String>();
    private TwiMLResponse twimlResponse;
    private String message;
    private String fromNumber;
    private String callerName;

    private static void initLogFile() {
        try {
            Handler fileHandler = new FileHandler("/tmp/logServlet");
            Logger.getLogger("").addHandler(fileHandler);

        } catch (IOException ex) {
            Logger.getLogger(FeedSpeakServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(FeedSpeakServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initLogFile();
        
        // Create a dict of people we know.
        //TODO: UserAccounts with mapping to source feed preferences in SQLite?
        callers.put("+447933298892", "Calum");
        callers.put("+447951751012", "Jack");
        //...

        //Get caller's number
        fromNumber = request.getParameter("From");
        callerName = callers.get(fromNumber);

        twimlResponse = new TwiMLResponse();

        try{
            formulateSayMessage();
        }catch(Exception e){
            Logger.getLogger(FeedSpeakServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        //Send response back to Twilio Server
        response.setContentType("application/xml");
        response.getWriter().print(twimlResponse.toXML());

    }

    private void formulateSayMessage() {
        String name;

        if (callerName == null) {
            // Use a generic name - No user account
            name = "Monkey";
        } else {
            // Use the caller's name - Existing customer
            name = callerName;
        }

        Verb sayTweetsVerb = new Verb("Say", "Tweets for " + name + ": " + getUsersTweets());// + getUsersTweets()/*"Hey " + name + ", these are your personal feeds. I've got a longer message now.  I wonder how long I can make this message.  Am I still going?  This is crazy! Tested some punctuation as well."*/);
        //TODO: Other feeds...getUsersBBCNewsFeed
        Verb sayBBCVerb = new Verb("Say", "BBC News Feed for " + name + ": " + getUsersBBCNewsFeed());

        try {
            twimlResponse.append(sayTweetsVerb);
            twimlResponse.append(sayBBCVerb);
            //TODO: Other feeds...
        } catch (TwiMLException ex) {
            System.err.println("Problem appending SAY verb(s) to TwiMLResponse");
        }
    }

    private String getUsersTweets() {

        String tweetMessage = "";
        List<String> tweets = YQL.getTweets();
        for (String tweet : tweets) {
            tweetMessage += tweet + " ";
        }
        return tweetMessage;
    }
    
    private String getUsersBBCNewsFeed() {
        String bbcMessage = "";
        List<String> headlines = BBC.getNewsHeadlines();
        for (String headline : headlines) {
            bbcMessage += headline + " ";
        }
        return bbcMessage;
    }
}
