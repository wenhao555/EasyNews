package com.example.easynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.easynews.model.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class NewssActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newss);
    }

    private void request()
    {

    }

    private Handler handler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(@NonNull Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    String string2 = (String) msg.obj;
                    if (string2.equals("推荐成功"))
                    {
                        Toast.makeText(NewssActivity.this, "推荐成功", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(NewssActivity.this, "推荐失败", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
            return false;
        }
    });
}
