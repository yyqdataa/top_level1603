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

public class MainActivity extends AppCompatActivity {


    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioGroup radioGroup;
    private Fragment homeFragment,hotFragment,selectFragment,myFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.rg_main);
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        homeFragment = HomeFragment.newInstance("","");
        transaction.add(R.id.frag_layout,homeFragment);
        transaction.commit();
        initLiatener();
    }
    private void initLiatener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                transaction = manager.beginTransaction();
                switch (checkedId){
                    case R.id.rb_home:
                        if (homeFragment!=null){
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
                        transaction.addToBackStack("");
                        break;
                    case R.id.rb_good:
                        if (hotFragment == null) {
                            hotFragment = HotFragment.newInstance("", "");
                            transaction.add(R.id.frag_layout, hotFragment);
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
                        transaction.addToBackStack("");
                        break;
                    case R.id.rb_select:
                        if (selectFragment == null) {
                            selectFragment = SelectFragment.newInstance("", "");
                            transaction.add(R.id.frag_layout, selectFragment);
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
                        transaction.addToBackStack("");
                        break;
                    case R.id.rb_profile:
                        if (myFragment == null) {
                            myFragment = MyFragment.newInstance("", "");
                            transaction.add(R.id.frag_layout, myFragment);
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
                        transaction.addToBackStack("");
                        break;
                }
                transaction.commit();
            }
        });
    }
}
