package org.ei.opensrp.indonesia.util;

import com.google.common.base.Strings;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.replace;

/**
 * Created by Dimas Ciputra on 9/14/15.
 */
public class StringUtil extends org.ei.opensrp.util.StringUtil {

    public static String getValueFromNumber(String value) { return Strings.isNullOrEmpty(value) ? "0" : value; }
    public static String humanize(String value) {
        return capitalize(replace(value, "_", " "));
    }

}
