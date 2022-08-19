
package com.baofu.feedback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.baofu.feedback.widget.RatingBar;
import com.baofu.netlibrary.BPListener;
import com.baofu.netlibrary.BPRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 评价&反馈 框
 */
public class RateDialog extends Dialog implements View.OnClickListener {

    ConstraintLayout layoutStar, layoutFeedBack;
    ImageView mClose;
    TextView mTvRate;
    TextView mCancel;
    Activity activity;
    TextView desc, title;
    ImageView icon;
    RatingBar ratingBar;
    /**
     * 星星数量
     */
    int score = 0;

    EditText editText;
    EditText etEmail;
    TextView submit;
    TextView descFb;
    TextView fb_title;
    boolean showRate;
    public String feedbackUrl;
    public String feedbackPath;
    public String extraMsg;

    private RateDialogListener mListener;
    int drawable[]=new int[]{
            R.drawable.rate1,R.drawable.rate2,R.drawable.rate3,R.drawable.rate4,R.drawable.rate5
    };

    public RateDialog(Activity activity, boolean showRate, RateDialogListener listener) {
        super(activity, R.style.FeedbackDialogStyle);
        this.mListener = listener;
        this.activity = activity;
        this.showRate = showRate;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_dialog_rate);

        setCanceledOnTouchOutside(false);

        mClose = findViewById(R.id.close);
        mCancel = findViewById(R.id.cancel);
        mTvRate = findViewById(R.id.tv_rate);
        etEmail = findViewById(R.id.etEmail);
        ratingBar = findViewById(R.id.ratingBar);
        desc = findViewById(R.id.desc);
        title = findViewById(R.id.title);
        icon = findViewById(R.id.icon);
        descFb = findViewById(R.id.descFb);
        fb_title = findViewById(R.id.fb_title);

        layoutStar = findViewById(R.id.layoutStar);
        layoutFeedBack = findViewById(R.id.layoutFeedBack);
        editText = findViewById(R.id.editText);
        submit = findViewById(R.id.submit);

