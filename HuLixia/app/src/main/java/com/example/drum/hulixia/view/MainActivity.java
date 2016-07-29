package com.example.drum.hulixia.view;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.adapter.MainStatePagerAdapter;
import com.example.drum.hulixia.presenter.MainPresenter;
import com.example.drum.hulixia.view.fragment.FoodsFragment;
import com.example.drum.hulixia.view.fragment.MainFragment;
import com.example.drum.hulixia.view.fragment.NewFragment;
import com.example.drum.hulixia.view.inter.IMainView;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity implements IMainView {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager vpMain;
    private FloatingActionButton fabBottom;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(MainActivity.this);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();

    }

    private void initData() {
        mainPresenter = new MainPresenter(this);
    }

    private void initListener() {
        fabBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.showSnackToast(view);
            }
        });
    }

    private void initView() {
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("主界面");
        fabBottom = (FloatingActionButton) findViewById(R.id.fab);
        tabLayout = (TabLayout) findViewById(R.id.tl_layout);
        vpMain = (ViewPager) findViewById(R.id.view_pager);
        // tabLayout.addTab(tabLayout.newTab().setText("Main"));
        // tabLayout.addTab(tabLayout.newTab().setText("新闻"));
        // tabLayout.addTab(tabLayout.newTab().setText("美食"));
        FragmentManager fm = getSupportFragmentManager();
        // FragmentPagerAdapter mAdapter = new MainFragmentPagerAdapter(fm);
        MainStatePagerAdapter mAdapter = new MainStatePagerAdapter(fm);
        mAdapter.addTab(MainFragment.getInstance(), "main");
        mAdapter.addTab(NewFragment.getInstance(), "新闻");
        mAdapter.addTab(FoodsFragment.getInstance(), "美食");
        vpMain.setAdapter(mAdapter);
        // tabLayout.setTabsFromPagerAdapter(mAdapter);
        tabLayout.setupWithViewPager(vpMain);
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (km.inKeyguardRestrictedInputMode()) {
            // 处于锁屏状态
            Intent intent = new Intent(this, AlertActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Snackbar.make(fabBottom, "退出程序？", Snackbar.LENGTH_SHORT).setAction("退出", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        }).show();
    }

    @Override
    public void showSnackToast(View view) {
        Snackbar.make(view, "查看个人信息?", Snackbar.LENGTH_SHORT).setAction("查看", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "将跳转到个人信息", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
}
