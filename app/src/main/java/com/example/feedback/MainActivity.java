package com.example.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.baofu.feedback.RateDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RateDialog rateDialog = new RateDialog(MainActivity.this,"http://192.168.0.101:3000/feedback",true, new RateDialog.RateDialogListener() {
            @Override
            public void onRate() {
            }

            @Override
            public void onFeedback() {
            }

            @Override
            public void onCancel() {

            }
        });
        rateDialog.show();
    }
}