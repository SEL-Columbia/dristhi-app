package org.ei.drishti;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import de.akquinet.android.androlog.Log;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrishtiMainActivity extends Activity {
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.init();
        Log.i(this, "Initializing ...");
        setContentView(R.layout.main);


        Button button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    populateList();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void populateList() {
        List<AlertAction> mothers = new ArrayList<AlertAction>();
        mothers.add(new AlertAction("Case B", "due", new HashMap<String, String>()));
        mothers.add(new AlertAction("Case Y", "late", new HashMap<String, String>()));
        listView.setAdapter(new AlertAdapter(this, R.layout.list_item, mothers));
    }

    private void getResponse() throws Exception {
        URL url = new URL("http://drishti.modilabs.org/alerts?anmIdentifier=1&timeStamp=0");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            readStream(new BufferedInputStream(urlConnection.getInputStream()));
        } finally {
            urlConnection.disconnect();
        }
    }

    private void readStream(InputStream in) throws Exception {
        List<String> lines = IOUtils.readLines(in);

    }


}

