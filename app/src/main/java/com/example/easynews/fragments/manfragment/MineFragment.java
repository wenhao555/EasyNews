package com.example.easynews.fragments.manfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easynews.R;
import com.example.easynews.UpdatePwdActivity;
import com.example.easynews.UserActivity;
import com.example.easynews.views.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment
{

    public MineFragment()
    {
        // Required empty public constructor
    }

    private CircleImageView mine_img;
    private TextView mine_name;
    private LinearLayout ming_data, ming_uppwd;

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        mine_img = view.findViewById(R.id.mine_img);
        mine_name = view.findViewById(R.id.mine_name);
        ming_data = view.findViewById(R.id.ming_data);
        ming_uppwd = view.findViewById(R.id.ming_uppwd);
        ming_data.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), UserActivity.class));
            }
        });
        ming_uppwd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), UpdatePwdActivity.class));
            }
        });
        return view;
    }

}
