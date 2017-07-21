package com.example.liuwei.perimissiondemo.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.liuwei.perimissiondemo.PermissionUtil;
import com.example.liuwei.perimissiondemo.R;

/**
 * Created by liuwei on 2017/7/5.
 */

public class TwoFragment extends Fragment{

    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two_fragment, null);
        btn = (Button) view.findViewById(R.id.onclick);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        return view;
    }

    private void requestPermissions(){
        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        PermissionUtil.getInstance(getActivity(), this).requestPermissions(true, permissions, PermissionUtil.code, new PermissionUtil.PermissionCallback(){
            @Override
            public void permittedPermissions() {
                Toast.makeText(getActivity(), "用户已授权", Toast.LENGTH_LONG).show();
            }

            @Override
            public void rejectPermission(String[] rejectPermissions) {
                Toast.makeText(getActivity(), "用户未授权", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.getInstance(getActivity(), this).onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionUtil.PermissionCallback() {
            @Override
            public void permittedPermissions() {
                Toast.makeText(getActivity(), "用户已授权", Toast.LENGTH_LONG).show();
            }

            @Override
            public void rejectPermission(String[] rejectPermissions) {
                Log.d("xxx", "rejectPermission");
                Toast.makeText(getActivity(), "用户未授权", Toast.LENGTH_LONG).show();

            }
        });
    }
}
