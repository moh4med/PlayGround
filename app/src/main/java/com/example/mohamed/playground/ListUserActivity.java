package com.example.mohamed.playground;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
    ArrayList<User>    listnewsData = new ArrayList<User>();
    MyAdapter myadapter;
    private String TAG=ListUserActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        myadapter=new MyAdapter(listnewsData);
        myadapter=new MyAdapter(listnewsData);
        ListView lsNews=(ListView) findViewById(R.id.lv_users);
        lsNews.setAdapter(myadapter);//intisal with data

        String url="http://10.0.2.2/androidlogin/list.php";
        String url2="http://192.168.1.20/androidlogin/list.php";

        new  listUserAsyncTask().execute(url2);
    }
    public class listUserAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            JSONArray json = null;
            Log.e(TAG,"onProgressUpdate ");
            try {
                json = new JSONArray(values[0]);
                for (int i=0;i<json.length();i++){
                    JSONObject user= json.getJSONObject(i);
                    listnewsData.add(new User(user.getInt("id"),user.getString("username"),user.getString("password")));
                }
                myadapter.notifyDataSetChanged();

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
                Log.e(TAG,"trying to connect ");
                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = stream2string(in);
                    //send to display data
                    Log.e(TAG,"publishProgress ");
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    Log.e(TAG,"disconnect ");
                    urlConnection.disconnect();
                }

            } catch (Exception ex) {
                Log.e(TAG,"Exception ");
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
    public class User{
        public   int ID;
        public  String UserName;
        public  String Password;
        //for news details
        User( int ID, String UserName,String Password)
        {
            this. ID=ID;
            this. UserName=UserName;
            this. Password=Password;
        }
    }
    public class MyAdapter extends BaseAdapter{
        ArrayList<User> myUsers;

        public MyAdapter(ArrayList<User> myUsers) {
            this.myUsers = myUsers;
        }

        @Override
        public int getCount() {
            if(myUsers==null)return 0;
            return myUsers.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = getLayoutInflater().inflate(R.layout.user_item,parent, false);
            final   User s = myUsers.get(position);

            TextView etID=( TextView)myView.findViewById(R.id.etID);
            etID.setText( String.valueOf( s.ID));
            TextView etUserName=( TextView)myView.findViewById(R.id.etUserName);
            etUserName.setText(s.UserName);
            TextView etPassword=( TextView)myView.findViewById(R.id.etPassword);
            etPassword.setText(s.Password);
            return myView;
        }
    }
}
