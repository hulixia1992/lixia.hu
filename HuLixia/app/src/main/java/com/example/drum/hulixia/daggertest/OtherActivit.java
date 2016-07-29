package com.example.drum.hulixia.daggertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.drum.hulixia.R;
import com.google.gson.Gson;

import javax.inject.Inject;

public class OtherActivit extends AppCompatActivity {

    @Inject
    Poetry poetry;
    @Inject
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        TestCommpent.getInstance().inject(this);
        initView();
    }

    private void initView() {
        TextView tvOther = (TextView) findViewById(R.id.tv_other);
        String jsonString = gson.toJson(poetry);
        tvOther.setText(jsonString + "poetry:" + poetry);
    }
}
