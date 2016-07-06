package com.phone1000.app.gifttalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phone1000.app.gifttalk.R;
import com.phone1000.app.gifttalk.bean.HomeExpandInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/4.
 */
public class OtherFragmentAdapter extends AbsAdapter {

    private List<HomeExpandInfo.DataBean.ItemsBean> data;
    private LayoutInflater inflater;
    private Context context;
    public OtherFragmentAdapter(Context context,List<HomeExpandInfo.DataBean.ItemsBean> data) {
        super(data);
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.child_item,parent,false);
            viewHolder = new ViewHolder(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String imgUrl = data.get(position).getCover_image_url();
        Picasso.with(context).load(imgUrl).into(viewHolder.imageView);
        viewHolder.textViewCount.setText(""+data.get(position).getLikes_count());
        viewHolder.textViewTitle.setText(data.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.child_iv)
        ImageView imageView;
        @BindView(R.id.child_tv_count)
        TextView textViewCount;
        @BindView(R.id.child_tv)
        TextView textViewTitle;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
            view.setTag(this);
        }
    }
}
