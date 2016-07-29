package com.example.drum.hulixia.daggertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.drum.hulixia.R;
import com.google.gson.Gson;

import javax.inject.Inject;

public class AActivity extends AppCompatActivity {
    private TextView textView;
    @Inject
    Gson gson;

    @PoetryQualifier("A")
    @Inject
    Poetry mPoetryA;
    @PoetryQualifier("B")
    @Inject
    Poetry mPoetryB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        MainApplication.getInstance().getaCommponent().inject(this);
        textView = (TextView) findViewById(R.id.tv_a_test);
        textView.setText(mPoetryA.getmPemo() + ",mPoetryB:" + mPoetryB.getmPemo() + (gson == null ? "gosn没有被注入" : "gosn已被注入"));
    }
}
