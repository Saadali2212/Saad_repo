package com.example.saada.project_idea.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.saada.project_idea.R;

import java.util.Calendar;

/**
 * Created by saada on 1/17/2017.
 */

public class DatePickerFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener  {
    Handler handler;
    Calendar c;
   public int year,month,day;
    public int myear,mmonth,mday;


    public DatePickerFragment(Handler hand){
        c=Calendar.getInstance();
        myear=c.get(Calendar.YEAR);
      mmonth=c.get(Calendar.MONTH);
        mday=c.get(Calendar.DAY_OF_MONTH);
       handler=hand;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year_x, int month_x, int day_x) {
        year=year_x;
        month=month_x;
        day=day_x;
        Bundle time_bundle=new Bundle();
        time_bundle.putInt("year",year);
        time_bundle.putInt("month",month);
        time_bundle.putInt("day",day);
        Message m = new Message();
        m.setData(time_bundle);
        handler.sendMessage(m);
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){

        return new android.app.DatePickerDialog(getActivity(), R.style.DialogTheme,this,myear,mmonth,mday);
    }

}
