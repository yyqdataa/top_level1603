package com.phone1000.app.gifttalk.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.phone1000.app.gifttalk.HeadItemActivity;
import com.phone1000.app.gifttalk.R;
import com.phone1000.app.gifttalk.adapter.ExpandAdapter;
import com.phone1000.app.gifttalk.adapter.HeadRVAdapter;
import com.phone1000.app.gifttalk.bean.BannerInfo;
import com.phone1000.app.gifttalk.bean.HomeExpandInfo;
import com.phone1000.app.gifttalk.constant.URLConstant;
import com.phone1000.app.okhttplibrary.IOKCallBack;
import com.phone1000.app.okhttplibrary.OkHttpTool;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WellChosenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WellChosenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WellChosenFragment extends Fragment implements OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View view;
    private HeadViewHolde headViewHolde;
    private HeadRVAdapter headRVAdapter;
    private HomeExpandInfo homeExpandInfo;
    private int page = 0;



    public WellChosenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WellChosenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WellChosenFragment newInstance(String param1, String param2) {
        WellChosenFragment fragment = new WellChosenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @BindView(R.id.expanded_lv)
    public PullToRefreshExpandableListView pullToRefreshExpandableListView;
    public ExpandableListView expandableListView;
    private ExpandAdapter adapter;
    private List<HomeExpandInfo.DataBean.ItemsBean> data = new ArrayList<>();
    private Map<String, List<HomeExpandInfo.DataBean.ItemsBean>> itemMap = new HashMap<>();
    //父布局集合
    private List<String> groupData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_well_chosen, container, false);
        ButterKnife.bind(this, view);
        //设置下拉时间
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        String lastTime = format.format(time);
        pullToRefreshExpandableListView.setLastUpdatedLabel("上次刷新："+lastTime);
        //设置下拉模式
        pullToRefreshExpandableListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        expandableListView = pullToRefreshExpandableListView.getRefreshableView();
        //1.准备数据源
        setupData();
        //2.准备适配器
        adapter = new ExpandAdapter(itemMap, groupData, getContext());
        //3.绑定适配器
        expandableListView.setAdapter(adapter);
        //初始化头部
        setupHeadView();
        initListener();
        return view;
    }

    private void setupData() {
        if (data!=null&&!data.isEmpty()){
            return;
        }
        setupData(URLConstant.WELL_CHOSE_URL);
    }

    private void initListener() {
        pullToRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                page=0;
                data.clear();
                groupData.clear();
                itemMap.clear();
                setupData(URLConstant.WELL_CHOSE_URL);
                setupCBImg();
                setupRecyleView(headViewHolde);
                pullToRefreshExpandableListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

                pullToRefreshExpandableListView.onRefreshComplete();
            }
        });
        pullToRefreshExpandableListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (homeExpandInfo!=null){
                    String next_url = homeExpandInfo.getData().getPaging().getNext_url();
                    data.clear();
                    setupData(next_url);
                }
            }
        });

    }

    private void setupHeadView() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_view,null);
        headViewHolde = new HeadViewHolde(headView);
        setupCBImg();
        setupRecyleView(headViewHolde);
        expandableListView.addHeaderView(headView);
    }

    //横向ListView
    private void setupRecyleView(HeadViewHolde headViewHolde) {
        //View headRVView = LayoutInflater.from(getContext()).inflate(R.layout.recyleview_item,null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        headViewHolde.recyclerView.setLayoutManager(linearLayoutManager);
        headRVAdapter = new HeadRVAdapter(getContext());
        headViewHolde.recyclerView.setAdapter(headRVAdapter);

    }
    private List<BannerInfo.DataBean.BannersBean> imageData = new ArrayList<>();

    //ConvenientBanner数据获取
    private void setupCBImg() {
        if (imageData!=null&&!imageData.isEmpty()){
            setupBanner(headViewHolde);
            return;
        }
        OkHttpTool.newInstance().start(URLConstant.WELL_CHOSE_BANNER).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                BannerInfo bannerInfo = gson.fromJson(result,BannerInfo.class);
                imageData.addAll(bannerInfo.getData().getBanners());
                setupBanner(headViewHolde);
                headViewHolde.convenientBanner.getViewPager().getAdapter().notifyDataSetChanged();
            }
        });
        setupBanner(headViewHolde);

    }

    @Override
    public void onStart() {
        super.onStart();
        headViewHolde.convenientBanner.startTurning(2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        headViewHolde.convenientBanner.stopTurning();
    }

    //设置ConvenientBanner
    private void setupBanner(HeadViewHolde headViewHolde) {
        headViewHolde.convenientBanner.setPageIndicator(new int[]{R.drawable.btn_check_disabled_nightmode,R.drawable.btn_check_normal}).setPages(new CBViewHolderCreator<HeadBannerHolder>() {
            @Override
            public HeadBannerHolder createHolder() {
                return new HeadBannerHolder();
            }
        },imageData)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        headViewHolde.convenientBanner.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        int target_id = imageData.get(position).getTarget_id();
        String title = imageData.get(position).getTarget().getTitle();
        Intent intent = new Intent(getActivity(), HeadItemActivity.class);
        intent.putExtra("id",target_id);
        intent.putExtra("title",title);
        startActivity(intent);
    }


    static class HeadBannerHolder implements Holder<BannerInfo.DataBean.BannersBean>{

        ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerInfo.DataBean.BannersBean data) {
            Picasso.with(context).load(data.getImage_url()).into(imageView);
        }
    }

    //头部ViewHolder
    static class HeadViewHolde{
        @BindView(R.id.well_chosen_cb)
        ConvenientBanner convenientBanner;
        @BindView(R.id.well_chosen_rc)
        RecyclerView recyclerView;

        public HeadViewHolde(View view){
            ButterKnife.bind(this,view);
        }
    }
    //加载数据
    private void setupData(String url) {

        OkHttpTool.newInstance().start(url).callback(new IOKCallBack() {
            @Override
            public void success(String result) {

                Gson gson = new Gson();
                homeExpandInfo = gson.fromJson(result, HomeExpandInfo.class);
                data.addAll(homeExpandInfo.getData().getItems());
                updata(data);
                expandableListView.setSelection(page*20);
                adapter.notifyDataSetChanged();
                //设置ExpandableListView点击不收缩
                expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        return true;
                    }
                });
                //默认所有的Group全部展开
                for (int i = 0; i < groupData.size(); i++) {
                    expandableListView.expandGroup(i);
                }
            }
        });
    }
    //处理数据，进行分类
    private void updata(List<HomeExpandInfo.DataBean.ItemsBean> data) {
//            String time = "20"+s.substring(0,2)+"-"+s.substring(2,4)+"-"+s.substring(4,6);
        if (data.size() != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E");
            long oneTime = data.get(0).getCreated_at();
            String time = sdf.format(new Date(oneTime * 1000));
            if (groupData.isEmpty()&&itemMap.isEmpty()){
                //初始化父数据第一项
                groupData.add(time);
                //初始化子数据第一项
                itemMap.put(time,new ArrayList<HomeExpandInfo.DataBean.ItemsBean>());
                itemMap.get(time).add(data.get(0));
            }

            boolean same = false;
            for (int i = 1; i < data.size(); i++) {
                long oneTime2 = data.get(i).getCreated_at();
                String time2 = sdf.format(new Date(oneTime2 * 1000));
                for (String t:groupData){
                    if (time2.equals(t)){
                        itemMap.get(time2).add(data.get(i));
                        same = true;
                    }
                }
                //如果时间相同就不执行
                if (!same){
                    groupData.add(time2);
                    itemMap.put(time2,new ArrayList<HomeExpandInfo.DataBean.ItemsBean>());
                    itemMap.get(time2).add(data.get(i));
                }
                same = false;
            }

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
