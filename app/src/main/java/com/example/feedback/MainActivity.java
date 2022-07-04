package com.example.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baofu.feedback.RateDialog;
import com.baofu.netlibrary.BPConfig;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.listener.RequestListener;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "UA");
        BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.OKHTTP).addHeader(header)
//                .banProxy(true)
                .setRequestListener(new RequestListener() {
                    @Override
                    public String responseListener(Headers headers, int status, String url, String response) {
                        return response;
                    }

                    @Override
                    public String exceptionListener(String url, String error, Exception e, int code) {
                        return null;
                    }

                })
                .build();
        BPRequest.getInstance().init(config);
        RateDialog rateDialog = new RateDialog(MainActivity.this,"dKU3fGryO}H8Pl7{QmjxPF7{PGL9P}DzPF<p]ZYn\\pImdz@@",true, new RateDialog.RateDialogListener() {
            @Override
            public void onRate() {
            }

            @Override
            public void onFeedback(String message) {
            }

            @Override
            public void onCancel() {

            }
        });
        rateDialog.show();
//        rateDialog.setFeedbackHint("haha");
//        rateDialog.setFeedbackTitle("hello");
    }
}