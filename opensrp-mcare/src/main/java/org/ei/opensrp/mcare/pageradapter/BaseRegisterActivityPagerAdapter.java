package org.ei.opensrp.mcare.pageradapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.ei.opensrp.view.fragment.DisplayFormFragment;

/**
 * Created by koros on 11/2/15.
 */
public class BaseRegisterActivityPagerAdapter extends FragmentPagerAdapter {
    public static final String ARG_PAGE = "page";
    String[] dialogOptions;
    Fragment mBaseFragment;
<<<<<<< HEAD
=======
    Fragment mProfileFragment;
    public int offset = 0;
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a

    public BaseRegisterActivityPagerAdapter(FragmentManager fragmentManager, String[] dialogOptions, Fragment baseFragment) {
        super(fragmentManager);
        this.dialogOptions = dialogOptions;
        this.mBaseFragment = baseFragment;
<<<<<<< HEAD
    }
=======
        offset += 1;
    }
    public BaseRegisterActivityPagerAdapter(FragmentManager fragmentManager, String[] dialogOptions, Fragment baseFragment, Fragment mProfileFragment) {
        super(fragmentManager);
        this.dialogOptions = dialogOptions;
        this.mBaseFragment = baseFragment;
        this.mProfileFragment = mProfileFragment;
        offset += 2;
    }

>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mBaseFragment;
                break;
<<<<<<< HEAD

            default:
                String formName = dialogOptions[position - 1]; // account for the base fragment
=======
            case 1:
                if(mProfileFragment != null) {
                    fragment = mProfileFragment;
                    break;
                }
            default:
                String formName = dialogOptions[position - offset]; // account for the base fragment
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
                DisplayFormFragment f = new DisplayFormFragment();
                f.setFormName(formName);
                fragment = f;
                break;
        }

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
<<<<<<< HEAD
        return dialogOptions.length + 1; // index 0 is always occupied by the base fragment
    }
=======
        return dialogOptions.length + offset; // index 0 is always occupied by the base fragment
    }

    public int offset() {
        return offset;
    }

>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
}
