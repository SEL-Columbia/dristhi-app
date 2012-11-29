package org.ei.drishti.view.controller;

import android.content.Intent;
import org.ei.drishti.view.activity.ANCDetailActivity;
import org.ei.drishti.view.activity.ChildDetailActivity;
import org.ei.drishti.view.activity.EligibleCoupleDetailActivity;

import static org.ei.drishti.AllConstants.CASE_ID;

public class ProfileNavigationController {

    public static void navigateToECProfile(android.content.Context context, String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), EligibleCoupleDetailActivity.class);
        intent.putExtra(CASE_ID, caseId);
        context.startActivity(intent);
    }

    public static void navigationToANCProfile(android.content.Context context, String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), ANCDetailActivity.class);
        intent.putExtra(CASE_ID, caseId);
        context.startActivity(intent);
    }

    public static void navigationToChildProfile(android.content.Context context, String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), ChildDetailActivity.class);
        intent.putExtra(CASE_ID, caseId);
        context.startActivity(intent);
    }
}
