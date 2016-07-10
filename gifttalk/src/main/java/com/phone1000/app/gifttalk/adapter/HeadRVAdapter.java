package com.phone1000.app.gifttalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.phone1000.app.gifttalk.HeadItemActivity;
import com.phone1000.app.gifttalk.R;
import com.phone1000.app.gifttalk.bean.CBImgInfo;
import com.phone1000.app.gifttalk.constant.URLConstant;
import com.phone1000.app.okhttplibrary.IOKCallBack;
import com.phone1000.app.okhttplibrary.OkHttpTool;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 横向ListView适配器
 * 泛型是HeadRVHolder类继承RecyclerView.ViewHolder
 * Created by Administrator on 2016/6/30.
 */
public class HeadRVAdapter  extends RecyclerView.Adapter<HeadRVHolder> {

    private Context context;
    private String id;

    public HeadRVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HeadRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyleview_item,parent,false);
        return new HeadRVHolder(view);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    String[] titles = {"","最佳礼物大赏","进口礼物市集","次日礼物馆","DIY礼物指南","秀恩爱专场","创意礼物专题",""};

    //更新item布局
    @Override
    public void onBindViewHolder(final HeadRVHolder holder, final int position) {


        OkHttpTool.newInstance().start(URLConstant.HORIZON_LIST_VIEW).callback(new IOKCallBack() {
            private List<CBImgInfo.DataBean.SecondaryBannersBean> secondary_banners;

            @Override
            public void success(String result) {
                CBImgInfo cbImgInfo ;
                Gson gson = new Gson();
                cbImgInfo = gson.fromJson(result,CBImgInfo.class);
                secondary_banners = cbImgInfo.getData().getSecondary_banners();
                String image_url = secondary_banners.get(position).getImage_url();
                Picasso.with(context).load(image_url).into(holder.imageView);
                String target_url = secondary_banners.get(position).getTarget_url();
                initListener(holder,position,target_url);
            }
        });


    }

    private void initListener(HeadRVHolder holder, final int position, final String target_url) {
        if (position > 0 && position < 7) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HeadItemActivity.class);
                    String[] strings = target_url.split("=");
                    id = strings[2];
                    String title = titles[position];
                    intent.putExtra("id",Integer.parseInt(id));
                    intent.putExtra("title",title);
                    context.startActivity(intent);
                }
            });

        }
    }


}

class HeadRVHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.recyle_item_iv)
    ImageView imageView;
    public HeadRVHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}