package com.example.whoscall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity_search extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //firestore
    FirebaseFirestore objectFirebsaeFirestore;
    EditText numberET, whoET, classET;
    TextView valuesTV;
    DocumentReference objectDocumentReference;
    ImageView resultIMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        try {
            objectFirebsaeFirestore = FirebaseFirestore.getInstance();
            numberET = findViewById(R.id.numberET);
            valuesTV = findViewById(R.id.valuesTV);
            resultIMG=findViewById(R.id.result_img);

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

        navigationView.setCheckedItem(R.id.nav_search);//app一開始home就被選著
        GlobalVariable gv = (GlobalVariable)getApplicationContext();

        String tc=gv.getTcolor();
        Drawable hb=gv.getHbg();
        toolbar.setBackgroundColor(Color.parseColor(tc));
        if(navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            header.setBackground(hb); }
    }

    //取得firestore資料
    public void getValueFromFirebase(View view)
    {
        try {
            if(!numberET.getText().toString().isEmpty()){
                objectDocumentReference=objectFirebsaeFirestore.collection("WhosCall").document(
                        numberET.getText().toString()
                );
                objectDocumentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String number=documentSnapshot.getId();//用id(號碼)才能查詢
                                String who=documentSnapshot.getString("who");
                                String Class=documentSnapshot.getString("class");
                                valuesTV.setText(
                                        "名稱："+who+"\n"+"類別："+Class+"\n"+"號碼："+number
                                );
                                resultIMG.setVisibility(View.VISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_search.this,"fails to get data back",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        catch (Exception e)
        {
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
            case R.id.nav_search:
                break;//在home時按home無效
            case R.id.nav_logout:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent = new Intent(Activity_search.this,Activity_register.class);
                startActivity(intent);//使用intent啟動另一個activity
                break;
            case R.id.nav_security:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent2 = new Intent(Activity_search.this, Activity_security.class);
                startActivity(intent2);//使用intent啟動另一個activity
                break;
            case R.id.nav_home:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent3 = new Intent(Activity_search.this,Activity_homepage.class);
                startActivity(intent3);//使用intent啟動另一個activity
                break;
            case R.id.nav_profile:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent4 = new Intent(Activity_search.this,Activity_profile.class);
                startActivity(intent4);//使用intent啟動另一個activity
                break;
            case R.id.nav_update:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent5 = new Intent(Activity_search.this,Activity_update.class);
                startActivity(intent5);//使用intent啟動另一個activity
                break;
            case R.id.nav_deleteC:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent6 = new Intent(Activity_search.this,Activity_delC.class);
                startActivity(intent6);//使用intent啟動另一個activity
                break;
            case R.id.nav_new:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent8 = new Intent(Activity_search.this,Activity_new.class);
                startActivity(intent8);//使用intent啟動另一個activity
                break;
            case R.id.nav_phonebook:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent9 = new Intent(Activity_search.this,Activity_phonebook.class);
                startActivity(intent9);//使用intent啟動另一個activity
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}