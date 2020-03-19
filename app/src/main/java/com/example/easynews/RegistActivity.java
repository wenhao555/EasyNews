package com.example.easynews;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easynews.model.User;
import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegistActivity extends AppCompatActivity
{
    private EditText account, password;
    private Button regist_commit;
    private Dialog dateDialog;
    private TextView user_bth;
    private RadioGroup user_group;
    private boolean isMan = false;
    private RadioButton user_man, user_woman;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        account = findViewById(R.id.account);
        user_bth = findViewById(R.id.user_bth);
        user_bth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy-MM-ss").format(new Date());
                showDateDialog(DateUtil.getDateForString(date));

            }
        });
        password = (EditText) findViewById(R.id.password);
        regist_commit = (Button) findViewById(R.id.regist_commit);
        regist_commit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = account.getText().toString();
                String pwd = password.getText().toString();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();


                if (name.equals("") || pwd.equals(""))
                {
                    Toast.makeText(RegistActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setName(name);
                user.setPassword(pwd);
                user.setUserRole(1);
                Gson gson = new Gson();
                String Json = gson.toJson(user);
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
                final Request request = new Request.Builder()
//                        .url(Urls.ADD_USER)
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
        });
    }

    private void showDateDialog(List<Integer> date)
    {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSelected(int[] dates)
            {

//                mTextView.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
//                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
                user_bth.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
            }

            @Override
            public void onCancel()
            {

            }
        })
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                String string = (String) msg.obj;
                if (string.equals("admin用户不可以注册"))
                {
                    Toast.makeText(RegistActivity.this, "admin用户不可以注册", Toast.LENGTH_SHORT).show();
                } else if (string.equals("非系统指定用户，不可以注册"))
                {
                    Toast.makeText(RegistActivity.this, "非系统指定用户，不可以注册", Toast.LENGTH_SHORT).show();
                } else if (string.equals("注册成功"))
                {
                    Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        }
    };
}

