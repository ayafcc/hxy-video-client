package cn.mahua.vod.ui.lanuch;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.ui.start.StartActivity;

public class LanuchActivity extends BaseActivity {


    @Override
    protected void initView() {
        super.initView();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide the title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (XXPermissions.isHasPermission(this, EXTERNAL_STORAGE)) {
           // phoneDevice = AppUtils.getDevice(this);
        } else {
            XXPermissions.with(this)
                    // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .constantRequest()
                    .permission(EXTERNAL_STORAGE)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                               // phoneDevice = AppUtils.getDevice(LoginActivity.this);
                            } else {
                               // phoneDevice = AppUtils.getDevice(LoginActivity.this);
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                Toast.makeText(LanuchActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LanuchActivity.this, "获取权限失败", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

        //加载启动图片
       // setContentView(R.layout.activity_lanuch);
        //后台处理耗时任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时任务，比如加载网络数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Integer time = 2000;    //设置等待时间，单位为毫秒
                        Handler handler = new Handler();
                        //当计时结束时，跳转至主界面
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent;

                                //如果已经登录

                                intent = new Intent(LanuchActivity.this, StartActivity.class);

                                startActivity(intent);
                                finish();
                            }
                        }, time);

                    }
                });
            }
        }).start();

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_lanuch;
    }

    //权限申请和检测模块
    //存储权限
    //手机状态码android.permission-group.PHONE
    public final String[] EXTERNAL_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE};

    //相机
    public final String[] CAMERA_STATE = new String[]{
            Manifest.permission.CAMERA};

}
