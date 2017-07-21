package com.example.liuwei.perimissiondemo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.liuwei.perimissiondemo.R;

/**
 * Created by liuwei on 2017/7/5.
 */

public class OneFragment extends Fragment{
    CallBackListener listener;
    private Button one_fragment_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_fragment, null);
        one_fragment_btn = (Button)view.findViewById(R.id.one_fragment_btn);
        one_fragment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean flag = listener.callback("test");//调用接口
                if(flag){
                    Toast.makeText(getActivity(), "成功", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    //回调接口
    public interface CallBackListener{
        boolean callback(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (CallBackListener) context;//实例化接口
        } catch (ClassCastException e){

        }
    }
}


