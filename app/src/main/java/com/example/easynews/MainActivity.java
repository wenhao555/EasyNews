package com.example.easynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.easynews.fragments.manfragment.MineFragment;
import com.example.easynews.fragments.manfragment.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigation;
    private Fragment[] fragments;
    private NewsFragment newsFragment;
    private MineFragment mineFragment;
    private int lastFragments = 0;

    private void init()
    {
        newsFragment = new NewsFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[]{newsFragment, mineFragment};
        lastFragments = 0;

        getSupportFragmentManager().beginTransaction().replace(R.id.mframeLayout, newsFragment).show(newsFragment).commit();
        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnNavigationItemSelectedListener(changeFragment);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.home:
                    if (lastFragments != 0)
                    {
                        switchFragment(lastFragments, 0);
                        lastFragments = 0;
                    }
                    return true;
                case R.id.mine:
                    if (lastFragments != 1)
                    {
                        switchFragment(lastFragments, 1);
                        lastFragments = 1;
                    }

                    return true;
            }

            return false;
        }
    };

    private void switchFragment(int lastFragments, int index)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastFragments]);
        if (!fragments[index].isAdded())
        {
            transaction.add(R.id.mframeLayout, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

}
