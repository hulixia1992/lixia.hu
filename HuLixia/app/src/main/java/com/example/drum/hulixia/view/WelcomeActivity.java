package com.example.drum.hulixia.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.databinding.ActivityWelcomeBinding;
import com.example.drum.hulixia.model.UserModel;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    private static final String PERMISSON_READ_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    private static final int REQUEST_PHONE_PERMISSON = 2;//请求获取设别信息权限
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_welcome);
        binding.setUser(new UserModel("hulixia_hahha","24"));
        initPermisson();
    }

    private void initPermisson() {
        if (Build.VERSION.SDK_INT >= 23) {
            whenVersionMoreThan23();
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void whenVersionMoreThan23() {
        int readPermission = ActivityCompat.checkSelfPermission(this, PERMISSON_READ_STORAGE);
        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            ArrayList<String> needQuest = new ArrayList<>();
            needQuest.add(PERMISSON_READ_STORAGE);
            String[] array = new String[needQuest.size()];
            ActivityCompat.requestPermissions(WelcomeActivity.this, needQuest.toArray(array), REQUEST_PHONE_PERMISSON);
        } else {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
