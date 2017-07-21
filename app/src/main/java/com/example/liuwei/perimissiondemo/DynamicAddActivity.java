package com.example.liuwei.perimissiondemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liuwei.perimissiondemo.fragment.FourFragment;
import com.example.liuwei.perimissiondemo.fragment.OneFragment;
import com.example.liuwei.perimissiondemo.fragment.ThreeFragment;
import com.example.liuwei.perimissiondemo.fragment.TwoFragment;


public class DynamicAddActivity extends Activity implements OneFragment.CallBackListener, View.OnClickListener{

    private TextView one_tv;
    private TextView two_tv;
    private TextView three_tv;
    private TextView four_tv;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_add);
        initView();
        manager = getFragmentManager();
        switchFragment(2);
    }



    public void showToast(){
        Toast.makeText(this, "Activity", Toast.LENGTH_LONG).show();
    }

    private void initView(){
        one_tv = (TextView) this.findViewById(R.id.one_tv);
        two_tv = (TextView) this.findViewById(R.id.two_tv);
        three_tv = (TextView) this.findViewById(R.id.three_tv);
        four_tv = (TextView) this.findViewById(R.id.four_tv);
        one_tv.setOnClickListener(this);
        two_tv.setOnClickListener(this);
        three_tv.setOnClickListener(this);
        four_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.one_tv:
                switchFragment(1);
                break;
            case R.id.two_tv:
                switchFragment(2);
                break;
            case R.id.three_tv:
                switchFragment(3);
                break;
            case R.id.four_tv:
                switchFragment(4);
                break;
        }
    }

    private void switchFragment(int index){
        transaction = manager.beginTransaction();
        hideFragment(transaction);
        switch(index){
            case 1:
                if(oneFragment == null){
                    oneFragment = new OneFragment();
                    transaction.add(R.id.container, oneFragment);
                } else {
                    transaction.show(oneFragment);
                }
//                oneFragment.showToast(DynamicAddActivity.this);
                break;
            case 2:
                if(twoFragment == null){
                    twoFragment = new TwoFragment();
                    transaction.add(R.id.container, twoFragment);
                } else {
                    transaction.show(twoFragment);
                }
                break;
            case 3:
                if(threeFragment == null){
                    threeFragment = new ThreeFragment();
                    transaction.add(R.id.container, threeFragment);
                } else {
                    transaction.show(threeFragment);
                }
                break;
            case 4:
                if(fourFragment == null){
                    fourFragment = new FourFragment();
                    transaction.add(R.id.container, fourFragment);
                } else {
                    transaction.show(fourFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if(oneFragment != null){
            transaction.hide(oneFragment);
        }
        if(twoFragment != null){
            transaction.hide(twoFragment);
        }
        if(threeFragment != null){
            transaction.hide(threeFragment);
        }
        if(fourFragment != null){
            transaction.hide(fourFragment);
        }
    }

    @Override
    public boolean callback(String text) {
        if("test".equals(text)){
            return true;
        }
        return false;
    }
}
