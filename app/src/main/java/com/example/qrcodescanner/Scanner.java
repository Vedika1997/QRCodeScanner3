package com.example.qrcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Scanner extends AppCompatActivity {
  SurfaceView Cameraview;
  BarcodeDetector Barcode;
  CameraSource Cs;
  SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        Cameraview = (SurfaceView)findViewById(R.id.cameraview);
        Cameraview.setZOrderMediaOverlay(true);
        holder = Cameraview.getHolder();
        Barcode = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(com.google.android.gms.vision.barcode.Barcode.QR_CODE)
                .build();
        if(!Barcode.isOperational()){
            Toast.makeText(getApplicationContext(),"Sorry counldn't set up the detector",Toast.LENGTH_SHORT).show();
            this.finish();}
        Cs = new CameraSource.Builder(this,Barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setRequestedPreviewSize(1920,1024)
                .build();
        Cameraview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ContextCompat.checkSelfPermission(Scanner.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                        Cs.start(Cameraview.getHolder());

                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        Barcode.setProcessor(new Detector.Processor<com.google.android.gms.vision.barcode.Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<com.google.android.gms.vision.barcode.Barcode> detections) {
            final SparseArray<Barcode> barcodes = detections.getDetectedItems();
            if(barcodes.size()>0 ){

                Intent intent  = new Intent();
                intent.putExtra("Barcode", barcodes.valueAt(0));
                setResult(RESULT_OK,intent);
                finish();

            }
            }
        });
    }

}
