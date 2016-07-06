package com.phone1000.app.gifttalk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.phone1000.app.gifttalk.fragment.HomeFragment;
import com.phone1000.app.gifttalk.fragment.HotFragment;
import com.phone1000.app.gifttalk.fragment.MyFragment;
import com.phone1000.app.gifttalk.fragment.SelectFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private FragmentManager manager;
    private FragmentTransaction transaction;
    @BindView(R.id.rg_main)
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        initLiatener();
        transaction = manager.beginTransaction();
        transaction.add(R.id.frag_layout, HomeFragment.newInstance("",""));
        transaction.commit();
    }
    private Fragment homeFragment,hotFragment,selectFragment,myFragment;
    private void initLiatener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = manager.beginTransaction();
                switch (checkedId){
                    case R.id.rb_home:
                        if (homeFragment == null) {
                            homeFragment = HomeFragment.newInstance("", "");
                            transaction.add(homeFragment, "home");
                            if (hotFragment != null) {
                                transaction.hide(hotFragment);
                            }
                            if (selectFragment != null) {
                                transaction.hide(selectFragment);
                            }
                            if (myFragment != null) {
                                transaction.hide(myFragment);
                            }
                        } else {
                            transaction.show(homeFragment);
                            if (hotFragment != null) {
                                transaction.hide(hotFragment);
                            }
                            if (selectFragment != null) {
                                transaction.hide(selectFragment);
                            }
                            if (myFragment != null) {
                                transaction.hide(myFragment);
                            }
                        }
                        transaction.addToBackStack("home");
                        break;
                    case R.id.rb_good:
                        if (hotFragment == null) {
                            hotFragment = HotFragment.newInstance("", "");
                            transaction.add(hotFragment, "hot");
                            if (homeFragment != null) {
                                transaction.hide(homeFragment);
                            }
                            if (selectFragment != null) {
                                transaction.hide(selectFragment);
                            }
                            if (myFragment != null) {
                                transaction.hide(myFragment);
                            }
                        } else {
                            transaction.show(hotFragment);
                            if (homeFragment != null) {
                                transaction.hide(homeFragment);
                            }
                            if (selectFragment != null) {
                                transaction.hide(selectFragment);
                            }
                            if (myFragment != null) {
                                transaction.hide(myFragment);
                            }
                        }
                        transaction.addToBackStack("hot");
                        break;
                    case R.id.rb_select:
                        if (selectFragment == null) {
                            selectFragment = SelectFragment.newInstance("", "");
                            transaction.add(selectFragment, "select");
                            if (homeFragment != null) {
                                transaction.hide(homeFragment);
                            }
                            if (hotFragment != null) {
                                transaction.hide(hotFragment);
                            }
                            if (myFragment != null) {
                                transaction.hide(myFragment);
                            }
                        } else {
                            transaction.show(selectFragment);
                            if (homeFragment != null) {
                                transaction.hide(homeFragment);
                            }
                            if (hotFragment != null) {
                                transaction.hide(hotFragment);
                            }
                            if (myFragment != null) {
                                transaction.hide(myFragment);
                            }
                        }
                        transaction.addToBackStack("select");
                        break;
                    case R.id.rb_profile:
                        if (myFragment == null) {
                            myFragment = MyFragment.newInstance("", "");
                            transaction.add(myFragment, "my");
                            if (homeFragment != null) {
                                transaction.hide(homeFragment);
                            }
                            if (hotFragment != null) {
                                transaction.hide(hotFragment);
                            }
                            if (selectFragment != null) {
                                transaction.hide(selectFragment);
                            }
                        } else {
                            transaction.show(myFragment);
                            if (homeFragment != null) {
                                transaction.hide(homeFragment);
                            }
                            if (hotFragment != null) {
                                transaction.hide(hotFragment);
                            }
                            if (selectFragment != null) {
                                transaction.hide(selectFragment);
                            }
                        }
                        transaction.addToBackStack("my");
                        break;
                }
                transaction.commit();
            }
        });
    }
}
