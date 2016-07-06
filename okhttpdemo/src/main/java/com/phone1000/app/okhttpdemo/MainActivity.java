package com.phone1000.app.okhttpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnGet;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnGet = (Button) findViewById(R.id.get_btn);
        btnPost = (Button) findViewById(R.id.post_btn);
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.get_btn:
                break;
            case R.id.post_btn:
                intent.setClass(MainActivity.this,PostActivity.class);
                break;
        }
        startActivity(intent);
    }
}
