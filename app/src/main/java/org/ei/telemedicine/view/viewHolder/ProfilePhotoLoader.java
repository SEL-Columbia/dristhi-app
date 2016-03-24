package org.ei.telemedicine.view.viewHolder;

import android.graphics.drawable.Drawable;

import org.ei.telemedicine.view.contract.SmartRegisterClient;

public interface ProfilePhotoLoader {
    public Drawable get(SmartRegisterClient client);
}
