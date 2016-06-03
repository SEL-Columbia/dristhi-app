package org.ei.opensrp.indonesia.view.viewHolder;

import android.graphics.drawable.Drawable;

import org.ei.opensrp.indonesia.view.contract.AnakClient;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.viewHolder.ProfilePhotoLoader;

import static org.ei.opensrp.indonesia.AllConstantsINA.FEMALE_GENDER_INA;

public class AnakRegisterProfilePhotoLoader implements ProfilePhotoLoader {
    private final Drawable maleInfantDrawable;
    private final Drawable femaleInfantDrawable;

    public AnakRegisterProfilePhotoLoader(Drawable maleInfantDrawable, Drawable femaleInfantDrawable) {
        this.maleInfantDrawable = maleInfantDrawable;
        this.femaleInfantDrawable = femaleInfantDrawable;
    }

    public Drawable get(SmartRegisterClient client) {
        return FEMALE_GENDER_INA.equalsIgnoreCase(((AnakClient) client).gender())
                ? femaleInfantDrawable
                : maleInfantDrawable;
    }
}
