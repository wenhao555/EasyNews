package com.example.easynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.example.easynews.utils.FragmentFactory;
import com.example.easynews.views.TabPageIndicator;

public class MainActivity extends AppCompatActivity
{
    private TabPageIndicator indicator;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indicator = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.viewPager);
        BasePagerAdapter adapter = new BasePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        setTabPagerIndicator();
    }

    private void setTabPagerIndicator()
    {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(Color.parseColor("#00bbcf"));// 设置分割线的颜色
//        indicator.setDividerPadding(CommonUtils.dip2px(this, 10));
        indicator.setIndicatorColor(Color.parseColor("#43A44b"));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor("#43A44b"));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#797979"));// 设置tab标题未被选中的颜色
//        indicator.setTextSize(CommonUtils.sp2px(this, 16));// 设置字体大小
    }

    class BasePagerAdapter extends FragmentPagerAdapter
    {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm)
        {
            super(fm);
            this.titles = getResources().getStringArray(R.array.expand_titles);
        }

        @Override
        public Fragment getItem(int position)
        {
            return FragmentFactory.createForExpand(position);
        }

        @Override
        public int getCount()
        {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return titles[position];
        }
    }
}
