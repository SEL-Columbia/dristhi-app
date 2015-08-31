package org.ei.telemedicine.doctor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.event.Listener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.ei.telemedicine.doctor.DoctorFormDataConstants.formData;

/**
 * Created by naveen on 6/18/15.
 */
public abstract class DoctorPatientDetailSuperActivity extends Activity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private Bundle bundle;
    private String formInfo, documentId, phoneNumber;
    private String[] details;
    ProgressDialog playProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(formData)) {

            formInfo = bundle.getString(DoctorFormDataConstants.formData);
            setupViews();
            details = setDatatoViews(formInfo);
            documentId = details[0] != null ? details[0] : "";
            phoneNumber = details[1] != null ? details[1] : "";

        }
    }

    protected abstract String[] setDatatoViews(String formInfo);

    protected abstract void setupViews();

    public void playData(String url) {
        try {
            final MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(url);
            player.prepare();

            playProgressDialog = new ProgressDialog(DoctorPatientDetailSuperActivity.this);
            playProgressDialog.setTitle("Playing Heartbeat");
            playProgressDialog.setCancelable(false);
            playProgressDialog.show();

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (playProgressDialog != null && playProgressDialog.isShowing()) {
                        playProgressDialog.dismiss();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//
//        new AsyncTask<String, Void, Boolean>() {
//            @Override
//            protected Boolean doInBackground(String... params) {
//                try {
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//
//            @Override
//            protected void onPreExecute() {
//
//
//            }
//
//            @Override
//            protected void onPostExecute(Boolean isRunning) {
//                if (playProgressDialog != null && !player.isPlaying())
//                    playProgressDialog.dismiss();
//            }
//        }.execute(url);

    }

    public void getVitalsData(final String vitalType, final String visitId) {
        getData(AllConstants.VITALS_INFO_URL_PATH + visitId, new Listener<String>() {
            @Override
            public void onEvent(String data) {
                Log.e("Result", visitId);
                Intent intent = new Intent(DoctorPatientDetailSuperActivity.this, NativeGraphActivity.class);
                intent.putExtra(AllConstants.VITALS_INFO_RESULT, data);
                intent.putExtra(AllConstants.VITAL_TYPE, vitalType);
                startActivity(intent);
            }
        });
    }

    public void referAnotherDoctor(String doctorId, String visitId, String entityId) {
        getData(AllConstants.DOCTOR_REFER_URL_PATH + doctorId + "&visitid=" + visitId + "&entityid=" + entityId, new Listener<String>() {
            @Override
            public void onEvent(String data) {
                finish();
            }
        });

    }


    public void getDrugData() {
        getData(AllConstants.DRUG_INFO_URL_PATH, new Listener<String>() {
            //        getDataFromServer(new Listener<String>() {
            public void onEvent(String resultData) {
                if (resultData != null) {
//                    Toast.makeText(DoctorPatientDetailSuperActivity.this, "Document Id " + documentId, Toast.LENGTH_SHORT).show();
                    Log.e("Document Id", documentId);
                    Context.getInstance().allDoctorRepository().updatePocInLocal(documentId, "", "");
                    Intent intent = new Intent(DoctorPatientDetailSuperActivity.this, DoctorPlanofCareActivity.class);
                    intent.putExtra(AllConstants.DRUG_INFO_RESULT, resultData);
                    intent.putExtra(DoctorFormDataConstants.formData, formInfo);
                    intent.putExtra(DoctorFormDataConstants.documentId, documentId);
                    intent.putExtra(DoctorFormDataConstants.phoneNumber, phoneNumber);
                    startActivity(intent);
                } else {
                    Toast.makeText(DoctorPatientDetailSuperActivity.this, "No Data getting from server", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData(final String url, final Listener<String> afterResult) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                Context context = Context.getInstance();
                Log.e("URL", context.configuration().dristhiDjangoBaseURL() + url);
                String result = context.userService().gettingFromRemoteURL(context.configuration().dristhiDjangoBaseURL() + url);

                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(DoctorPatientDetailSuperActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.loggin_in_dialog_message));
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(String resultData) {
                super.onPostExecute(resultData);
                if (progressDialog.isShowing())
                    progressDialog.hide();
                afterResult.onEvent(resultData);
            }
        }.execute();
    }

    public void createFileFrombyte(byte[] audioBytes) {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            path += "/audiorecordtest.wav";
            File dstFile = new File(path);
            if (!dstFile.exists())
                dstFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(dstFile);
            fout.write(audioBytes);
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPlaying(String path) {
        MediaPlayer mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDatafromJson(String jsonStr, String key) {
        if (jsonStr != null) {
            try {
                JSONObject jsonData = new JSONObject(jsonStr);
                return jsonData.has(key) ? jsonData.getString(key) : "";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
