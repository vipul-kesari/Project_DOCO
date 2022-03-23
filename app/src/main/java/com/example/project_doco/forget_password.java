package com.example.project_doco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password extends AppCompatActivity {

    private static final String TAG = "forget_password";
    private EditText email;
    private Button resetpassword;
    private TextView signin;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        email=(EditText) findViewById(R.id.forget_email_input);
        resetpassword=(Button) findViewById(R.id.forget_button);
        signin=(TextView)findViewById(R.id.go_to_sing_in);
        auth=FirebaseAuth.getInstance();
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }


        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(forget_password.this,sign_in.class));
                Toast.makeText(forget_password.this, "Going to Sign in page!", Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Going to sign in page!");
            }
        });


    }

    private void resetPassword() {
        String emailval=email.getText().toString().trim();
        if(emailval.isEmpty()){
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailval).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(emailval).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forget_password.this, "check your email to reset password!", Toast.LENGTH_LONG).show();
                    Log.i(TAG,"check your email to reset password!");
                }
                else{
                    Toast.makeText(forget_password.this, "Try Again!! Something Went wrong!!!", Toast.LENGTH_LONG).show();
                    Log.i(TAG,"Try Again!! Something Went wrong!!!");
                }
            }
        });

    }
}