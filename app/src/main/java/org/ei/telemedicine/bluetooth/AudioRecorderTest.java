package org.ei.telemedicine.bluetooth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AudioRecorderTest extends Activity {
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;

    String time = "10000";
    ProgressDialog dialog = null;

    private void onRecord(boolean start) {
        if (start) {


        } else {

        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
//            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();

            InputStream fis = new FileInputStream(mFileName);
            byte[] buffer = new byte[(int) mFileName.length()];
            fis.read(buffer, 0, buffer.length);
            fis.close();

            Log.v("Audio Length", "" + buffer);
            Log.v("Audio Length2", "" + buffer.length);

            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, "audiorecordtest.wav");

            byte[] dd;
            try {
                FileInputStream fns = new FileInputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();


                dd = new byte[fns.available()];
                fns.read(dd);

                Toast.makeText(getApplicationContext(), "inside fileinputstream" + dd, Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }


    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        LinearLayout ll = new LinearLayout(this);
        mRecordButton = new RecordButton(this);
        ll.addView(mRecordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        setContentView(ll);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

}
