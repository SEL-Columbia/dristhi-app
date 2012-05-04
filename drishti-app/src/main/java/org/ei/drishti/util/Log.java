package org.ei.drishti.util;

import static android.util.Log.i;
import static android.util.Log.v;
import static android.util.Log.w;

public class Log {
    public static void logVerbose(String message) {
        v("DRISHTI", message);
    }

    public static void logInfo(String message) {
        i("DRISHTI", message);
    }

    public static void logWarn(String message) {
        w("DRISHTI", message);
    }
}
