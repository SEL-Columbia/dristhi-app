package org.ei.opensrp.indonesia.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;

import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.opensrp.view.dialog.DialogOptionModel;
import org.ei.opensrp.view.dialog.SmartRegisterDialogFragment;

/**
 * Created by Dimas Ciputra on 3/8/15.
 */
public abstract class BidanSecuredNativeSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private static final String DIALOG_TAG = "dialog";

    @Override
    protected void showFragmentDialog(DialogOptionModel dialogOptionModel, Object tag) {
        if (dialogOptionModel.getDialogOptions().length <= 0) {
            return;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        SmartRegisterDialogFragment
                .newInstance(this, dialogOptionModel, tag)
                .show(ft, DIALOG_TAG);
    }

}
