package com.example.project_doco;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements  View.OnClickListener {


    private FirebaseAuth mAuth;
    EditText username,password,email,contact;
    Button signup;
    ImageButton signin;
    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        username=(EditText) findViewById(R.id.username_sign_up);
        password=(EditText) findViewById(R.id.pwd_sign_up);
        email =(EditText) findViewById(R.id.email);
        contact=(EditText) findViewById(R.id.contactInfo);

        //repassword=(EditText) findViewById(R.id.pwd_sign_up_c);
        signup=(Button) findViewById(R.id.sign_up_btn);
        signin=(ImageButton) findViewById(R.id.sign_in_page);
//        DB=new DBHelper(this);

        signin.setOnClickListener(this);
        signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.sign_in_page:
                startActivity(new Intent(this,sign_in.class));
                break;

            case R.id.sign_up_btn:
                registeruser();
                break;
        }
    }

    private void registeruser() {
        String emailval=email.getText().toString().trim();
        String userName=username.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        String conc=contact.getText().toString().trim();

        //conditions for the registration form

        if(userName.isEmpty()){
            username.setError("Full name is Required");
            username.requestFocus();
            return;
        }

        if(conc.isEmpty()){
            contact.setError("Contact is Required");
            contact.requestFocus();
            return;
        }

        if(emailval.isEmpty()){
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailval).matches()){
            email.setError("Please provide valid Email");
            email.requestFocus();
            return;
        }

        if(pwd.isEmpty()){
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }

        if(pwd.length()<6){
            password.setError("Min password length should be 6 characters !!");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailval,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(userName,conc,emailval);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "User Registered Successfully", Toast.LENGTH_LONG).show();


                                        Log.i(TAG,"User Registered Successfully");

                                        //redirect to login panel
                                        startActivity(new Intent(SignUp.this,sign_in.class));
                                        Log.i(TAG,"Started new activity sign_in");
                                    }
                                    else{
                                        Toast.makeText(SignUp.this, "Registration Failed!! Try Again!!!", Toast.LENGTH_LONG).show();
                                        Log.i(TAG,"Registration Failed!! Try Again!!!");
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(SignUp.this, "Registration Failed!! Try Again!!!", Toast.LENGTH_LONG).show();
                            Log.i(TAG,"Registration Failed!! Try Again!!!");
                        }
                    }
                });
    }
}