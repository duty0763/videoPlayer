package com.example.videoplayer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private Fragment localVideoFragment, onlineVideoFragment;
    private Boolean flag;
    private TextView localVideo;
    private TextView onlineVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flag=false;


        viewPager = findViewById(R.id.viewPager);
        localVideo = findViewById(R.id.localVideo);
        onlineVideo = findViewById(R.id.onlineVideo);

        initView();

        localVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        onlineVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                onlineVideo.setTextColor(Color.parseColor(flag ? "#AAFFFFFF" : "#ffffff"));
                localVideo.setTextColor(Color.parseColor(flag ? "#ffffff" : "#AAFFFFFF"));
                flag = !flag;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initView() {

        localVideoFragment = new LocalVideoFragment();
        onlineVideoFragment = new OnlineVideoFragment();
        setAdapter();
    }

    private void setAdapter() {

        fragmentList = new ArrayList<>();
        fragmentList.add(localVideoFragment);
        fragmentList.add(onlineVideoFragment);

        //建立Fragment页面适配器对象
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragmentList);

        //给viewPager设置上适配器
        viewPager.setAdapter(adapter);

    }
}

