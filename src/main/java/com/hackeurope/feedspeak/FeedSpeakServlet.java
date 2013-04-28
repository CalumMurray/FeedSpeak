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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Calum
 */
@WebServlet(name = "FeedSpeakServlet", urlPatterns = {"/tweets"})
public class FeedSpeakServlet extends HttpServlet {

//    public static final String ACCOUNT_SID = "AC.....";
//    public static final String AUTH_TOKEN = ".......";
    private HashMap<String, String> callers = new HashMap<String, String>();
    private ConcreteDBConnector dbConnection = new ConcreteDBConnector();
    private TwiMLResponse twimlResponse;

    private String fromNumber;
    private User user;
    private List<String> preferredSources = new ArrayList<String>();;

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
        //doPost(request, response);
        //DatabaseConnector db = new DatabaseConnector();
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
        //initLogFile();

        // Create a dict of people we know.
        //TODO: UserAccounts with mapping to source feed preferences in SQLite?
//        callers.put("+447933298892", "Calum");
//        callers.put("+447951751012", "Jack");
//        callers.put("+447835218616", "Neil");
        //...
        
        //Get caller's number
        fromNumber = request.getParameter("From");
        fromNumber = fromNumber.replace("+44", "0");
        user = dbConnection.getUserByPhoneNumber(fromNumber);
        
        if (user != null)
            preferredSources = dbConnection.getUsersSources(user);
        
        twimlResponse = new TwiMLResponse();

        try {
            formulateSayMessage();
        } catch (Exception e) {
            Logger.getLogger(FeedSpeakServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        //Send response back to Twilio Server
        response.setContentType("application/xml");
        response.getWriter().print(twimlResponse.toXML());

    }

    private void formulateSayMessage() {

        if (preferredSources.contains("twitter"))
        {
            appendTweets();
        }
        if (preferredSources.contains("bbc"))
        {    
            appendBBC();
        }   
        if (preferredSources.contains("sport"))
        {    
            appendSports();
        }   
        //TODO: Other feeds...
    }

//    private String getUsersTweets() {
//
//        String tweetMessage = "";
//        List<String> tweets = TwitterFeed.getTweets();
//        for (String tweet : tweets) {
//            tweetMessage += tweet + " ";
//        }
//        return tweetMessage;
//    }
//
//    private String getUsersBBCNewsFeed() {
//        String bbcMessage = "";
//        List<String> headlines = BBC.getNewsHeadlines();
//        for (String headline : headlines) {
//            bbcMessage += headline + " ";
//        }
//        return bbcMessage;
//    }
    
    private void appendSports(){
        
        Verb saySportsVerb = new Verb("Say", "Yahoo Soccer Headlines for " + user.getName() + ": ");
        saySportsVerb.set("name", "en-gb");
        
        try {
            twimlResponse.append(saySportsVerb);
            List<String> headlines = BBC.getNewsHeadlines();
            appendList(saySportsVerb, headlines);
            } catch (TwiMLException ex) 
            {
                System.err.println("Problem appending SAY verb(s) to TwiMLResponse");
            }
    }
    
    

    private void appendBBC() {
        Verb sayBBCVerb = new Verb("Say", "BBC Headlines for " + user.getName() + ": ");// + getUsersTweets()/*"Hey " + name + ", these are your personal feeds. I've got a longer message now.  I wonder how long I can make this message.  Am I still going?  This is crazy! Tested some punctuation as well."*/);
        sayBBCVerb.set("name", "en-gb");
        try {
            twimlResponse.append(sayBBCVerb);

            List<String> headlines = BBC.getNewsHeadlines();

            appendList(sayBBCVerb, headlines);

        } catch (TwiMLException ex) {
            System.err.println("Problem appending SAY verb(s) to TwiMLResponse");
        }
    }

    private void appendTweets() {
        Verb sayTweetsVerb = new Verb("Say", "Tweets for " + user.getName() + ": ");// + getUsersTweets()/*"Hey " + name + ", these are your personal feeds. I've got a longer message now.  I wonder how long I can make this message.  Am I still going?  This is crazy! Tested some punctuation as well."*/);
        sayTweetsVerb.set("language", "en");

        try {
            twimlResponse.append(sayTweetsVerb);

            List<String> tweets = TwitterFeed.getTweets();
            appendList(sayTweetsVerb, tweets);

        } catch (TwiMLException ex) {
            System.err.println("Problem appending SAY verb(s) to TwiMLResponse");
        }
    }

    private void appendList(Verb verb, List<String> list) throws TwiMLException {
        boolean maleVoice = true;

        for (String tweet : list) {
            verb = new Verb("Say", StringEscapeUtils.escapeXml(tweet));
            verb.set("language", "en");

            if (maleVoice) {
                verb.set("voice", "man");
            } else {
                verb.set("voice", "woman");
            }
            maleVoice = !maleVoice;

            twimlResponse.append(verb);
        }
    }
}
