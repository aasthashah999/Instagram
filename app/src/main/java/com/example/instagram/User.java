package com.example.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {
    private static final String username = "username";
    private static final String profileImage = "profileImage";

    public void setUsername(String usernameParse){
        put("username", usernameParse);
    }

    public String getUsername(){
        return getString("username");
    }

    public void setProfileImage(ParseFile file){
        put("profileImage", file);
    }

    public ParseFile getProfileImage(){
        return getParseFile("profileImage");
    }
}
