package com.example.easynews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdatePwdActivity extends AppCompatActivity
{
    private EditText old_pwd, new_pwd, new_pwd2;
    private Button mine_exit;
    private ImageView title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        old_pwd = findViewById(R.id.old_pwd);
        new_pwd = findViewById(R.id.new_pwd);
        new_pwd2 = findViewById(R.id.new_pwd2);
        mine_exit = findViewById(R.id.mine_exit);
        mine_exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                request();

            }
        });
    }

    private void request()
    {

    }
}
