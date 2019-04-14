package com.example.jeuxmagie;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static final int PERMISSIONS_REQUEST=1;
    private Activity thisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        thisActivity=this;
        setContentView(R.layout.activity_welcome_screen);


        final Button host= (Button) findViewById(R.id.HostButton);
        host.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StartHostActivity(v);
            }
        });



        final Button guest= (Button) findViewById(R.id.GuestButton);
        guest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StartQRCodeReaderActivity(v);
            }
        });




    }

    public void StartHostActivity(View view){
        Intent intent = new Intent(this, HostActivity.class);
        this.startActivity(intent);
    }

    public void StartQRCodeReaderActivity(View view) {
        Intent intent = new Intent(this, QRCodeReaderActivity.class);
        this.startActivity(intent);
    }
}
