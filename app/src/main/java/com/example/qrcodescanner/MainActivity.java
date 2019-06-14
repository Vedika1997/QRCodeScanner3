package com.example.qrcodescanner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {
    Button Scan;
    TextView Result;
    public static final int REQUEST_CODE=100;
    public static final int PERMISSION_REQUEST=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Scan = (Button)findViewById(R.id.scan);
        Result = (TextView) findViewById(R.id.result);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST);
        }
        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Scanner.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if (data !=null){
                final Barcode barcode = data.getParcelableExtra("Barcode");
                Result.post(new Runnable() {
                    @Override
                    public void run() {
                        Result.setText(barcode.displayValue);
                    }
                });
            }
        }
    }
}
