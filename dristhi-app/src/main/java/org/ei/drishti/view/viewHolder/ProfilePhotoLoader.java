package org.ei.drishti.view.viewHolder;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import org.ei.drishti.view.contract.SmartRegisterClient;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH;

public class ProfilePhotoLoader {

    private Resources resources;
    private Drawable defaultPlaceHolder;
    private Map<String, Drawable> drawableMap = new HashMap<String, Drawable>();


    public ProfilePhotoLoader(Resources res, Drawable placeHolder) {
        this.resources = res;
        defaultPlaceHolder = placeHolder;
    }

    public Drawable get(SmartRegisterClient client) {
        if (drawableMap.containsKey(client.entityId())) {
            return drawableMap.get(client.entityId());
        }

        String photoPath = client.profilePhotoPath();
        if (isBlank(photoPath) || photoPath.contains(DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH)) {
            return defaultPlaceHolder;
        }

        Drawable profilePhoto = new BitmapDrawable(resources, photoPath.replace("file:///mnt/", "/"));
        drawableMap.put(client.entityId(), profilePhoto);
        return profilePhoto;
    }
}
