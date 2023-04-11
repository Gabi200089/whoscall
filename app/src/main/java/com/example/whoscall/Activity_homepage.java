package com.example.whoscall;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Activity_homepage extends AppCompatActivity {
    String name;
    FirebaseAuth auth;
    ImageView imageView;
    FirebaseFirestore db;
    DocumentReference objectDocumentReference;
    FirebaseFirestore objectFirebsaeFirestore;
    TextView userTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        objectFirebsaeFirestore = FirebaseFirestore.getInstance();
        imageView=findViewById(R.id.home_bg);

        userTV = findViewById(R.id.userTV);
        CardView cv1= findViewById(R.id.search_cv);
        CardView cv2= findViewById(R.id.new_cv);
        CardView cv4= findViewById(R.id.update_cv);
        CardView cv5= findViewById(R.id.delete_cv);
        CardView cv6= findViewById(R.id.phonebook_cv);
        CardView cv7= findViewById(R.id.profile_cv);
        CardView cv8= findViewById(R.id.security_cv);

        ImageButton bt3 = findViewById(R.id.exitBtn2);

        cv1.setOnClickListener(cv1Listener);
        cv2.setOnClickListener(cv2Listener);
        cv4.setOnClickListener(cv4Listener);
        cv5.setOnClickListener(cv5Listener);
        cv6.setOnClickListener(cv6Listener);
        cv7.setOnClickListener(cv7Listener);
        cv8.setOnClickListener(cv8Listener);

        bt3.setOnClickListener(bt3Listener);

        GlobalVariable gv = (GlobalVariable) getApplicationContext();

        Drawable homebg = gv.getHomebg();
        imageView.setImageDrawable(homebg);
        name=gv.getMail();
        getName();
    }

    public void getName() {
        try {
            objectDocumentReference = objectFirebsaeFirestore.collection("users").document(
                    name
            );
            objectDocumentReference.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            name=documentSnapshot.getId();//用id(號碼)才能查詢
                            String who = documentSnapshot.getString("姓名");
                            userTV.setText(who);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(Activity_homepage.this,"fails to get data back",Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private CardView.OnClickListener cv1Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_homepage.this,Activity_search.class);
            startActivity(intent);//使用intent啟動另一個activity
        }
    };
    private CardView.OnClickListener cv2Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_homepage.this,Activity_new.class);
            startActivity(intent);//使用intent啟動另一個activity
        }
    };
    private ImageButton.OnClickListener bt3Listener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            auth.getInstance().signOut();
            Intent intent = new Intent(Activity_homepage.this, Activity_login.class);
            startActivity(intent);//使用intent啟動另一個activity
        }
    };
    private CardView.OnClickListener cv4Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_homepage.this,Activity_update.class);
            startActivity(intent);//使用intent啟動另一個activity
        }
    };
    private CardView.OnClickListener cv5Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_homepage.this,Activity_delC.class);
            startActivity(intent);//使用intent啟動另一個activity
        }
    };
    private CardView.OnClickListener cv6Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_homepage.this,Activity_phonebook.class);
            startActivity(intent);//使用intent啟動另一個activity
        }
    };
    private CardView.OnClickListener cv7Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_homepage.this,Activity_profile.class);
            startActivity(intent);//使用intent啟動另一個activity
        }
    };
    private CardView.OnClickListener cv8Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_homepage.this,Activity_security.class);
            startActivity(intent);//使用intent啟動另一個activity
        }
    };

}
