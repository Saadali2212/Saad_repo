package com.example.saada.project_idea.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saada.project_idea.BO.SmsBO;
import com.example.saada.project_idea.R;

import java.util.List;

/**
 * Created by saada on 12/25/2016.
 */
public class SMSAdapter extends BaseAdapter {

    List<SmsBO>result;
    Context context;

    private static LayoutInflater inflater = null;



    public SMSAdapter(Context c, List<SmsBO> prgmNameList) {
        result = prgmNameList;
        this.context = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.sms_list_item, null);
        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.number = (TextView) rowView.findViewById(R.id.num);
        holder.date_time = (TextView) rowView.findViewById(R.id.date_time);
        holder.message = (TextView) rowView.findViewById(R.id.message);



        holder.name.setText(result.get(i).getName());
        holder.number.setText(result.get(i).getNumber());
        holder.date_time.setText(result.get(i).getDate());
        holder.message.setText(result.get(i).getMessage());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "hello", Toast.LENGTH_LONG).show();
            }
        });
        //return rowView;
        return rowView;
    }

    public class Holder {
        TextView name;
        TextView number;
        TextView date_time;
        TextView message;

    }
    public void swapSMS(List<SmsBO> sms) {
        result= sms;
        notifyDataSetChanged();
    }

}


