package com.example.saada.project_idea.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.saada.project_idea.R;

import java.util.ArrayList;

/**
 * Created by saada on 1/2/2017.
 */
public class Contacts_adapter extends BaseAdapter {

    ArrayList<String> result_name;
    String[] result_num;
    Context context;

    private static LayoutInflater inflater = null;


    public Contacts_adapter(Context c, ArrayList<String> nameArray) {
        result_name = nameArray;
        //result_num=numArray;
        this.context = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result_name.size();
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
        rowView = inflater.inflate(R.layout.contact_item, null);
        holder.tv_name = (TextView) rowView.findViewById(R.id.name);
       holder.tv_num = (TextView) rowView.findViewById(R.id.number);
        String[] separated = result_name.get(i).split(":");

        holder.tv_name.setText("" + separated[0]);
        if (separated[1].toString().contains("042")){
            holder.tv_num.setText("Home  " + separated[1]);
        }
         else {
            holder.tv_num.setText("Mobile  " + separated[1]);
        }

       /* rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, result_name[], Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }

    public class Holder {
        TextView tv_name;
        TextView tv_num;
    }
}
