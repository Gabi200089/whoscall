package com.example.whoscall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Activity_register extends AppCompatActivity{
    Activity_register context = this;
    Button register_register;
    ImageButton register_login;
    TextView register_account,register_password,register_name,register_success;
    EditText register_emailText,register_passwordText,register_nameText;
    String email;
    FirebaseFirestore db;
    FirebaseAuth auth;

    FirebaseFirestore objectFirebsaeFirestore;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_login = findViewById(R.id.register_login);
        register_register = (Button)findViewById(R.id.register_register);
        register_account = (TextView)findViewById(R.id.register_account);
        register_password = (TextView)findViewById(R.id.register_password);
        register_success = (TextView)findViewById(R.id.register_success);
        register_name = (TextView)findViewById(R.id.register_name);
        register_emailText = (EditText)findViewById(R.id.register_emailText);
        register_passwordText = (EditText)findViewById(R.id.register_passwordText);
        register_nameText= (EditText)findViewById(R.id.register_nameText);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        objectFirebsaeFirestore = FirebaseFirestore.getInstance();

        register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                auth.createUserWithEmailAndPassword(register_emailText.getText().toString(),register_passwordText.getText().toString()).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            register_success.setText(user.getEmail()+"註冊成功");
                        }else{
                            register_success.setText("註冊失敗"+task.getException());
                        }
                    }
                });
                String name = register_nameText.getText().toString();
                {
                    Map<String, String> objectMap = new HashMap<>();

                    objectMap.put("姓名", register_nameText.getText().toString());//將register_nameText放到name類別
                    objectFirebsaeFirestore
                            .collection("users")
                            .document(register_emailText.getText().toString())//用register_nameText當firestore的id
                            .set(objectMap)
                            .addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Activity_register.this, "data is stored", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            )
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            });
                }
            }
        });
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_register.this, Activity_login.class);
                startActivity(intent);//使用intent啟動另一個activity
            }
        });


    }
    public void dbcreate(View view){

    }


}