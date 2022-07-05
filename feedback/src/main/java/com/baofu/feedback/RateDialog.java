
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
    Activity activity;
    TextView desc, title;
    ImageView icon;
    RatingBar ratingBar;
    /**
     * 星星数量
     */
    int score = 0;

    EditText editText;
    TextView submit;
    TextView descFb;
    boolean showRate;
    public String feedbackUrl;
    public String feedbackPath;
    public String extraMsg;

    private RateDialogListener mListener;

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
        mTvRate = findViewById(R.id.tv_rate);
        ratingBar = findViewById(R.id.ratingBar);
        desc = findViewById(R.id.desc);
        title = findViewById(R.id.title);
        icon = findViewById(R.id.icon);
        descFb = findViewById(R.id.descFb);

        layoutStar = findViewById(R.id.layoutStar);
        layoutFeedBack = findViewById(R.id.layoutFeedBack);
        editText = findViewById(R.id.editText);
        submit = findViewById(R.id.submit);

        mClose.setOnClickListener(this);
        mTvRate.setOnClickListener(this);
        submit.setOnClickListener(this);

        if (showRate) {
            layoutStar.setVisibility(View.VISIBLE);
            layoutFeedBack.setVisibility(View.GONE);
        } else {
            layoutStar.setVisibility(View.GONE);
            layoutFeedBack.setVisibility(View.VISIBLE);
        }

        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                score = (int) ratingCount;
                switch (score) {
                    case 0: {
                        desc.setText(getContext().getString(R.string.feedback_rate_desc));
                        title.setText(getContext().getString(R.string.feedback_rate_title));
                        icon.setImageResource(R.drawable.feedback_star_5);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(false);
                        break;
                    }
                    case 1: {
                        desc.setText(getContext().getString(R.string.feedback_rate_star));
                        title.setText(getContext().getString(R.string.feedback_rate_star_title));
//                        icon.setImageResource(R.drawable.rate_dialog_icon_star_1);
                        icon.setImageResource(R.drawable.feedback_ic_insaver_emoji_rate_1);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    case 2: {
                        desc.setText(getContext().getString(R.string.feedback_rate_star));
                        title.setText(getContext().getString(R.string.feedback_rate_star_title));
//                        icon.setImageResource(R.drawable.rate_dialog_icon_star_2);
                        icon.setImageResource(R.drawable.feedback_ic_insaver_emoji_rate_2);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    case 3: {
                        desc.setText(getContext().getString(R.string.feedback_rate_star));
                        title.setText(getContext().getString(R.string.feedback_rate_star_title));
//                        icon.setImageResource(R.drawable.rate_dialog_icon_star_3);
                        icon.setImageResource(R.drawable.feedback_ic_insaver_emoji_rate_3);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    case 4: {
                        desc.setText(getContext().getString(R.string.feedback_rate_star));
                        title.setText(getContext().getString(R.string.feedback_rate_star_title_4));
//                        icon.setImageResource(R.drawable.rate_dialog_icon_star_4);
                        icon.setImageResource(R.drawable.feedback_ic_insaver_emoji_rate_4);
                        mTvRate.setText(getContext().getString(R.string.feedback_rate));
                        mTvRate.setEnabled(true);
                        break;
                    }
                    case 5: {
                        desc.setText(getContext().getString(R.string.feedback_rate_star));
                        title.setText(getContext().getString(R.string.feedback_rate_star_title_4));
                        icon.setImageResource(R.drawable.feedback_star_5);
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
            Map<String, String> header = new HashMap<>();
            Map<String, String> params = new HashMap<>();
            params.put("message", editText.getText().toString().trim());
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
    public void setFeedbackTitle(String title){
        if(descFb!=null){
            descFb.setText(title);
        }
    }
}
