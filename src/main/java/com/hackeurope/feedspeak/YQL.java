/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackeurope.feedspeak;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;
import twitter4j.api.*;

/**
 *
 * @author Calum
 */
public class YQL {
    
    private static void initLogFile() {
        try {
            Handler fileHandler = new FileHandler("/tmp/log");
            Logger.getLogger("").addHandler(fileHandler);

        } catch (IOException ex) {
            Logger.getLogger(YQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(YQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<String> getTweets(){
        initLogFile();
        Logger.getLogger(YQL.class.getName()).log(Level.INFO, "getting tweets");
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("aTR1FAEsR0hAj9w47ko9Tg")
        .setOAuthConsumerSecret("XDSn1TTobWDBy46gZAfm6ya2kYkmli30B2vD2ixxpMA")
        .setOAuthAccessToken("1110145321-wYAlo6r3Oms2gHMlK8yRyqfPTd93oWCXqbU4M8C")
        .setOAuthAccessTokenSecret("WUlVHwaAis8eln16vWSajb4yTsPo0vaGs0PjgHmUo");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        
        List<String> packagedStatus = null;
        try {
            User user = twitter.verifyCredentials();
            
            List<Status> statuses = twitter.getHomeTimeline();
            
            for (Status status : statuses) {
                packagedStatus.add(status.getUser().getScreenName() + " says: " + status.getText());
            }

        } catch (TwitterException ex) {
            Logger.getLogger(YQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return packagedStatus;
    }
}
