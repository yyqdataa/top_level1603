package com.phone1000.app.gifttalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.phone1000.app.gifttalk.adapter.BannerItemAdapter;
import com.phone1000.app.gifttalk.bean.BannerItemInfo;
import com.phone1000.app.okhttplibrary.IOKCallBack;
import com.phone1000.app.okhttplibrary.OkHttpTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadItemActivity extends AppCompatActivity implements PullToRefreshBase.OnRefreshListener2 {

    @BindView(R.id.head_item_ptrlv)
    PullToRefreshListView pullToRefreshListView;
    @BindView(R.id.gift_title)
    TextView textView;
    private String str1 = "http://api.liwushuo.com/v2/collections/";
    private String str2 = "/posts?limit=20&offset=0";
    private List<BannerItemInfo.DataBean.PostsBean> data = new ArrayList<>();
    private String url = "";
    private BannerItemAdapter adapter;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_item);
        ButterKnife.bind(this);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        url = str1+id+str2;
        String title = intent.getStringExtra("title");
        textView.setText(title);
        adapter = new BannerItemAdapter(this,data);
        setupData();
        //绑定适配器
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(this);
    }

    private void setupData() {
        OkHttpTool.newInstance().start(url).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                BannerItemInfo bannerItemInfo = gson.fromJson(result,BannerItemInfo.class);
                data.addAll(bannerItemInfo.getData().getPosts());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 0;
        data.clear();
        setupData();
        pullToRefreshListView.getRefreshableView().setSelection(page*20);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
}
