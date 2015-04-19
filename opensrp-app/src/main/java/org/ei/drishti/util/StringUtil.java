package org.ei.drishti.util;

import org.apache.commons.lang3.text.WordUtils;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.upperCase;

public class StringUtil {
    public static String humanize(String value) {
        return capitalize(replace(value, "_", " "));
    }

    public static String replaceAndHumanize(String value, String oldCharacter, String newCharacter) {
        return humanize(replace(value, oldCharacter, newCharacter));
    }

    public static String replaceAndHumanizeWithInitCapText(String value, String oldCharacter, String newCharacter) {
        return humanize(WordUtils.capitalize(replace(value, oldCharacter, newCharacter)));
    }

    public static String humanizeAndDoUPPERCASE(String value) {
        return upperCase(humanize(value));
    }

}