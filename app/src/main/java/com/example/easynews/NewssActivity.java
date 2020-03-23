package com.example.easynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easynews.model.News;
import com.example.easynews.model.User;
import com.example.easynews.net.Urls;
import com.example.easynews.utils.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewssActivity extends AppCompatActivity
{
    private TextView news_title, news_content, news_time;
    private ImageView news_img;
    private Button news_recommend;
    private String news, titles, imgs, time, type;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newss);
        news_title = findViewById(R.id.news_title);
        news_content = findViewById(R.id.news_content);
        news_recommend = findViewById(R.id.news_recommend);
        news_time = findViewById(R.id.news_time);
        news_img = findViewById(R.id.news_img);
        titles = getIntent().getStringExtra("title");
        news = getIntent().getStringExtra("news");
        type = getIntent().getStringExtra("type");
        imgs = getIntent().getStringExtra("img");
        time = getIntent().getStringExtra("time");
        if (type.equals("推荐"))
        {
            news_recommend.setVisibility(View.INVISIBLE);
        }
        news_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(NewssActivity.this, ImgActivity.class).putExtra("img", imgs));
            }
        });
        news_recommend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                request();
            }
        });


        id = getIntent().getIntExtra("id", 0);
        news_title.setText(titles);
        news_content.setText(news);
        news_time.setText(time);
        byte[] decodedString = Base64.decode(imgs
                .substring(imgs
                        .indexOf(",") + 1), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        news_img.setImageBitmap(decodedByte);
    }

    private void request()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final News newss = new News();
        newss.setId(id);
        newss.setTitle(titles);
        newss.setType(type);
        newss.setContent(news);
        newss.setDate(time);
        newss.setImage(imgs);
        Gson gson = new Gson();
        String Json = gson.toJson(newss);
        Log.e("测试json", Json);
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()
                .url(Urls.setNewsToRecommend)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = response.body().string();
                mHandler.sendMessage(msg);
            }
        });
    }

    private Handler mHandler = new Handler(new Handler.Callback()
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
                    } else if (string2.equals("已存在"))
                    {
                        Toast.makeText(NewssActivity.this, "已推荐", Toast.LENGTH_SHORT).show();
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
