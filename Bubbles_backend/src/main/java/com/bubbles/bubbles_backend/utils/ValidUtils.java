package com.bubbles.bubbles_backend.utils;

public class ValidUtils {
    public static boolean isValid(String s){
        if(s == null || s.length() == 0)
            return false;
        return true;
    }
    public static boolean isValid(int n){
        return n != 0;
    }
    public static boolean isValid(Object o){
        return o != null;
    }
}
