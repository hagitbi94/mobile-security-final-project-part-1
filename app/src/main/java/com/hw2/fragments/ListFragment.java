package com.hw2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hw2.R;
import com.hw2.adapters.Adapter;
import com.hw2.callbackes.RecordTableCallBack;
import com.hw2.elements.Record;
import com.hw2.elements.TopTen;

public class ListFragment extends Fragment {
    private ListView list_records;
    public static final String NAME = "NAME";
    public static final String SCORE = "SCORE";
    public static final String LAT = "LAT";
    public static final String LNG = "LNG";
    private String name;
    private int score;
    private double lat= 0.0;
    private double lng = 0.0;
    private TopTen tenRecords = new TopTen();
    private int numOfRecords;
    private Record record ;
    private RecordTableCallBack recordTableCallBack;


    public ListFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        findViews(view);
        initViews();
        return view;
    }



    private void initViews() {
        createRecordsList();
        list_records.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record record2 = tenRecords.getRecords().get(position);
                recordTableCallBack.playerCB(record2);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RecordTableCallBack ) {
            recordTableCallBack = (RecordTableCallBack) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    private void createRecordsList(){
        Bundle bundle = this.getArguments();
        if(bundle!= null) {
            numOfRecords = bundle.getInt("numOfRecords", numOfRecords);
            if (numOfRecords > 10) {
                numOfRecords = 10;
            }
            for (int i = 0; i < numOfRecords; i++) {
                name = bundle.getString(NAME + i, name);
                score = bundle.getInt(SCORE + i, score);
                lat = bundle.getDouble(LAT+i, lat);
                lng = bundle.getDouble(LNG+i, lng);
                record = new Record(name, "", score, lat, lng);
                tenRecords.addRecord(record);
            }
        }
        final Adapter adapter = new Adapter(getContext(), R.layout.one_record_for_list, tenRecords);
        list_records.setAdapter(adapter);
    }






    private void findViews(View view){
        list_records = view.findViewById(R.id.list_records);
    }

}
