package com.example.whoscall;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * 来电广播捕获
 *
 * @author : Joh Liu
 * @date : 2019/8/15 11:14
 */
public class PhoneReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneReceiver";

    private Context mcontext;
    private MyPhoneCallListener phoneCallListener = new MyPhoneCallListener();
    FirebaseFirestore objectFirebsaeFirestore;
    DocumentReference objectDocumentReference;
    String result;
    String r="未知";

    @Override
    public void onReceive(Context context, Intent intent) {
        objectFirebsaeFirestore = FirebaseFirestore.getInstance();
        mcontext = context;
        // 非拨出状态
        if (intent.getAction() != null && !intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            // 查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            // 设置一个监听器
            if (tm != null) {
                phoneCallListener.setPhoneListener(number -> {
                    if (!TextUtils.isEmpty(number)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (Settings.canDrawOverlays(mcontext)) {
                                getValueFromFirebase(number);
                            }
                        } else {
                            com.example.whoscall.WindowsUtils.showPopupWindow(mcontext, r +"\n"+ number);
                        }
                    }
                });
                tm.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);
            }
        }
    }
    //取得firestore資料
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
                            com.example.whoscall.WindowsUtils.showPopupWindow(mcontext,  "名稱:"+who+"("+Class+")"+"\n"+"號碼:"+number);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(PhoneReceiver.this,"fails to get data back",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (Exception e)
        {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

