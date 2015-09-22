package org.ei.opensrp.indonesia.lib;

import android.content.Context;

import main.java.com.mindscapehq.android.raygun4android.RaygunClient;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunUserInfo;

/**
 * Created by Dimas on 9/22/2015.
 */
public class RaygunFacade {

    public static void initErrorHandler(Context context) {
        RaygunClient.Init(context);
        RaygunClient.AttachExceptionHandler();
    }

    public static void setUsername(String Fullname, String FirstName) {
        RaygunUserInfo user = new RaygunUserInfo();
        user.FullName = Fullname;
        user.FirstName = FirstName;
        user.IsAnonymous = false;
        RaygunClient.SetUser(user);
    }

}
