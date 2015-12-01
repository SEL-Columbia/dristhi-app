//package org.ei.telemedicine.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.os.StrictMode;
//import android.util.Log;
//import android.widget.Toast;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.ei.telemedicine.Context;
//import org.ei.telemedicine.view.activity.ActionActivity;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//public class CallService extends Service {
//    private Context context;
//    private static final String TAG = "CallService";
//    public String id, caller;
//    boolean message;
//    private final String CALLER = "name";
//    private final String url = "http://beloved23.webfactional.com/json.php?id=";
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    @Override
//    public void onCreate() {
//        MyNotify();
//    }
//
//    @Override
//    public void onDestroy() {
//        Toast.makeText(this, "Dead", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onStart(Intent intent, int startid) {
//        MyNotify();
//        Log.d(TAG, "onStart");
//    }
//
//    public String getUsern() {
//        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());
//        return context.allSharedPreferences().fetchRegisteredANM();
//    }
//
//    public void MyNotify() {
//        try {
//            JSONObject jObject = new JSONObject(getCall());
//            id = jObject.getString("id");
//            message = jObject.getBoolean("message");
//            caller = jObject.getString("caller");
//            if (message) {
//                Intent dialogIntent = new Intent(this, ActionActivity.class);
//                dialogIntent.putExtra(CALLER, caller);
//                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(dialogIntent);
//            } else {
//                return;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public String getCall() {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll()
//                .penaltyLog()
//                .build());
//
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
//                .penaltyLog()
//                .build());
//        String name = getUsern();
//        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
//
//        HttpPost httppost = new HttpPost(url + name);
//        Log.d(TAG, url + name);
//        httppost.setHeader("Content-type", "application/json");
//        InputStream inputStream = null;
//        String result = null;
//        try {
//            HttpResponse response = httpclient.execute(httppost);
//            HttpEntity entity = response.getEntity();
//            inputStream = entity.getContent();
//            // json is UTF-8 by default
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            result = sb.toString();
//        } catch (Exception e) {
//            // Oops
//        } finally {
//            try {
//                if (inputStream != null) inputStream.close();
//            } catch (Exception squish) {
//            }
//        }
//        return result;
//    }
//
//}