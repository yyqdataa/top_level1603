package com.phone1000.app.gifttalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phone1000.app.gifttalk.R;
import com.phone1000.app.gifttalk.bean.HomeExpandInfo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ExpandAdapter extends BaseExpandableListAdapter {

    private List<HomeExpandInfo.DataBean.ItemsBean> data;

    private LayoutInflater inflater;
    //自布局Map集合
    private Map<String,List<HomeExpandInfo.DataBean.ItemsBean>> itemMap;
    //父布局集合
    private List<String> groupData;

    private Context context;

    public ExpandAdapter(Map<String,List<HomeExpandInfo.DataBean.ItemsBean>> itemMap,List<String> groupData, Context context) {
        this.itemMap = itemMap;
        this.groupData = groupData;
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public int getGroupCount() {

        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key = groupData.get(groupPosition);
        List<HomeExpandInfo.DataBean.ItemsBean> data = itemMap.get(key);
        return data!=null?data.size():0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = groupData.get(groupPosition);
        List<HomeExpandInfo.DataBean.ItemsBean> data = itemMap.get(key);
        return data.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.group_item,parent,false);
            groupViewHolder = new GroupViewHolder(convertView);
        }else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        if (groupData.size()!=0){
        String s = groupData.get(groupPosition);
        groupViewHolder.textView.setText(s);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.child_item,parent,false);
            childViewHolder = new ChildViewHolder(convertView);
        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        if (groupData.isEmpty()){
            return convertView;
        }
        List<HomeExpandInfo.DataBean.ItemsBean> itemBeen = itemMap.get(groupData.get(groupPosition));
        HomeExpandInfo.DataBean.ItemsBean itemsBean = itemBeen.get(childPosition);
        Picasso.with(context).load(itemsBean.getCover_image_url()).into(childViewHolder.imageView);
        childViewHolder.textViewCount.setText(""+itemsBean.getLikes_count());
        childViewHolder.textView.setText(itemsBean.getTitle());
        if (itemsBean.getContent_type()==1){
            childViewHolder.view.setVisibility(View.VISIBLE);
        }else {
            childViewHolder.view.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



    static class GroupViewHolder {
        @BindView(R.id.group_time)
        TextView textView;
        public GroupViewHolder(View view){
            ButterKnife.bind(this,view);
            view.setTag(this);
        }
    }

    static class ChildViewHolder{
        @BindView(R.id.child_tv_count)
        TextView textViewCount;
        @BindView(R.id.child_tv)
        TextView textView;
        @BindView(R.id.child_iv)
        ImageView imageView;
        @BindView(R.id.view_fresh)
        View view;

        public ChildViewHolder(View view){
            ButterKnife.bind(this,view);
            view.setTag(this);
        }

    }
}
