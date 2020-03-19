package com.example.easynews.fragments.manfragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easynews.MainActivity;
import com.example.easynews.R;
import com.example.easynews.utils.FragmentFactory;
import com.example.easynews.views.TabPageIndicator;

public class NewsFragment extends Fragment
{
    private TabPageIndicator indicator;
    private ViewPager viewPager;

    public NewsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        // Inflate the layout for this fragment
        indicator = view.findViewById(R.id.indicator);
        viewPager = view.findViewById(R.id.viewPager);
        BasePagerAdapter adapter = new BasePagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        setTabPagerIndicator();
        return view;
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
