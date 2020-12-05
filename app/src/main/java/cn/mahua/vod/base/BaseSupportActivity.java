package cn.mahua.vod.base;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.mahua.vod.R;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseSupportActivity extends SupportActivity {

    private Unbinder unbinder;

    @LayoutRes
    protected abstract int getLayoutResID();

//    @Overridedd
//    public Resources getResources() {
//        if(ScreenUtils.isPortrait()){
//            return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
//        }else {
//            return AdaptScreenUtils.adaptHeight(super.getResources(), 1080);
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.e("onCreate:--" + this.toString());
        super.onCreate(savedInstanceState);
        //清空启动背景
        //this.getWindow().getDecorView().setBackground(null);
        setContentView(getLayoutResID());
        BaseApplication.setContextRef(this);
        unbinder = ButterKnife.bind(this);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.setContextRef(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtils.e("onConfigurationChanged:--" + this.toString());
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        LogUtils.e("onBackPressedSupport:--" + this.toString());
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, R.anim.slide_out_right);
    }
}
