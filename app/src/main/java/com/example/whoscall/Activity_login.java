package com.example.whoscall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_login extends AppCompatActivity {
    Activity_login context = this;
    Button signin_login,signin_register;
    TextView signin_account,signin_password,signin_success;
    EditText signin_emailText,signin_passwordText,signin_nameText;
    String email,password;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin_login = (Button)findViewById(R.id.signin_login);
        signin_register = (Button)findViewById(R.id.signin_register);
        signin_account = (TextView)findViewById(R.id.signin_account);
        signin_password = (TextView)findViewById(R.id.signin_password);
        signin_success = (TextView)findViewById(R.id.signin_success);
        signin_emailText = (EditText)findViewById(R.id.signin_emailText);
        signin_passwordText = (EditText)findViewById(R.id.signin_passwordText);
        auth = FirebaseAuth.getInstance();



        signin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signInWithEmailAndPassword(signin_emailText.getText().toString(),signin_passwordText.getText().toString()).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            email = user.getEmail();
                            GlobalVariable gv = (GlobalVariable) getApplicationContext();
                            gv.setMail(signin_emailText.getText().toString());
                            Intent intent = new Intent(Activity_login.this, Activity_homepage.class);
                            startActivity(intent);//使用intent啟動另一個activity
                        }else{
                            signin_success.setText("登入失敗"+task.getException());
                        }
                    }
                });
            }
        });
        signin_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_login.this, Activity_register.class);
                startActivity(intent);//使用intent啟動另一個activity
            }
        });


    }
    //private FirebaseAuth fileAuth = FirebaseAuth.getInstance();



}