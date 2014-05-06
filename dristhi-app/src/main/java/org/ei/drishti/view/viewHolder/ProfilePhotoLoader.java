package org.ei.drishti.view.viewHolder;

import android.graphics.drawable.Drawable;
import org.ei.drishti.view.contract.SmartRegisterClient;

public interface ProfilePhotoLoader {
    public Drawable get(SmartRegisterClient client);
}
