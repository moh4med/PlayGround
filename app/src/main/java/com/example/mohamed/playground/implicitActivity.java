package com.example.mohamed.playground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class implicitActivity extends AppCompatActivity {
    TextView tv_first;
    TextView tv_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String action = intent.getAction();
        tv_first = (TextView) findViewById(R.id.tv_first);
        tv_second = (TextView) findViewById(R.id.tv_second);
        String firstname = intent.getStringExtra("first");
        String secondname = intent.getStringExtra("second");
        if (action == null) {
            tv_first.setText("hello " + firstname);
            tv_second.setText("hello " + secondname);
        } else if (action.equals("com.example.mohamed.playground.DUMMY")) {
            tv_first.setText(firstname);
            tv_second.setText(secondname);
        } else if (action.equals("com.example.mohamed.playground.DUMMYREVERSE")) {
            tv_first.setText(secondname);
            tv_second.setText(firstname);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
