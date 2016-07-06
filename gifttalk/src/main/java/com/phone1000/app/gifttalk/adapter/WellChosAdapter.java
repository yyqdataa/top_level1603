package com.phone1000.app.gifttalk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.phone1000.app.gifttalk.bean.TableLayoutInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class WellChosAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentLists;
    private List<TableLayoutInfo.DataBean.ChannelsBean> titles;
    public WellChosAdapter(FragmentManager fm,List<Fragment> fragmentLists,List<TableLayoutInfo.DataBean.ChannelsBean> titles) {
        super(fm);
        this.fragmentLists = fragmentLists;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentLists!=null?fragmentLists.get(position):null;
    }

    @Override
    public int getCount() {
        return  fragmentLists!=null?fragmentLists.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = titles.get(position).getName();
        return name;
    }
}
