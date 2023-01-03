package com.bubbles.bubbles_backend.utils;

public class MessageType {
    public static int TEXT = 0;
    public static int FILE = 1;
    public static int IMAGE = 2;
    public static boolean isValid(int type){
        return type == FILE || type == IMAGE;
    }
}
