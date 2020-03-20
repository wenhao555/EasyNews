package com.example.easynews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.easynews.utils.PrefUtils;
import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;

import java.io.IOException;
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
    private ImageView user_img;

    /**
     * 检查是否有对应权限
     *
     * @param activity   上下文
     * @param permission 要检查的权限
     * @return 结果标识
     */
    public int verifyPermissions(Activity activity, java.lang.String permission)
    {
        int Permission = ActivityCompat.checkSelfPermission(activity, permission);
        if (Permission == PackageManager.PERMISSION_GRANTED)
        {
            return 1;
        } else
        {
            return 0;
        }
    }

    private void toPicture()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        title = findViewById(R.id.title);
        user_img = findViewById(R.id.user_img);
        title.setOnClickListener(this);
        user_bth = findViewById(R.id.user_bth);
        user_woman = findViewById(R.id.user_woman);
        user_man = findViewById(R.id.user_man);
        user_bth.setText(PrefUtils.getString(this, "birth", ""));
        user_bth.setOnClickListener(this);
        user_name = findViewById(R.id.user_name);
        user_name.setText(PrefUtils.getString(this, "name", ""));
        user_group = findViewById(R.id.user_group);

        if (PrefUtils.getString(this, "sex", "").equals("男"))
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

        user_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toPicture();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 100:   //相册返回的数据（相册的返回码）
                Uri uri01 = data.getData();
                try
                {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri01));
                    user_img.setImageBitmap(bitmap);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                break;
        }
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
