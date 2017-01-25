package com.example.saada.project_idea.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * Created by saada on 1/4/2017.
 */

public class SMSBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String SPhone =intent.getStringExtra("exPhone");

        String SSms = intent.getStringExtra("exSmS");


        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage(SPhone, null, SSms, null, null);


    }
}
