package com.example.whoscall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_new extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //firestore
    FirebaseFirestore objectFirebsaeFirestore;
    EditText numberET, whoET;
    Spinner classSP;
    TextView valuesTV;
    DocumentReference objectDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        //firestore find.view.by.id
        try {
            objectFirebsaeFirestore = FirebaseFirestore.getInstance();
            numberET = findViewById(R.id.numberET3);
            whoET = findViewById(R.id.whoET);
            classSP = findViewById(R.id.classSP);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_new);//app一開始home就被選著
        GlobalVariable gv = (GlobalVariable)getApplicationContext();

        String tc=gv.getTcolor();
        Drawable hb=gv.getHbg();
        toolbar.setBackgroundColor(Color.parseColor(tc));
        if(navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            header.setBackground(hb); }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.classes)
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        classSP.setAdapter(adapter);
    }


    //新增
    public void addValueToFirebase(android.view.View view) {
        try {
            if (!numberET.getText().toString().isEmpty()
                    && !whoET.getText().toString().isEmpty()
                    && !whoET.getText().toString().isEmpty())
            {
                Map<String, String> objectMap = new HashMap<>();

                objectMap.put("who", whoET.getText().toString());//將whoET放到who類別裡
                objectMap.put("class", classSP.getSelectedItem().toString());//將clsaaET放到 class類別裡

                objectFirebsaeFirestore
                        .collection("WhosCall")
                        .document(numberET.getText().toString())//用number當firestore的id
                        .set(objectMap)
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity_new.this,"data is stored",Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_new.this,"fails to add data",Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                Toast.makeText(Activity_new.this, "請填入所有資料喔", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
    //toolbar的來回
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    @Override
    //toolbar選項選擇之頁面跳轉
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_new:
                break;//在home時按home無效
            case R.id.nav_logout:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent = new Intent(Activity_new.this,Activity_register.class);
                startActivity(intent);//使用intent啟動另一個activity
                break;
            case R.id.nav_security:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent2 = new Intent(Activity_new.this, Activity_security.class);
                startActivity(intent2);//使用intent啟動另一個activity
                break;
            case R.id.nav_home:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent3 = new Intent(Activity_new.this,Activity_homepage.class);
                startActivity(intent3);//使用intent啟動另一個activity
                break;
            case R.id.nav_search:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent4 = new Intent(Activity_new.this,Activity_search.class);
                startActivity(intent4);//使用intent啟動另一個activity
                break;
            case R.id.nav_update:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent5 = new Intent(Activity_new.this,Activity_update.class);
                startActivity(intent5);//使用intent啟動另一個activity
                break;
            case R.id.nav_deleteC:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent6 = new Intent(Activity_new.this,Activity_delC.class);
                startActivity(intent6);//使用intent啟動另一個activity
                break;

            case R.id.nav_profile:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent8 = new Intent(Activity_new.this,Activity_profile.class);
                startActivity(intent8);//使用intent啟動另一個activity
                break;
            case R.id.nav_phonebook:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent9 = new Intent(Activity_new.this,Activity_phonebook.class);
                startActivity(intent9);//使用intent啟動另一個activity
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}