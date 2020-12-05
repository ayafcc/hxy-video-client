package cn.mahua.vod.ui.play;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.ui.widget.X5WebView;


public class X5WebActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    private X5WebView myX5WebView;
    private String webUrl;

    private long exitTime = 0;
    private Bundle extras;
    public final String TAG = "x5webview";
    public static final String DATA = "确定退出当前页面吗？";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myX5WebView = this.findViewById(R.id.main_web);

        extras = getIntent().getExtras();
        webUrl = extras.getString("url");
        String title = extras.getString("title");
        tvTitle.setText(title);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myX5WebView.canGoBack()) {
                    myX5WebView.goBack();
                } else {
                    finish();
                }
            }
        });

        Log.i(TAG, "load url : " + webUrl);
        //full(true);
        // webUrl = "http://www.baidu.com";
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myX5WebView.loadUrl(webUrl);
        myX5WebView.setListener(new X5WebView.x5EventListener() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (view != null && view.getTitle() != null && !view.getTitle().equals("") && tvTitle != null) {
                    tvTitle.setText(view.getTitle());
                }
            }

            @Override
            public void shouldOverrideUrlLoading(WebView view, String url) {
                if (view.getTitle() != null && !view.getTitle().equals("") && tvTitle != null) {
                    tvTitle.setText(view.getTitle());
                }
            }
        });
    }


    @Override
    public void initListener() {

    }
//
//    @Override
//    public void initData() {
//        myX5WebView.loadUrl(webUrl);
//    }

    //    @Override
//    public void processClick(View v) {
//
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(this, DATA, Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//            }
            if (myX5WebView.canGoBack()) {
                myX5WebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.layout_x5;
    }


}
