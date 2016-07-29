package com.example.drum.hulixia.daggertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.drum.hulixia.R;
import com.google.gson.Gson;

import javax.inject.Inject;

public class DraggerTestActivity extends AppCompatActivity {

    @Inject
    Poetry poetry;

    @Inject
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragger_test);
        TestCommpent.getInstance()
                .inject(this);
        initView();
    }

    private void initView() {
        TextView tvTest = (TextView) findViewById(R.id.tv_dragger_test);
        String poetryJson = gson.toJson(poetry);
        tvTest.setText(poetryJson + "poetry:" + poetry);
        findViewById(R.id.btn_goto_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DraggerTestActivity.this, AActivity.class);
                startActivity(intent);
            }
        });
    }
}
