package com.example.drum.hulixia.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.constants.Constants;

/**
 * Created by hulixia on 2016/7/11.
 * activty基类
 */
public class BaseActivity extends AppCompatActivity {
    private TextSwitcher tvTitle;
    private Toolbar toolbar;

    private void initTitle(String title) {
        tvTitle = (TextSwitcher) findViewById(R.id.tv_title);
        tvTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView textView = new TextView(BaseActivity.this);
                textView.setTextAppearance(BaseActivity.this, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                }, 1738);
                return textView;
            }
        });
        tvTitle.setInAnimation(this, android.R.anim.fade_in);
        tvTitle.setOutAnimation(this, android.R.anim.fade_out);
        tvTitle.setText(title);
        //  tvTitle.setText(getIntent().getStringExtra(Constants.WEB_TITLE));
    }

    public void initTitleString(String title) {
        tvTitle.setText(title);
    }

    public void initTitleView(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(getIntent().getStringExtra(Constants.WEB_TITLE));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });
        initTitle(title);
    }

    public void finishActivity() {
        finish();
    }
}
