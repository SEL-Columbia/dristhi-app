package org.ei.telemedicine.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.ei.telemedicine.R;

import java.util.Random;

public class ActionActivity extends Activity {

    protected String callUrl="http://202.153.34.169/demos/callerdemo.html";

    private Ringtone ringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            ringAlarm(getApplicationContext()).play();
            Random rt = new Random();
            int max = 500, min = 100;
            int rd = rt.nextInt((max - min) + 1) + min;
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        setContentView(R.layout.activity_action);
        Intent myIntent = getIntent();
        String callerId = myIntent.getStringExtra("name");
        TextView showCaller =(TextView) findViewById(R.id.txtCaller);
        showCaller.setText(callerId + " is calling..");
        findViewById(R.id.btnCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AudioManager aM = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                aM.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Uri url = Uri.parse(callUrl);
                Intent _broswer = new Intent(Intent.ACTION_VIEW, url);
                startActivity(_broswer);
                finish();
            }
        });


    }
    public Ringtone ringAlarm(Context context)
    {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(alert == null){
            // alert is null, using backup
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if(alert == null){  // I can't see this ever being null (as always have a default notification) but just incase
                // alert backup is null, using 2nd backup
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), alert);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolumeAlarm = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        //int maxVolumeRing = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolumeAlarm, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        //audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolumeRing,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        Toast.makeText(context.getApplicationContext(), "alarm started", Toast.LENGTH_LONG).show();
        return r;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
