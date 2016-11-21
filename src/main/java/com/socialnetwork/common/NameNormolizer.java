package com.socialnetwork.common;

/**
 * Created by Vasiliy Bobkov on 21.11.2016.
 */
public abstract class NameNormolizer {
    public static String normolize(String name) {
        name = name.toLowerCase();
        String[] subNames = name.split("-");
        name = Character.toString(subNames[0].charAt(0)).toUpperCase() + subNames[0].substring(1);
        for (int i = 1; i < subNames.length; i++) {
            name += "-" + Character.toString(subNames[i].charAt(0)).toUpperCase() + subNames[i].substring(1);
        }
        return name;
    }
}