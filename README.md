# PermissionDemo
Android6.0 runtime permissions
效果图如下：
![image](https://github.com/lweiandroid/PermissionDemo/blob/master/gif/permission_demo.gif)  
一：工具类PermissionUtil.java中方法解析: 


1.requestPermissions(boolean isNeedShowRequestPermissionRationale, String[] permissions, int requestCode, PermissionCallback permissionCallback)方法： 


其中boolean isNeedShowRequestPermissionRationale：是否显示解释弹框，当用户拒绝权限之后再次申请权限时是否显示解释弹框。  

String[] permissions：请求权限数组。 

int requestCode：请求码。

PermissionCallback permissionCallback：检查权限的回调接口。

2.onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, PermissionCallback callback)方法 

需要在Activity/Fragment请求权限的回调方法中调用。

其中PermissionCallback请求权限最终回调结果。

    interface PermissionCallback{

        void permittedPermissions(); //允许申请的权限
        
        void rejectPermission(String[] rejectPermissions);//申请的权限中有未授权的权限，参数为未授权的权限集合
    }
    
3.工具类中实现了当用户点击“不再询问”后，再次申请权限弹框提示用户跳转到设置页面手动授权。（showGoToSettingDialog方法）

    public void intentSetting(String[] permissions){
        if(!shouldShowRequestPermissionRationale(permissions)){
            if(getFlag()){
                showGoToSettingDialog(permissions);
            }
            saveFlag(true);
        }
    }

4.工具类中使用一个数组存储未授权的权限，避免用户申请权限数组中已被授权的权限再次调用请求权限方法。

二.使用方法示例：

1.请求权限：

    private void requestPermissions(){ 
        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        PermissionUtil.getInstance(this).requestPermissions(true, permissions, PermissionUtil.code, new PermissionUtil.PermissionCallback(){
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
    
2.请求权限回调：

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.getInstance(this).onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionUtil.PermissionCallback() {
            @Override
            public void permittedPermissions() {
                Toast.makeText(MainActivity.this, "用户已授权", Toast.LENGTH_LONG).show();
            }

            @Override
            public void rejectPermission(String[] rejectPermissions) {
                Toast.makeText(MainActivity.this, "用户未授权", Toast.LENGTH_LONG).show();

            }
        });
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
三：关于更多Android6.0权限问题，查看[我的博客](http://blog.csdn.net/liuwei187/article/details/73505363)
    
