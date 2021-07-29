package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.myapplication.elements.Record;
import com.example.myapplication.elements.TopTen;
import com.example.myapplication.R;

public class Adapter extends ArrayAdapter<Record> {

    private final Context context;
    private final TopTen tenRecords;
    int resource;
    private Record record = new Record();


    public Adapter(@NonNull Context context, int resource, TopTen tenRecords) {
        super(context, resource, tenRecords.getRecords());
        this.context = context;
        this.tenRecords = tenRecords;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);
        TextView title_LBL = (TextView) rowView.findViewById(R.id.title_LBL);
        TextView subTitle_LBL = (TextView) rowView.findViewById(R.id.subTitle_LBL);
        TextView location_LBL = rowView.findViewById(R.id.location_LBL);
        TextView time_LBL = rowView.findViewById(R.id.time_LBL);
        title_LBL.setText((position+1)+") "+tenRecords.getRecord(position).getName());
        subTitle_LBL.setText(tenRecords.getRecord(position).getDate()+ "       ");
        location_LBL.setText(tenRecords.getRecord(position).getLat()+ " , "+tenRecords.getRecords().get(position).getLng());
        time_LBL.setText("Score:" + tenRecords.getRecord(position).getScore());
        return rowView;
    }

}
