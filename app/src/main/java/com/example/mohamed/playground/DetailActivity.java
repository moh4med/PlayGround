package com.example.mohamed.playground;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView tv_name;
    private Intent returnintent;
    EditText et_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_name=(TextView)findViewById(R.id.tv_name);
        et_message=(EditText) findViewById(R.id.et_message);
        String name=getIntent().getStringExtra("name");
        tv_name.setText(name);
        returnintent=new Intent();
        setResult(Activity.RESULT_OK,returnintent);
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        String message=et_message.getText().toString();
        returnintent.putExtra("message",message);
        finish();
        return true;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                String message=et_message.getText().toString();
                returnintent.putExtra("message",message);
               // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
