package com.phone1000.app.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    private Button asynBtn;
    private Button synBtn;
    private TextView tvShow;
    private OkHttpClient okHttpClient = new OkHttpClient();
    public static final String URL = "http://www.1688wan.com/majax.action?method=searchGift";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initView();
    }

    private void initView() {
        asynBtn = (Button) findViewById(R.id.btn_asyn_post);
        synBtn = (Button) findViewById(R.id.btn_syn_post);
        tvShow = (TextView) findViewById(R.id.tv_show);
        asynBtn.setOnClickListener(this);
        synBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_asyn_post:
                asynRequest();
                break;
            case R.id.btn_syn_post:
                runOnThread();
                break;
        }
    }

    private void runOnThread(){
        new Thread(){
            @Override
            public void run() {
                final String result ;
                try {
                    result = synReqest().body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvShow.setText(result);
                    }
                });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Response synReqest() {
        //post数据
        FormBody formBody = new FormBody.Builder().add("key","热血").build();
        Request request = new Request.Builder().url(URL).post(formBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


    private void asynRequest() {
        //封装post数据
        FormBody formBody = new FormBody.Builder().add("key","热血").build();
        //请求
        Request request = new Request.Builder().url(URL).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvShow.setText(result);
                    }
                });
            }
        });
    }
}
