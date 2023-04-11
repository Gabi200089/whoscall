package com.example.whoscall;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * 来电监听服务
 *
 * @author : Joh Liu
 * @date : 2019/8/15 16:44
 */
public class PhoneListenService extends Service {

    private static final String TAG = "PhoneListenService";

    // 电话管理者对象
    private TelephonyManager mTelephonyManager;
    // 电话状态监听者
    private MyPhoneCallListener phoneCallListener;
    FirebaseFirestore objectFirebsaeFirestore;
    DocumentReference objectDocumentReference;
    String result;
    String r="未知";

    @Override
    public void onCreate() {
        objectFirebsaeFirestore = FirebaseFirestore.getInstance();
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneCallListener = new MyPhoneCallListener();
        phoneCallListener.setPhoneListener(number -> {

            if (!TextUtils.isEmpty(number)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(getApplicationContext())) {
                        getValueFromFirebase(number);
//                        WindowsUtils.showPopupWindow(getApplicationContext(), r +"\n"+number);
                    }
                } else {
                    WindowsUtils.showPopupWindow(getApplicationContext(), r +"\n"+ number);
                }
            }
        });
        mTelephonyManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void getValueFromFirebase(String number)
    {
        try {
                objectDocumentReference=objectFirebsaeFirestore.collection("WhosCall").document(
                        number
                );
                objectDocumentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String number=documentSnapshot.getId();//用id(號碼)才能查詢
                                String who=documentSnapshot.getString("who");
                                String Class=documentSnapshot.getString("class");

                                if(who==null && Class==null)
                                    WindowsUtils.showPopupWindow(getApplicationContext(), "未知"+"\n"+number);
                                else
                                    WindowsUtils.showPopupWindow(getApplicationContext(), "名稱:"+who+"("+Class+")"+"\n"+"號碼:"+number);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PhoneListenService.this,"fails to get data back",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
//    @Override
//    public void onDestroy() {
//        // 取消来电的电话状态监听服务
//        if (mTelephonyManager != null && myPhoneStateListener != null) {
//            mTelephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
//        }
//        super.onDestroy();
//    }
}
