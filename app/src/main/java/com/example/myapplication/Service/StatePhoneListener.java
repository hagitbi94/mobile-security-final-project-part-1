package com.example.myapplication.Service;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.myapplication.Aid.AidFunction;
import com.example.myapplication.Object.Call;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatePhoneListener extends PhoneStateListener {
    private Call call;
    private Context context;
    private AidFunction aidFunction = new AidFunction();

    private int lastState = TelephonyManager.CALL_STATE_IDLE;

    private long start_time, end_time;
    private boolean isIncoming = false;
    private String pattern = "dd.MM.yyyy HH:mm:ss";

    public StatePhoneListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        DateFormat df = new SimpleDateFormat(pattern);

        // Check call state
        switch (state) {
            // When the phone rings
            case TelephonyManager.CALL_STATE_RINGING:
                call = new Call();

                // Save incoming number
                call.setCallNumber(incomingNumber);

                call.setCallStartTime(df.format(new Date()));
                isIncoming = true;
                break;

            // When the user answers the phone call
            case TelephonyManager.CALL_STATE_OFFHOOK:
                start_time = System.currentTimeMillis();

                call.setCallStartTime(df.format(new Date()));
                break;

            // When the call idle   
            case TelephonyManager.CALL_STATE_IDLE:
                // Checks if the user answered the call
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    call.setDuration("Not Answered");
                } else if (isIncoming) {
                    end_time = System.currentTimeMillis();

                    call.setDuration(aidFunction.duration(start_time, end_time));

                    isIncoming = false;
                }

                String name = aidFunction.checkIfNumberInContactList(context, call.getCallNumber());

                // Check if colling number exist in contact list
                call.setExist(!name.equals("null"));
                call.setCallName(name);

                // Save call info to DB
                saveCallToDB(call);
                break;
        }

        lastState = state;
    }

    // Save call information to DB
    private void saveCallToDB(Call call) {
        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference();

//        mDatabase.child("CALL/").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/").child(mDatabase.push().getKey() + "/").setValue(call);
        mDatabase.child("CALL/").child("/MQJFZkdDklQQoIAjTSZoAmP3IIy1" + "/").child(mDatabase.push().getKey() + "/").setValue(call);

    }
}
