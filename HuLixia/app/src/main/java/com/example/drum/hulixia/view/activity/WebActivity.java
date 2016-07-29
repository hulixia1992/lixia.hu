package com.example.drum.hulixia.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextSwitcher;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.constants.Constants;
import com.example.drum.hulixia.view.BaseActivity;

public class WebActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    private void initView() {
        initTitleView(getIntent().getStringExtra(Constants.WEB_TITLE));
        webView = (WebView) findViewById(R.id.web_view);
        webView.loadUrl(getIntent().getStringExtra(Constants.WEB_URL));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
    }
}
