package com.example.mohamed.playground;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private EditText et_cityName;
    private TextView tv_cityResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_cityName = (EditText) findViewById(R.id.et_cityName);
        tv_cityResult = (TextView) findViewById(R.id.tv_result);


    }

    public void btn_go_onClick(View view) {
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + et_cityName.getText().toString() + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        new mAsyncTask().execute(url);
    }

    public class mAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            JSONObject json = null;
            try {
                json = new JSONObject(values[0]);
                JSONObject query = json.getJSONObject("query");
                JSONObject results = query.getJSONObject("results");
                JSONObject channel = results.getJSONObject("channel");
                JSONObject astronomy = channel.getJSONObject("astronomy");
                String sunset = astronomy.getString("sunset");
                String sunrise = astronomy.getString("sunrise");

                tv_cityResult.setText("sunset:" + sunset + ",sunrise:" + sunrise);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... Params) {
            try {
                URL url = new URL(Params[0]);
                URLConnection urlConnection = (URLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = stream2string(in);
                publishProgress(s);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private String stream2string(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        String out = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                out += line;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}
