package com.example.easynews.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easynews.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

/**
 * A simple {@link Fragment} subclass.
 */
public class BenDiFragment extends Fragment
{

    public BenDiFragment()
    {
        // Required empty public constructor
    }

    private RecyclerView recyvle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tuijian, container, false);
        recyvle = view.findViewById(R.id.recyvle);
        return view;
    }

    private Handler handler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(@NonNull Message msg)
        {
            switch (msg.what)
            {

            }
            return false;
        }
    });

    private void request()
    {
//        byte[] decodedString = Base64.decode(productItem.getPicture().substring(productItem.getPicture().indexOf(",") + 1), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
