package com.example.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.baofu.feedback.RateDialog;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "UA");
        RateDialog rateDialog = new RateDialog(MainActivity.this,true, new RateDialog.RateDialogListener() {
            @Override
            public void onRate() {
            }

            @Override
            public void onFeedback(String message,int star,String email) {
                if(TextUtils.isEmpty(message)){
                    return;
                }
//                Map<String, String> header = new HashMap<>();
//                Map<String, String> params = new HashMap<>();
//                params.put("message", message );
//                params.put("star", star + "");
//                params.put("user", email);
//                BPRequest.getInstance()
//                        .setMethod(BPRequest.Method.POST)
////                    .setUrl("http://192.168.0.101:3000/feedback")
//                        .setUrl(AppConfig.BASE_URL)
//                        .appenEncryptPath("/feedback")
//                        .setParams(params)
//                        .setHeader(header)
//                        .setOnResponseString(new BPListener.OnResponseString() {
//                            @Override
//                            public void onResponse(String response) {
//                                Log.i("RateDialog", response);
//                            }
//                        })
//                        .setOnException(new BPListener.OnException() {
//                            @Override
//                            public void onException(Exception e, int code, String response) {
//                                e.printStackTrace();
//                            }
//
//                        })
//                        .request();
            }

            @Override
            public void onCancel() {

            }
        });
        int[] drawable = new int[]{
                R.drawable.ic_rate1, R.drawable.ic_rate2,
                R.drawable.ic_rate3, R.drawable.ic_rate4,
                R.drawable.ic_rate5
        };
        rateDialog.setRateDrawable(drawable);
        rateDialog.show();
        rateDialog.showEmail(true);
        rateDialog.feedbackUrl="url";
        rateDialog.feedbackPath="path";
//        rateDialog.setFeedbackHint("haha");
//        rateDialog.setFeedbackTitle("hello");
    }
}