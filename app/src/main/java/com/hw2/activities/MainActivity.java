package com.hw2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hw2.R;

public class MainActivity extends AppCompatActivity {


    private TextView  welcome;
    private Button startGame;
    private Button topTen;
    LocationManager locationManager;
    private MediaPlayer mp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        chooseActivity();
        checkGPS();

    }

    public void findViews(){
        welcome = findViewById(R.id.welcome);
        startGame = findViewById(R.id.startGame);
        topTen = findViewById(R.id.topten);

    }


    public void chooseActivity(){
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(R.raw.shuffcards);
                startGameActivity();
            }
        });
        topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRecordActivity();
            }
        });

    }

    public void startGameActivity(){
        Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(myIntent);
        finish();

    }

    public void goToRecordActivity(){
        Intent myIntent = new Intent(MainActivity.this, RecordActivity.class);
        startActivity(myIntent);
        finish();



    }


    private void checkGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertMessageNoGPS();
        } else {
            fetchLocation();
        }
    }

    private void alertMessageNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Your GPS seems to be disable, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }


    protected void playSound(int rawName) {

        mp = MediaPlayer.create(this, rawName);
        mp.setOnCompletionListener(mp -> {
            mp.reset();
            mp.release();
        });
        mp.start();
    }
}