package com.example.saada.project_idea.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import android.os.Handler;

import com.example.saada.project_idea.R;

/**
 * Created by saada on 1/4/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public int  hour, min;

    Calendar c;
    Handler handler;
    private int mHour, mMinute;
    Bundle time_bundle;
    public TimePickerFragment(Handler h){
        c = Calendar.getInstance();
        time_bundle=new Bundle();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        handler=h;

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;
        min = minute;

        time_bundle.putInt("Hour",hour);
        time_bundle.putInt("Minute",min);
        Message m = new Message();
        m.setData(time_bundle);
        handler.sendMessage(m);
        String date=hour +"-"+min;
        //Toast.makeText(getActivity(),date,Toast.LENGTH_SHORT).show();

    }
    public Dialog onCreateDialog(Bundle savedInstanceState){

        return new TimePickerDialog(getActivity(), R.style.DialogTheme,this, mHour, mMinute, false);
    }
}
