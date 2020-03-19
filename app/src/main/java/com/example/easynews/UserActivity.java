package com.example.easynews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.easynews.utils.PrefUtils;
import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView title;
    private TextView user_bth;
    private EditText user_name;
    private RadioGroup user_group;
    private Dialog dateDialog;
    private boolean isMan = false;
    private RadioButton user_man, user_woman;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        title = findViewById(R.id.title);
        title.setOnClickListener(this);
        user_bth = findViewById(R.id.user_bth);
        user_woman = findViewById(R.id.user_woman);
        user_man = findViewById(R.id.user_man);
        user_bth.setText(PrefUtils.getString(this, "bth", ""));
        user_bth.setOnClickListener(this);
        user_name = findViewById(R.id.user_name);
        user_bth.setText(PrefUtils.getString(this, "name", ""));
        user_group = findViewById(R.id.user_group);

        if (PrefUtils.getBoolean(this, "sex", true))
        {
            user_man.setChecked(true);
        } else
        {
            user_woman.setChecked(true);
        }

        user_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.user_man:
                        isMan = true;
                        break;
                    case R.id.user_woman:
                        isMan = false;
                        break;
                }
            }
        });
    }

    private void request()
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.title:
                finish();
                break;
            case R.id.user_bth:
                @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy-MM-ss").format(new Date());
                showDateDialog(DateUtil.getDateForString(date));
                break;
            case R.id.user_name:
                request();
                break;
        }
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
}
