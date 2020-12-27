package com.hw2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.hw2.R;
import com.hw2.callbackes.RecordTableCallBack;
import com.hw2.elements.Record;
import com.hw2.elements.TopTen;
import com.hw2.fragments.ListFragment;
import com.hw2.fragments.MapFragment;
import java.util.Collections;



public class RecordActivity extends AppCompatActivity implements RecordTableCallBack {

    public static final String NAME = "NAME";
    public static final String SCORE = "SCORE";
    private Button backToMain;
    private String record;
    private String name;
    private int score;
    private double lat = 0.0;
    private double lng = 0.0;
    private int lastPlaceScore;
    private TopTen tenRec;
    private ListFragment listFragment;
    private MapFragment mapFragment;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.record_activity);
        super.onCreate(savedInstanceState);

        listFragment = new ListFragment();
        mapFragment = new MapFragment();
        findViews();
        SharedPreferences prefs = getSharedPreferences("MY_SP", MODE_PRIVATE);
        if(!prefs.getAll().isEmpty()) {
            record = prefs.getString("playerRecord", "No Records Defined");
            tenRec = new Gson().fromJson(record, TopTen.class);
            Collections.sort(tenRec.getRecords(), Record.RecordComperator);
            Bundle bundle = new Bundle();
            Log.d("pppttttttt", "" + tenRec.getRecords().size());

            bundle.putInt("numOfRecords", tenRec.getRecords().size());
            for (int i = 0; i < tenRec.getRecords().size(); i++) {
                name = tenRec.getRecords().get(i).getName();
                score = tenRec.getRecords().get(i).getScore();
                lng = tenRec.getRecords().get(i).getLng();
                lat = tenRec.getRecords().get(i).getLat();
//                Log.d("name+score+loc", name +"|"+score+ "|lat:" + lat +"|lng:" + lng);
                bundle.putString(ListFragment.NAME + i, name);
                bundle.putInt(ListFragment.SCORE + i, score);
                bundle.putDouble(ListFragment.LAT + i, lat);
                bundle.putDouble(ListFragment.LNG + i, lng);
                listFragment.setArguments(bundle);
            }
        }
        getSupportFragmentManager().beginTransaction().add(R.id.main_LAY_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_LAY_map, mapFragment).commit();
        initViews();



    }



    private void initViews() {
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RecordActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }


    private void findViews(){
        backToMain = findViewById(R.id.backToMenu);

    }



    @Override
    public void playerCB(Record record) {

        mapFragment.getUserLocation(record);
    }


}
