package com.warehouse.util;

import java.util.Base64;

public class Util {

    public static String getFormattedStars(int stars) {
        StringBuilder formatted = new StringBuilder();
        for( int i=0; i < stars; i++){
            formatted.append( "*");
        }
        return formatted.toString();
    }

    public static String getBasicAuth( String user, String password){

        StringBuilder sb = new StringBuilder("Basic ");

        String token = Base64.getUrlEncoder().encodeToString(  ( user + ":" + password).getBytes());
        sb.append( token);
        System.out.println( sb);

        return sb.toString();
    }

    public static void main(String[] args) {
        getBasicAuth("user","user");
    }
}
