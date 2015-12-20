package me.fernandodominguez.galerist.helpers;

/**
 * Created by fernando on 20/12/15.
 */
public class StringsHelper {

    public static String capitalize(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
