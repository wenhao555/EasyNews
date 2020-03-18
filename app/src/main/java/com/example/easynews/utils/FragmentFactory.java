package com.example.easynews.utils;


import com.example.easynews.fragments.BenDiFragment;
import com.example.easynews.fragments.GuoJiFragment;
import com.example.easynews.fragments.JunShiFragment;
import com.example.easynews.fragments.KeJiFragment;
import com.example.easynews.fragments.ShengHuoFragment;
import com.example.easynews.fragments.TuijianFragment;

import androidx.fragment.app.Fragment;

/**
 * Created by shan_yao on 2016/6/17.
 */
public class FragmentFactory
{

    public static Fragment createForExpand(int position)
    {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new TuijianFragment();
                break;
            case 1:
                fragment = new BenDiFragment();
                break;
            case 2:
                fragment = new GuoJiFragment();
                break;
            case 3:
                fragment = new JunShiFragment();
                break;
            case 4:
                fragment = new KeJiFragment();
                break;
            case 5:
                fragment = new ShengHuoFragment();
                break;
        }
        return fragment;
    }
}
