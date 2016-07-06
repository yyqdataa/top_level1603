package com.phone1000.app.gifttalk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.phone1000.app.gifttalk.R;
import com.phone1000.app.gifttalk.bean.CBImgInfo;
import com.phone1000.app.gifttalk.constant.URLConstant;
import com.phone1000.app.okhttplibrary.IOKCallBack;
import com.phone1000.app.okhttplibrary.OkHttpTool;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 横向ListView适配器
 * 泛型是HeadRVHolder类继承RecyclerView.ViewHolder
 * Created by Administrator on 2016/6/30.
 */
public class HeadRVAdapter  extends RecyclerView.Adapter<HeadRVHolder> {

    private Context context;

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

    //更新item布局
    @Override
    public void onBindViewHolder(final HeadRVHolder holder, final int position) {


        OkHttpTool.newInstance().start(URLConstant.HORIZON_LIST_VIEW).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                CBImgInfo cbImgInfo ;
                Gson gson = new Gson();
                cbImgInfo = gson.fromJson(result,CBImgInfo.class);
                String image_url = cbImgInfo.getData().getSecondary_banners().get(position).getImage_url();
                Picasso.with(context).load(image_url).into(holder.imageView);
            }
        });
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