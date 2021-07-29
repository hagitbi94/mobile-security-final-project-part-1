package com.example.myapplication.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.elements.Record;
import com.example.myapplication.elements.TopTen;
import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

public class WinnerActivity extends AppCompatActivity {
    public static final String SCORE = "SCORE";
    public static final String PLAYER = "PLAYER";
    private Button back;
    TextView Winner_LBL;
    private EditText editText;
    TextView Final_Score_LBL;
    int player = 0;
    int score = 0;
    ImageView winner_IMG;
    String playerName;
    private MediaPlayer mp;
//    private EditText enter_LBL_name;
    private TopTen tenRecords = new TopTen();
    private Record record;
    String records;
    Location currentLocation;
    private LocationCallback mLocationCallback;
    private double lng = 0.0;
    private double lat = 0.0;
    private boolean flag = false;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity);
        findViews();
        initViews();
        setUpLocationDetails();
        loadRecordsFromMemory();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerName = editText.getText().toString();
                record = new Record(playerName, "", score, 0.0,0.0);

                Intent myIntent = new Intent(WinnerActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();

//                getLocation();
            }
        });


    }




    public void findViews(){
        winner_IMG = findViewById(R.id.winner_IMG);
        Winner_LBL = findViewById(R.id.Winner_LBL);
        back = findViewById(R.id.backToMenu);
        Final_Score_LBL = findViewById(R.id.Final_Score_LBL);
        editText = findViewById(R.id.enter_LBL_name);


    }


    protected void playSound(int rawName) {

        mp = MediaPlayer.create(this, rawName);
        mp.setOnCompletionListener(mp -> {
            mp.reset();
            mp.release();
        });
        mp.start();
    }


    /*
       get the score of each player, and the avatar of the winner to show on the screen
     */
    private void initViews() {
        player = getIntent().getIntExtra(PLAYER, -1);
        score = getIntent().getIntExtra(SCORE, -1);
        playSound(R.raw.win);
        if (player == 1) {
            winner_IMG.setImageResource(R.drawable.morty);
            Winner_LBL.setText("The Winner is: player " + player + "\n");
            Final_Score_LBL.setText("Your Score\n" + score);

        } else if (player == 2) {
            winner_IMG.setImageResource(R.drawable.rick);
            Winner_LBL.setText("The Winner is: player " + player + "\n");
            Final_Score_LBL.setText("Your Score\n" + score);

        } else {
            Winner_LBL.setText("     It's A Tie!!");
            Final_Score_LBL.setText("Your Score\n " + score);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(WinnerActivity.this, MainActivity.class);
                    startActivity(myIntent);
                    finish();
                }
            });
        }

    }


    private void setUpLocationDetails(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(WinnerActivity.this);
        this.mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location mLastLocation = locationResult.getLastLocation();
                lat = mLastLocation.getLatitude();
                lng = mLastLocation.getLongitude();
            }
        };
    }


    private void loadRecordsFromMemory() {
        SharedPreferences sp = getSharedPreferences("MY_SP", MODE_PRIVATE);
        if (!sp.getAll().isEmpty()) {
            records = sp.getString("playerRecord", "No Records Defined");
            tenRecords = new Gson().fromJson(records, TopTen.class);
        }
    }

    private void saveRecordToMemory() {
        SharedPreferences.Editor editor = getSharedPreferences("MY_SP", MODE_PRIVATE).edit();
        tenRecords.addRecord(record);
        String ttJason = new Gson().toJson(tenRecords);
        editor.putString("playerRecord", ttJason);
        editor.apply();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        flag = true;
                        currentLocation = location;
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        record.setLat(lat);
                        record.setLng(lng);
                        saveRecordToMemory();
                    } else {
                        requestNewLocationData();
                    }
                }
            });
            task.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (flag) {
                        Intent myIntent = new Intent(WinnerActivity.this, MainActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                }
            });
        }
    }


    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, this.mLocationCallback, Looper.myLooper());
        }
    }
}
