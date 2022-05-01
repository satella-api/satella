package dev.leandro.erllet.satella.util;

import lombok.val;

import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern PATTTERN = Pattern.compile("[^a-zA-Z0-9_]+");


    public static boolean validate(String name) {
        if (name.length() > 16) {
            return false;
        }
        val matcher = PATTTERN.matcher(name);
        return !matcher.find();
    }
}
