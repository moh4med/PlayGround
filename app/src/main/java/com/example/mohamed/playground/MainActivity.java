package com.example.mohamed.playground;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int DETAIL_ACTIVITY_REQUEST_CODE = 1;
    TextView tv_message;
    private EditText et_name;
    private String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message = findViewById(R.id.tv_name);
        et_name = (EditText) findViewById(R.id.et_name);
    }

    public void onclick(View view) {
        switch (view.getTag().toString()) {
            case "detail": {
                Intent intent = new Intent(this.getApplicationContext(), DetailActivity.class);
                String name = et_name.getText().toString();
                intent.putExtra("name", name);
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                for(ResolveInfo x:resolveInfos){
                    Log.e(TAG,x.activityInfo.packageName);
                }
                startActivityForResult(intent, DETAIL_ACTIVITY_REQUEST_CODE);
                break;
            }
            case "view": {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Intent chooser = Intent.createChooser(intent, "choose app");
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                for(ResolveInfo x:resolveInfos){
                    Log.e(TAG,x.activityInfo.packageName);
                }
                if (intent.resolveActivity(getPackageManager()) != null) {
                    //            startActivity(chooser);         //to create specific choose with no default button
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "no app found", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case "dummy": {
                Intent intent = new Intent("com.example.mohamed.playground.DUMMY");
                intent.putExtra("first", "mohamed");
                intent.putExtra("second", "ahmed");
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                for(ResolveInfo x:resolveInfos){
                    Log.e(TAG,x.activityInfo.packageName);
                }
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "no app found", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case "reverse": {
                Intent intent = new Intent("com.example.mohamed.playground.DUMMYREVERSE");
                intent.putExtra("first", "ali");
                intent.putExtra("second", "hassan");
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                for(ResolveInfo x:resolveInfos){
                    Log.e(TAG,x.activityInfo.packageName);
                }
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "no app found", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case "explicit": {
                Intent intent = new Intent(this, implicitActivity.class);
                intent.putExtra("first", "ezz");
                intent.putExtra("second", "deghedy");
                Intent chooser = Intent.createChooser(intent, "choose app");
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                for(ResolveInfo x:resolveInfos){
                    Log.e(TAG,x.activityInfo.packageName);
                }
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;

            }case "any": {
                Intent intent = new Intent("");
                intent.addCategory("android.intent.category.DEFAULT");
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                for(ResolveInfo x:resolveInfos){
                    Log.e(TAG,x.activityInfo.packageName);
                }
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "no app found", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DETAIL_ACTIVITY_REQUEST_CODE: {
                if (resultCode == Activity.RESULT_OK) {
                    String message = data.getStringExtra("message");
                    tv_message.setText(message);
                } else {

                }

                break;
            }
        }
    }
}
