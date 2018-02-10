package com.example.mohamed.playground;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int DETAIL_ACTIVITY_REQUEST_CODE = 1;
    TextView tv_message;
    private EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message=findViewById(R.id.tv_name);
        et_name=(EditText) findViewById(R.id.et_name);
    }

    public void onclick(View view) {
        switch (view.getTag().toString()) {
            case "detail": {
                Intent intent = new Intent(this.getApplicationContext(), DetailActivity.class);
                String name=et_name.getText().toString();
                intent.putExtra("name", name);
                startActivityForResult(intent, DETAIL_ACTIVITY_REQUEST_CODE);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case DETAIL_ACTIVITY_REQUEST_CODE:{
                if(resultCode== Activity.RESULT_OK){
                    String message = data.getStringExtra("message");
                    tv_message.setText(message);
                }else{

                }

                break;
            }
        }
    }
}
