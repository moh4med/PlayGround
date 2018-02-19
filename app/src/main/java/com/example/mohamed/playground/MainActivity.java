package com.example.mohamed.playground;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private TextView tv_cityResult;
    private EditText et_password;
    private EditText et_userName;
    private final int MY_PERMISSIONS_REQUEST_INTERNET=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_cityResult = (TextView) findViewById(R.id.tv_result);
        checkperm();
    }

    private void checkperm() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {



                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_INTERNET);


            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    public void btn_register_onClick(View view) {
        String url = "http://10.0.2.2/androidlogin/add.php?username=" + et_userName.getText().toString() + "&password=" + et_password.getText().toString();
        String url2 = "http://192.168.1.20/androidlogin/add.php?username=" + et_userName.getText().toString() + "&password=" + et_password.getText().toString();
        new registerAsyncTask().execute(url);
    }

    public void btn_login_onClick(View view) {
        String url = "http://10.0.2.2/androidlogin/login.php?username=" + et_userName.getText().toString() + "&password=" + et_password.getText().toString();
        String url2 = "http://192.168.1.20/androidlogin/login.php?username=" + et_userName.getText().toString() + "&password=" + et_password.getText().toString();

        new loginAsyncTask().execute(url);
    }

    public void btn_list_onClick(View view) {
        Intent intent=new Intent(this,ListUserActivity.class);
        startActivity(intent);
    }

    public class loginAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            JSONObject json = null;
            tv_cityResult.setText(values[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = stream2string(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public class registerAsyncTask extends AsyncTask<String, String, String> {
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
                String msg = json.getString("msg");
                Log.e(TAG,"onProgressUpdate ");
                tv_cityResult.setText(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds
                Log.e(TAG,"doInBackground ");

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = stream2string(in);
                    //send to display data
                    publishProgress(NewsData);
                    Log.e(TAG,"publishProgress ");
                } finally {
                    //end connection
                    Log.e(TAG,"disconnect ");
                    urlConnection.disconnect();
                }

            } catch (Exception ex) {
                Log.e(TAG,"exception ");
                ex.printStackTrace();
            }
            return null;
        }
    }

    public String stream2string(InputStream in) {
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
