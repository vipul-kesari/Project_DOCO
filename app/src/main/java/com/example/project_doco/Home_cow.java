package com.example.project_doco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Home_cow extends AppCompatActivity {

    private ImageButton sign_in;
    private static final String TAG = "Home_cow";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cow);
        sign_in=findViewById(R.id.img_btn_home_cow);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home_cow.this,sign_in.class);
                Toast.makeText(Home_cow.this, "Going to sign in page", Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Going to sign in page");
                startActivity(intent);
            }
        });
    }
}