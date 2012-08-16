package org.ei.drishti.util;

import android.app.Activity;
import android.view.View;
import org.ei.drishti.service.NavigationService;

public class Wait {
    public static void waitForProgressBarToGoAway(Activity activity) {
        waitForProgressBarToGoAway(activity, 2000);
    }

    public static void waitForProgressBarToGoAway(Activity activity, int numberOfMillisecondsToWait) {
        waitForProgressBarVisibilityToBe(activity, 500, View.VISIBLE);
        if (waitForProgressBarVisibilityToBe(activity, numberOfMillisecondsToWait, View.INVISIBLE)) return;
        throw new RuntimeException("Timed out. Progress bar is still visible after " + numberOfMillisecondsToWait + " milliseconds.");
    }

    private static boolean waitForProgressBarVisibilityToBe(Activity activity, int numberOfMillisecondsToWait, int expectedVisibility) {
        return true;
    }

    public static void waitForFilteringToFinish() {
        sleep(400);
    }

    public static void waitForLoginActivityToFinish(FakeNavigationService navigationService) {
        for (int i = 0; i < 10; i++) {
            if (navigationService.isAtHome()) {
                return;
            }
            sleep(400);
        }
        if (!navigationService.isAtHome()) {
            throw new RuntimeException("Never came home!");
        }
    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

