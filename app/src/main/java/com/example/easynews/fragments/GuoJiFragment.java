package com.example.easynews.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.easynews.NewssActivity;
import com.example.easynews.R;
import com.example.easynews.adapter.BaseRecyclerAdapter;
import com.example.easynews.listeners.OnItemClickListener;
import com.example.easynews.model.News;
import com.example.easynews.net.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuoJiFragment extends Fragment
{

    public GuoJiFragment()
    {
        // Required empty public constructor
    }

    private RecyclerView recyvle;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tuijian, container, false);
        requestData();
        recyvle = view.findViewById(R.id.recyvle);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                //这里获取数据的逻辑
                requestData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyvle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        initData();
        recyvle.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(@NonNull int position)
            {
                final News news = newsList.get(position);
                startActivity(new Intent(getActivity(), NewssActivity.class).putExtra("title", news.getTitle())
                        .putExtra("img", news.getImage())
                        .putExtra("news", news.getContent())
                        .putExtra("time", news.getDate()).putExtra("type", "国际")
                        .putExtra("id", news.getId()));
            }
        });
        return view;
    }

    private BaseRecyclerAdapter adapter;
    private List<News> newsList = new ArrayList<>();
    protected MaterialDialog loadingDialog;


    private void initData()
    {
        adapter = new BaseRecyclerAdapter()
        {
            @Override
            protected void onBindView(@NonNull BaseViewHolder holder, @NonNull final int position)
            {
                final News news = newsList.get(position);
                ImageView item_img = holder.getView(R.id.item_img);
                TextView item_title = holder.getView(R.id.item_title);
                TextView item_new = holder.getView(R.id.item_new);
                TextView item_date = holder.getView(R.id.item_date);
                item_title.setText(news.getTitle());
                item_new.setText(news.getContent());
                item_date.setText(news.getDate());
                if (!news.getImage().equals(""))
                {
                    byte[] decodedString = Base64.decode(news.getImage().substring(news.getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    item_img.setImageBitmap(decodedByte);
                } else
                {
                    item_img.setVisibility(View.GONE);
                }
            }

            @Override
            protected int getLayoutResId(int position)
            {
                return R.layout.iten_news;
            }

            @Override
            public int getItemCount()
            {
                return newsList.size();
            }
        };
    }

    private Handler mHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(@NonNull Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    String string = (String) msg.obj;
                    Gson gson = new Gson();
                    newsList = gson.fromJson(string, new TypeToken<List<News>>()
                    {
                    }.getType());
                    break;

            }
            return false;
        }
    });

    private void requestData()
    {

        showLoadingDialog();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final News news = new News();
        news.setType("国际");
        Gson gson = new Gson();
        String Json = gson.toJson(news);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()
                .url(Urls.getNewsByType)
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
                dismissLoadingDialog();
            }
        });
    }

    protected void showLoadingDialog()
    {
        loadingDialog = new MaterialDialog.Builder(getActivity()).content(R.string.loading).progress(true, 0).build();
        if (loadingDialog != null && !loadingDialog.isShowing())
        {
            loadingDialog.show();
        }
    }

    /**
     * 隐藏loading对话框
     */
    protected void dismissLoadingDialog()
    {
        if (loadingDialog != null && loadingDialog.isShowing())
        {
            loadingDialog.dismiss();
        }
    }
}
