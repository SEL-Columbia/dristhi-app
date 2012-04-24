package org.ei.drishti.util;

import android.view.View;
import org.ei.drishti.R;
import org.ei.drishti.activity.DrishtiMainActivity;

public class Wait {
    public static void waitForProgressBarToGoAway(DrishtiMainActivity activity, int numberOfMillisecondsToWait) {
        waitForProgressBarVisibilityToBe(activity, 500, View.VISIBLE);
        if (waitForProgressBarVisibilityToBe(activity, numberOfMillisecondsToWait, View.INVISIBLE)) return;
        throw new RuntimeException("Timed out. Progress bar is still visible after " + numberOfMillisecondsToWait + " milliseconds.");
    }

    private static boolean waitForProgressBarVisibilityToBe(DrishtiMainActivity activity, int numberOfMillisecondsToWait, int expectedVisibility) {
        for (int i = 0; i < numberOfMillisecondsToWait / 100; i++) {
            if (activity.findViewById(R.id.progressBar).getVisibility() == expectedVisibility) {
                return true;
            }

            sleep(100);
        }

        return false;
    }

    public static void waitForFilteringToFinish() {
        sleep(200);
    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

