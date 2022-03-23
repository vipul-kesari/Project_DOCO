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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_in extends AppCompatActivity implements View.OnClickListener {

    private TextView forgotpassword;
    EditText username,password,repassword;
    Button signin;
    ImageButton signup;
    private FirebaseAuth mAuth;
    private static final String TAG1 = "sign_in";
//    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username=(EditText) findViewById(R.id.UserName);
        password=(EditText) findViewById(R.id.pwd);
        //repassword=(EditText) findViewById(R.id.)
        signin=(Button) findViewById(R.id.sign_in_btn);
        signup=(ImageButton) findViewById(R.id.sign_up_page);
        mAuth=FirebaseAuth.getInstance();
        forgotpassword=(TextView)findViewById(R.id.forget_password);
//        DB =new DBHelper(this);

        signup.setOnClickListener(this);

        signin.setOnClickListener(this);

        forgotpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_up_page:
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.sign_in_btn:
                userLogin();
                break;
            case R.id.forget_password:
                startActivity(new Intent(sign_in.this,forget_password.class));
        }

    }

    private void userLogin() {
        String email=username.getText().toString().trim();
        String pwd=password.getText().toString().trim();

        if(email.isEmpty()){
            username.setError("Email is Required");
            username.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Please enter a valid email");
            username.requestFocus();
            return;
        }

        if(pwd.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(pwd.length()<6){
            password.setError("Min password length is 6 characters!!");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        //redirect to user profile

                        startActivity(new Intent(sign_in.this,HomeActivity.class));
                        Log.i(TAG1,"started new activity home");
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(sign_in.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                        Log.i(TAG1,"Check your email to verify your account!");
                    }


                }
                else{
                    Toast.makeText(sign_in.this, "Failed to Login! Check Credentials!!", Toast.LENGTH_LONG).show();
                    Log.i(TAG1,"Failed to Login! Check Credentials!!");
                }
            }
        });
    }
}