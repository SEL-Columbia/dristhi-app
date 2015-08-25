package org.ei.telemedicine.view.controller;

import android.content.Intent;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.view.activity.ANCDetailActivity;
import org.ei.telemedicine.view.activity.ChildDetailActivity;
import org.ei.telemedicine.view.activity.EligibleCoupleDetailActivity;
import org.ei.telemedicine.view.activity.NativeOverviewActivity;
import org.ei.telemedicine.view.activity.PNCDetailActivity;

import static org.ei.telemedicine.AllConstants.CASE_ID;
import static org.ei.telemedicine.AllConstants.VISIT_TYPE;

public class ProfileNavigationController {

    public static void navigateToProfile(android.content.Context context, String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), NativeOverviewActivity.class);
        intent.putExtra(CASE_ID, caseId);
        context.startActivity(intent);
    }

    public static void navigateToECProfile(android.content.Context context, String caseId) {
//        Intent intent = new Intent(context.getApplicationContext(), EligibleCoupleDetailActivity.class);
        Intent intent = new Intent(context.getApplicationContext(), NativeOverviewActivity.class);
        intent.putExtra(CASE_ID, caseId);
        intent.putExtra(VISIT_TYPE, "ec");
        context.startActivity(intent);
    }

    public static void navigateToANCProfile(android.content.Context context, String caseId) {
//        Intent intent = new Intent(context.getApplicationContext(), ANCDetailActivity.class);
        Intent intent = new Intent(context.getApplicationContext(), NativeOverviewActivity.class);
        intent.putExtra(CASE_ID, caseId);
        intent.putExtra(VISIT_TYPE, "anc");
        context.startActivity(intent);
    }

    public static void navigateToPNCProfile(android.content.Context context, String caseId) {
//        Intent intent = new Intent(context.getApplicationContext(), PNCDetailActivity.class);
        Intent intent = new Intent(context.getApplicationContext(), NativeOverviewActivity.class);
        intent.putExtra(CASE_ID, caseId);
        intent.putExtra(VISIT_TYPE, "pnc");
        context.startActivity(intent);
    }

    public static void navigateToChildProfile(android.content.Context context, String caseId) {
//        Intent intent = new Intent(context.getApplicationContext(), ChildDetailActivity.class);
        Intent intent = new Intent(context.getApplicationContext(), NativeOverviewActivity.class);
        intent.putExtra(CASE_ID, caseId);
        intent.putExtra(VISIT_TYPE, "child");
        context.startActivity(intent);
    }

}
