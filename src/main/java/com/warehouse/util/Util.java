package com.warehouse.util;

public class Util {

    public static String getFormattedStars(int stars) {
        StringBuilder formatted = new StringBuilder();
        for( int i=0; i < stars; i++){
            formatted.append( "*");
        }
        return formatted.toString();
    }
}
