package com.msb;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    static Properties props = new Properties();

    static{
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object get(String key){
        if(props == null ) return null;
        return props.get(key);
    }

    public static int getIntValue(String key) {
        Object obj = get(key);
        if (obj == null) {
            System.err.println("Key not found: " + key);
            return 0; // Default value or throw an exception
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer for key: " + key + ", value: " + obj);
            return 0; // Default value or throw an exception
        }
    }

//    // Optional method to provide a default value if the key is missing or invalid
//    public static int getIntValueOrDefault(String key, int defaultValue) {
//        Object obj = get(key);
//        if (obj == null) {
//            System.err.println("Key not found: " + key + ". Returning default value: " + defaultValue);
//            return defaultValue;
//        }
//        try {
//            return Integer.parseInt(obj.toString());
//        } catch (NumberFormatException e) {
//            System.err.println("Invalid integer for key: " + key + ", value: " + obj + ". Returning default value: " + defaultValue);
//            return defaultValue;
//        }
//    }

    public static void main(String[] args) {
        System.out.println(PropertyMgr.get("initTankCount"));
    }
}
