/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackeurope.feedspeak;

/**
 *
 * @author Calum
 */
public class User {
    private int id;
    private String name;
    private String phoneNumber;
    private String oauthToken;
    private String oauthTokenSecret;
    
    private boolean includeTwitter;
    private boolean includeBBC;
    
    public User()
    {
        this(0, "", "", "", "");
    }

    public User(int id, String name, String phoneNumber, String oauthToken, String oauthTokenSecret) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.oauthToken = oauthToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOauthTokenSecret() {
        return oauthTokenSecret;
    }

    public void setOauthTokenSecret(String oauthTokenSecret) {
        this.oauthTokenSecret = oauthTokenSecret;
    }

    public boolean isIncludeTwitter() {
        return includeTwitter;
    }

    public void setIncludeTwitter(boolean includeTwitter) {
        this.includeTwitter = includeTwitter;
    }

    public boolean isIncludeBBC() {
        return includeBBC;
    }

    public void setIncludeBBC(boolean includeBBC) {
        this.includeBBC = includeBBC;
    }
    
    
}
