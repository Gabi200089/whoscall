package com.example.whoscall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_update extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //firestore
    FirebaseFirestore objectFirebsaeFirestore;
    EditText numberET, whoET;
    Spinner classSP2;
    TextView valuesTV,txt1,txt2;
    DocumentReference objectDocumentReference;
    ImageView bcg;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        try {
            objectFirebsaeFirestore = FirebaseFirestore.getInstance();
            numberET = findViewById(R.id.numberET5);
            whoET = findViewById(R.id.whoET3);
            classSP2 = findViewById(R.id.classSP2);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        bcg=findViewById(R.id.result_img);
        btn=findViewById(R.id.updateBtn);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_update);//app一開始update就被選著
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
        classSP2.setAdapter(adapter);

    }
    public void getValueFromFirebase(View view)
    {
        try {
            if(!numberET.getText().toString().isEmpty()){
                objectDocumentReference=objectFirebsaeFirestore.collection("WhosCall").document(
                        numberET.getText().toString()
                );
                objectDocumentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String number=documentSnapshot.getId();//用id(號碼)才能查詢
                                String who=documentSnapshot.getString("who");
                                String Class=documentSnapshot.getString("class");
                                whoET.setText(who);
                                int n = 1;
                                if(Class.equals("商家"))
                                    n=0;
                                if(Class.equals("詐騙集團"))
                                    n=1;
                                if(Class.equals("一接就掛"))
                                    n=2;
                                if(Class.equals("一般用戶"))
                                    n=3;
                                classSP2.setSelection(n);

                                txt1.setVisibility(View.VISIBLE);
                                whoET.setVisibility(View.VISIBLE);
                                txt2.setVisibility(View.VISIBLE);
                                classSP2.setVisibility(View.VISIBLE);
                                btn.setVisibility(View.VISIBLE);
                                bcg.setImageDrawable(getResources().getDrawable(R.drawable.updateresult));

                        }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_update.this,"fails to get data back",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //更新資訊
    public void updateValue(View view)
    {
        try {
            if(!numberET.getText().toString().isEmpty()) {
                objectDocumentReference = objectFirebsaeFirestore.collection("WhosCall").document(
                        numberET.getText().toString()
                );
                Map<String, Object> objectMap = new HashMap<>();

                if(!whoET.getText().toString().isEmpty()){//若格子有輸入則更新
                    objectMap.put("who", whoET.getText().toString());//將whoET放到who類別裡
                }
                    objectMap.put("class", classSP2.getSelectedItem().toString());//將clsaaSP放到 class類別裡


                objectDocumentReference.update(objectMap)
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity_update.this,"資訊已更新",Toast.LENGTH_SHORT).show();

                                        txt1.setVisibility(View.INVISIBLE);
                                        whoET.setVisibility(View.INVISIBLE);
                                        txt2.setVisibility(View.INVISIBLE);
                                        classSP2.setVisibility(View.INVISIBLE);
                                        btn.setVisibility(View.INVISIBLE);
                                        bcg.setImageDrawable(getResources().getDrawable(R.drawable.updatebcg));
                                        whoET.setText("");
                                    }
                                }
                        )
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_update.this,"資訊更新失敗",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
            else
            {

                Toast.makeText(this, "請提供電話號碼以供更新", Toast.LENGTH_SHORT).show();
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
            case R.id.nav_update:
                break;//在update時按update無效
            case R.id.nav_logout:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent = new Intent(Activity_update.this,Activity_register.class);
                startActivity(intent);//使用intent啟動另一個activity
                break;
            case R.id.nav_security:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent2 = new Intent(Activity_update.this, Activity_security.class);
                startActivity(intent2);//使用intent啟動另一個activity
                break;
            case R.id.nav_home:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent3 = new Intent(Activity_update.this,Activity_homepage.class);
                startActivity(intent3);//使用intent啟動另一個activity
                break;
            case R.id.nav_search:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent4 = new Intent(Activity_update.this,Activity_search.class);
                startActivity(intent4);//使用intent啟動另一個activity
                break;
            case R.id.nav_profile:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent5 = new Intent(Activity_update.this,Activity_profile.class);
                startActivity(intent5);//使用intent啟動另一個activity
                break;
            case R.id.nav_deleteC:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent6 = new Intent(Activity_update.this,Activity_delC.class);
                startActivity(intent6);//使用intent啟動另一個activity
                break;
            case R.id.nav_new:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent8 = new Intent(Activity_update.this,Activity_new.class);
                startActivity(intent8);//使用intent啟動另一個activity
                break;
            case R.id.nav_phonebook:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent9 = new Intent(Activity_update.this,Activity_phonebook.class);
                startActivity(intent9);//使用intent啟動另一個activity
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}