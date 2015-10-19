package org.ei.telemedicine.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.ei.telemedicine.service.CallService;
import static org.ei.telemedicine.util.Log.logInfo;

/**
 * Created by Beloved on 9/21/2015.
 */
public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        logInfo("Sync alarm triggered. Trying to Sync.");
        Intent service1 = new Intent(context, CallService.class);
        context.startService(service1);
    }

}