package com.example.liuwei.perimissiondemo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    private static int REQUEST_PERMISSION_CALL_PHONE = 100;
    private TextView request_permission_tv;
    private TextView request_permissions_tv;
    private TextView fragment_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        request_permission_tv = (TextView )findViewById(R.id.request_permission);
        request_permission_tv.setOnClickListener(this);
        request_permissions_tv = (TextView )findViewById(R.id.request_permissions);
        request_permissions_tv.setOnClickListener(this);
        fragment_request = (TextView)findViewById(R.id.fragment_request);
        fragment_request.setOnClickListener(this);
    }

    /**
     * 使用封装后的工具类PermissionUtil请求多个权限
     */
    private void requestPermissions(){
        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        PermissionUtil.getInstance(this, null).requestPermissions(true, permissions, PermissionUtil.code, new PermissionUtil.PermissionCallback(){
            @Override
            public void permittedPermissions() {
                Toast.makeText(MainActivity.this, "用户已授权", Toast.LENGTH_LONG).show();
                callPhone();
            }

            @Override
            public void rejectPermission(String[] rejectPermissions) {
                Toast.makeText(MainActivity.this, "用户未授权", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 使用未封装的原生api申请电话权限
     */
    private void requestPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission
                .CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {//用户未授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                //给用户一个申请权限的解释
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("说明")
                        .setMessage("需要使用电话权限，拨打电话")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //继续请求权限
                                ActivityCompat.requestPermissions(MainActivity.this, new
                                        String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL_PHONE);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "用户未授权", Toast.LENGTH_LONG).show();
                                return;
                            }
                        })
                        .create()
                        .show();
            } else {
                //请求权限
                ActivityCompat.requestPermissions(this, new
                                String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_PERMISSION_CALL_PHONE);
            }

        } else {//用户已经授权
            callPhone();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意
                callPhone();
            } else {
                //权限被用户拒绝
                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
                    //用户点击了“不在询问”弹框，则再次申请权限时跳到设置页提醒用户手动设置权限
                    PermissionUtil.getInstance(this, null).intentSetting(new String[]{Manifest.permission.CALL_PHONE});
                } else {
                    Toast.makeText(MainActivity.this, "用户未授权", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            PermissionUtil.getInstance(this, null).onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionUtil.PermissionCallback() {
                @Override
                public void permittedPermissions() {
                    Toast.makeText(MainActivity.this, "用户已授权", Toast.LENGTH_LONG).show();
                }

                @Override
                public void rejectPermission(String[] rejectPermissions) {
                    Toast.makeText(MainActivity.this, "用户未授权", Toast.LENGTH_LONG).show();

                }
            });
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 拨打电话
     */
    private void callPhone(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.request_permission:
                //点击单个权限，使用的是未封装的原生api
                requestPermission();
                break;
            case R.id.request_permissions:
                //点击多个权限，使用的是封装后的工具类PermissionUtil.java
                requestPermissions();
                break;
            case R.id.fragment_request:
                Intent intent = new Intent(this, DynamicAddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
