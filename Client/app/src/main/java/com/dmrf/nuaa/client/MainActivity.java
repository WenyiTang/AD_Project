package com.dmrf.nuaa.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button getBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getBtn = findViewById(R.id.get);
        getBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v){

        int id = v.getId();
        if(id == R.id.get){
            Intent intent = new Intent();
            intent.setClass(this, PastMealsActivity.class);
            startActivity(intent);


        }

    }


}
