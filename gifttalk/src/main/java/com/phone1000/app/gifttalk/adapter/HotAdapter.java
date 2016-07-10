package com.phone1000.app.gifttalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phone1000.app.gifttalk.R;
import com.phone1000.app.gifttalk.bean.HotInfo;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/8.
 */
public class HotAdapter extends AbsAdapter {

    private List<HotInfo.DataBean.ItemsBean> data;
    private Context context;
    private LayoutInflater inflater;
    public HotAdapter(Context context, List data) {
        super(data);
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.hot_gv_item,parent,false);
            viewHolder = new ViewHolder(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BigDecimal bd = new BigDecimal(data.get(position).getData().getFavorites_count()/1000.0);
        bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);
        viewHolder.textViewCounts.setText(bd.toString()+"k");
        viewHolder.textViewPrice.setText(data.get(position).getData().getPrice());
        viewHolder.textViewTitle.setText(data.get(position).getData().getName());
        Picasso.with(context).load(data.get(position).getData().getCover_image_url()).into(viewHolder.imageView);
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.hot_gv_item_iv)
        ImageView imageView;
        @BindView(R.id.hot_gv_item_tv)
        TextView textViewTitle;
        @BindView(R.id.hot_gv_item_price)
        TextView textViewPrice;
        @BindView(R.id.hot_gv_item_likes)
        TextView textViewCounts;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
            view.setTag(this);
        }
    }
}