        mClose.setOnClickListener(this);
        mTvRate.setOnClickListener(this);
        submit.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        if (showRate) {
            layoutStar.setVisibility(View.VISIBLE);
            layoutFeedBack.setVisibility(View.GONE);
        } else {
            layoutStar.setVisibility(View.GONE);
            layoutFeedBack.setVisibility(View.VISIBLE);
        }
        icon.setImageResource(drawable[4]);
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                score = (int) ratingCount;
                switch (score) {
                    case 0: {
                        desc.setText(getContext().getString(R.string.feedback_rate_desc));
                        title.setText(getContext().getString(R.string.feedback_rate_title));
                        icon.setImageResource(drawable[4]);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(false);
                        break;
                    }
                    case 1: {
                        desc.setText(getContext().getString(R.string.feedback_rate_desc_start1));
                        title.setText(getContext().getString(R.string.feedback_rate_title_star1));
//                        icon.setImageResource(R.drawable.rate_dialog_icon_star_1);
                        icon.setImageResource(drawable[0]);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    case 2: {
                        desc.setText(getContext().getString(R.string.feedback_rate_desc_start2));
                        title.setText(getContext().getString(R.string.feedback_rate_title_star1));
//                        icon.setImageResource(R.drawable.rate_dialog_icon_star_2);
                        icon.setImageResource(drawable[1]);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    case 3: {
                        desc.setText(getContext().getString(R.string.feedback_rate_star3));
                        title.setText(getContext().getString(R.string.feedback_rate_title_star1));
//                        icon.setImageResource(R.drawable.rate_dialog_icon_star_3);
                        icon.setImageResource(drawable[2]);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    case 4: {
                        desc.setText(getContext().getString(R.string.feedback_rate_star3));
                        title.setText(getContext().getString(R.string.feedback_rate_title_star4));
//                        icon.setImageResource(R.drawable.rate_dialog_icon_star_4);
                        icon.setImageResource(drawable[3]);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    case 5: {
                        desc.setText(R.string.feedback_rate_desc_start5);
                        title.setText(getContext().getString(R.string.feedback_rate_title_star4));
                        icon.setImageResource(drawable[4]);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate_start_5));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    default:
                        break;
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submit.setEnabled(!TextUtils.isEmpty(s.toString().trim()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resetWidth();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_rate) {
            // 5颗星直接评价，其它都需要反馈
            if (score == 5) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getApplicationInfo().packageName);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    intent.setPackage("com.android.vending");
                    activity.startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(activity.getPackageManager()) != null) {
                        activity.startActivity(Intent.createChooser(intent, ""));
                    } else {
                        activity.startActivity(intent);
                    }
                }
                FeedbackSharePreference.savePraise(activity, true);
                if (mListener != null) {
                    mListener.onRate();
                }
                cancel();
            } else {
                layoutStar.setVisibility(View.GONE);
                layoutFeedBack.setVisibility(View.VISIBLE);
            }
        } else if (i == R.id.submit) {
            String message=editText.getText().toString().trim();
            if(TextUtils.isEmpty(message)){
                return;
            }
            String email = etEmail.getText().toString().trim();
            Map<String, String> header = new HashMap<>();
            Map<String, String> params = new HashMap<>();
            params.put("message", message + " " + email);
            params.put("app", activity.getApplication().getPackageName());
            params.put("star", score + "");
            params.put("version", FeedbackUtils.getAppVersionName(getContext()));
            params.put("versioncode", FeedbackUtils.getAppVersionCode(getContext()) + "");
            params.put("device", FeedbackUtils.getDeviceInfo(getContext()));
            params.put("extra_msg", extraMsg);
            BPRequest.getInstance()
                    .setMethod(BPRequest.Method.POST)
//                    .setUrl("http://192.168.0.101:3000/feedback")
                    .setUrl(feedbackUrl)
                    .encryptionUrl(true)
                    .encryptionDiff(-3)
                    .appenEncryptPath(feedbackPath)
                    .setParams(params)
                    .setHeader(header)
                    .setOnResponseString(new BPListener.OnResponseString() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("RateDialog", response);
                        }
                    })
                    .setOnException(new BPListener.OnException() {
                        @Override
                        public void onException(Exception e, int code, String response) {
                            e.printStackTrace();
                        }

                    })
                    .request();
            Toast.makeText(activity, activity.getString(R.string.feedback_fb_success), Toast.LENGTH_SHORT).show();
            FeedbackSharePreference.savePraise(activity, true);
            if (mListener != null) {
                mListener.onFeedback(editText.getText().toString().trim());
            }
            cancel();
        }else if(i == R.id.cancel){
            cancel();
        }else if(i == R.id.close){
            cancel();
        }
    }

    public interface RateDialogListener {
        void onRate();

        void onFeedback(String message);

        void onCancel();
    }

    /**
     * 反馈模式
     */
    public void feedBack() {
        layoutStar.setVisibility(View.GONE);
        layoutFeedBack.setVisibility(View.VISIBLE);
    }

    public void resetWidth() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = getContext().getResources().getDisplayMetrics().widthPixels * 80 / 100;
        getWindow().setAttributes(params);
    }

    public void setFeedbackHint(String hint){
        if(editText!=null){
            editText.setHint(hint);
        }
    }
    public void setFeedbackDesc(String title){
        if(descFb!=null){
            descFb.setText(title);
        }
    }
    public void showFeedbackDesc(boolean show){
        if(descFb!=null){
           if(show){
               descFb.setVisibility(View.VISIBLE);
           }else {
               descFb.setVisibility(View.GONE);
           }
        }
    }
    public void setFeedbackTitle(String title){
        if(fb_title!=null){
            fb_title.setText(title);
        }
    }
    public void showFeedbackTitle(boolean show){
        if(fb_title!=null){
           if(show){
               fb_title.setVisibility(View.VISIBLE);
           }else {
               fb_title.setVisibility(View.GONE);
           }
        }
    }
    public void setSubmitText(String title){
        if(submit!=null){
            submit.setText(title);
        }
    }

    public void setRateDrawable(int[] drawable){
        if(drawable.length<5){
            return;
        }
        this.drawable=drawable;
        if(icon!=null){
            icon.setImageResource(drawable[4]);
        }
    }
    public void showRateDesc(boolean show){
        if(desc!=null){
            if(show){
                desc.setVisibility(View.VISIBLE);
            }else {
                desc.setVisibility(View.GONE);
            }
        }
    }

    public void showEmail(boolean show){
        if(etEmail!=null){
            if(show){
                etEmail.setVisibility(View.VISIBLE);
            }else {
                etEmail.setVisibility(View.GONE);
            }
        }
    }
    public void setEmailHint(String hint){
        if(etEmail!=null){
            if(etEmail!=null){
                etEmail.setHint(hint);
            }
        }
    }
}
