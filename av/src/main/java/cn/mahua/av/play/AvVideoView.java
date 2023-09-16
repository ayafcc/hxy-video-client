package cn.mahua.av.play;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.blankj.utilcode.util.ConvertUtils;
import java.util.LinkedHashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SimpleTextCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import xyz.doikki.videoplayer.player.VideoView;

public class AvVideoView extends VideoView implements VideoViewImpt {

    //弹幕
    private DanmakuView mDanmakuView;
    private AvVideoController mAvVideoController;
    private DanmakuContext mDanmakuContext;
    private BaseDanmakuParser mDanmakuParser;
    //清晰度
    private LinkedHashMap<String, String> mHdMap;
    private String mCurHd;
    private HdClickListener mHdClickListener;

    public AvVideoView(@NonNull Context context) {
        super(context);
    }

    public AvVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        initDanmakuView();
    }

    @Override
    protected void startPrepare(boolean needReset) {
        super.startPrepare(needReset);
        if (mDanmakuView != null) {
            mDanmakuView.prepare(mDanmakuParser, mDanmakuContext);
        }
    }

    @Override
    protected void startInPlaybackState() {
        super.startInPlaybackState();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    public void pause() {
        super.pause();
        if (isInPlaybackState()) {
            if (mDanmakuView != null && mDanmakuView.isPrepared()) {
                mDanmakuView.pause();
            }
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    public void release() {
        super.release();
        if (mDanmakuView != null) {
            mDanmakuView.release();
        }
    }

    @Override
    public void seekTo(long pos) {
        super.seekTo(pos);
        if (isInPlaybackState()) {
            if (mDanmakuView != null) mDanmakuView.seekTo(pos);
        }
    }
    private void initDanmakuView() {
        if (mDanmakuView == null) {
            mDanmakuView = new DanmakuView(getContext());
//            mDanmakuView.showFPS(true);
            mDanmakuView.enableDanmakuDrawingCache(true);
            mDanmakuView.setCallback(new DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {

                }

                @Override
                public void prepared() {
                    if (mDanmakuView != null) mDanmakuView.start();
                }
            });
            mDanmakuContext = DanmakuContext.create();
            mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_NONE, 3)
                    .setCacheStuffer(new SimpleTextCacheStuffer(), null)
                    .setDuplicateMergingEnabled(false)
                    .setMaximumVisibleSizeInScreen(-1)
                    .setScrollSpeedFactor(1.2f)
                    .setMaximumLines(null)
                    .setDanmakuMargin(10);
            mDanmakuParser = new BaseDanmakuParser() {
                @Override
                protected IDanmakus parse() {
                    return new Danmakus();
                }
            };
        }
        //开始把view添加进去
        mPlayerContainer.removeView(mDanmakuView);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPlayerContainer.addView(mDanmakuView, layoutParams);
        //将控制器提到最顶层，如果有的话
        if (mVideoController != null) {
            mVideoController.bringToFront();
        }
    }


    @Override
    public void setVideoSpeed(float speed) {
        this.setSpeed(speed);
    }

    @Override
    public void showDanmaku() {
        if (mDanmakuView != null && isAttachedToWindow()) mDanmakuView.show();
    }

    @Override
    public void hideDanmaku() {
        if (mDanmakuView != null && isAttachedToWindow()) mDanmakuView.hide();
    }

    @Override
    public void addDanmaku(String text, boolean isSelf) {
        if (mDanmakuView == null || !isAttachedToWindow()) return;
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null) {
            return;
        }
        if (isSelf) {
            danmaku.isLive = true;
            danmaku.priority = 1;  // 可能会被各种过滤器过滤并隐藏显示
            danmaku.borderColor = Color.TRANSPARENT;
        } else {
            danmaku.isLive = false;
            danmaku.priority = 0;
            danmaku.borderColor = Color.TRANSPARENT;
        }
        danmaku.text = text;
        danmaku.textSize = ConvertUtils.dp2px(14);
        danmaku.textColor = Color.WHITE;
        danmaku.textShadowColor = Color.GRAY;
        danmaku.setTime(mDanmakuView.getCurrentTime());
        mDanmakuView.addDanmaku(danmaku);
    }

    public void setHdClickListener(HdClickListener hdClickListener) {
        this.mHdClickListener = hdClickListener;
    }

    @Override
    public LinkedHashMap<String, String> getHdData() {
        return mHdMap;
    }

    @Override
    public void switchHd(String hd) {
        String url = mHdMap.get(hd);
        if (hd.equals(mCurHd)) return;
        mCurHd = hd;
        if (mHdClickListener != null) {
            //返回等待解析的地址
            mHdClickListener.switchHd(url);
        }
    }

    //设置等待解析地址合集
    public void setHds(LinkedHashMap<String, String> videos) {
        this.mHdMap = videos;
        switchHd(getValueFromLinkedMap(videos,0));
    }

    public static String getValueFromLinkedMap(LinkedHashMap<String, String> map, int index) {
        int currentIndex = 0;
        for (String key : map.keySet()) {
            if (currentIndex == index) {
                return map.get(key);
            }
            currentIndex++;
        }
        return null;
    }


}
