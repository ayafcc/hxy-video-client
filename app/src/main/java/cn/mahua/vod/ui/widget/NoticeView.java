package cn.mahua.vod.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.mahua.vod.R;

/**
 * Created by AItsuki on 2016/1/18.
 */
public class NoticeView extends FrameLayout implements View.OnClickListener {

    private final long DEFAULT_ANIMATION_DURATION = 1000; // 动画时长
    private final long DEFAULT_NOTICE_SPACE = 3000; // 公告切换时长
    private final int DEFAULT_TEXT_COLOR = 0xff000000;  // 默认字体颜色

    private long mNoticeDuration = DEFAULT_NOTICE_SPACE;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private float mTextSize;
    private LayoutParams mLayoutParams;
    private List<TextView> mNoticeList;
    private int mCurrentNotice;
    private AnimationSet mEnterAnimSet;
    private AnimationSet mExitAnimSet;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private NoticeRunnable mNoticeRunnalbe;
    private OnItemClickListener mListener;
    private TextPaint textPaint;


    public NoticeView(Context context) {
        this(context, null);
    }

    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.NoticeView);
        mTextColor = array.getColor(R.styleable.NoticeView_textColor, DEFAULT_TEXT_COLOR);
        mTextSize = array.getDimension(R.styleable.NoticeView_textSize, mTextSize);
        array.recycle();

        // 初始化动画
        createExitAnimation();
        createEnterAnimation();

        // 初始化一个画笔，用于测量高度
        textPaint = new TextPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 如果是未指定大小，那么设置宽为300px
        int exceptWidth = 300;
        int exceptHeight = 0;

        // 计算高度，如果将高度设置为textSize会很丑，因为文字有默认的上下边距。
        if(MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            if(mTextSize > 0) {
                textPaint.setTextSize(mTextSize);
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                exceptHeight = (int) (fontMetrics.bottom - fontMetrics.top);
            }
        }

        int width = resolveSize(exceptWidth, widthMeasureSpec);
        int height = resolveSize(exceptHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private void createEnterAnimation() {
        mEnterAnimSet = new AnimationSet(false);
        TranslateAnimation translateAnimation =
                new TranslateAnimation(0,0,0,0, TranslateAnimation.RELATIVE_TO_PARENT, 1f,
                        TranslateAnimation.RELATIVE_TO_SELF, 0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f,1f);
        mEnterAnimSet.addAnimation(translateAnimation);
        mEnterAnimSet.addAnimation(alphaAnimation);
        mEnterAnimSet.setDuration(DEFAULT_ANIMATION_DURATION);
    }

    private void createExitAnimation() {
        mExitAnimSet = new AnimationSet(false);
        TranslateAnimation translateAnimation =
                new TranslateAnimation(0,0,0,0, TranslateAnimation.RELATIVE_TO_SELF, 0f,
                        TranslateAnimation.RELATIVE_TO_PARENT, -1f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f,0f);
        mExitAnimSet.addAnimation(translateAnimation);
        mExitAnimSet.addAnimation(alphaAnimation);
        mExitAnimSet.setDuration(DEFAULT_ANIMATION_DURATION);
    }

    /**
     * 设置公告的集合
     * @param list
     */
    public void setNoticeList(List<String> list) {
        // 设置集合的时候，要将上一次的集合清除。
        if(list == null || list.size() ==0) {
            return;
        }
        removeAllViews();
        if(mNoticeList == null) {
            mNoticeList = new ArrayList<>();
        }
        mNoticeList.clear();

        // 创建TextView
        for (int i=0; i< list.size(); i++) {
            TextView textView = createTextView(list.get(i));
            mNoticeList.add(textView);
            addView(textView);
        }
        mCurrentNotice = 0;
        mNoticeList.get(mCurrentNotice).setVisibility(VISIBLE);
    }

    /**
     * 设置条目点击侦听
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        setOnClickListener(this);
        mListener = listener;
    }

    /**
     * 设置公告的切换间隔
     * @param duration
     */
    public void setNoticeDuration(long duration) {
        if(duration > 0) {
            mNoticeDuration = duration;
        }
    }

    /**
     * 设置默认动画的时长
     * @param duration
     */
    public void setAnimationDuration(long duration) {
        if(duration > 0) {
            if(mEnterAnimSet != null) {
                mEnterAnimSet.setDuration(duration);
            }
            if(mExitAnimSet != null) {
                mExitAnimSet.setDuration(duration);
            }
        }
    }

    /**
     * @param animation
     */
    public void setEnterAnimation(AnimationSet animation) {
        mEnterAnimSet = animation;
    }

    /**
     * 设置公告的退出动画
     * @param animation
     */
    public void setExitAnimation(AnimationSet animation) {
        mExitAnimSet = animation;
    }

    /**
     * 开始循环播放公告
     * 推荐和pause()配合在生命周期中使用
     */
    public void start() {
        if(mNoticeRunnalbe == null) {
            mNoticeRunnalbe = new NoticeRunnable();
        } else {
            mHandler.removeCallbacks(mNoticeRunnalbe);
        }
        mHandler.postDelayed(mNoticeRunnalbe, mNoticeDuration);
    }

    /**
     * 暂停循环播放公告
     * 推荐和start()配合在生命周期中使用
     */
    public void pause() {
        if(mNoticeRunnalbe!= null) {
            mHandler.removeCallbacks(mNoticeRunnalbe);
        }
    }

    /**
     * TextView默认水平居中， singline， invisible
     * @param text
     * @return
     */
    private TextView createTextView(String text) {
        if (mLayoutParams == null) {
            mLayoutParams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            mLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        }

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(mLayoutParams);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(mTextColor);
        textView.setVisibility(INVISIBLE);
        textView.setText(text);
        // 如果有设置字体大小，如果字体大小为null。
        if (mTextSize > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize);
        }
        return textView;
    }

    @Override
    public void onClick(View v) {
        if(mListener != null && mNoticeList!= null && mNoticeList.size() >0) {
            mListener.onItemClick(mNoticeList.get(mCurrentNotice),mCurrentNotice);
        }
    }

    /**
     *
     * 动画开始的一瞬间，就代表这个公告已经invisiable，下一个公告开始进入，点击事件也是给了下一个公告。
     */
    class NoticeRunnable implements Runnable {
        @Override
        public void run() {
            // 隐藏当前的textView
            TextView currentView = mNoticeList.get(mCurrentNotice);
            currentView.setVisibility(INVISIBLE);
            if(mExitAnimSet != null) {
                currentView.startAnimation(mExitAnimSet);
            }
            mCurrentNotice++;
            if(mCurrentNotice >= mNoticeList.size()) {
                mCurrentNotice = 0;
            }

            // 显示下一个TextView
            TextView nextView = mNoticeList.get(mCurrentNotice);
            nextView.setVisibility(VISIBLE);
            if(mEnterAnimSet != null) {
                nextView.startAnimation(mEnterAnimSet);
            }
            mHandler.postDelayed(this, mNoticeDuration);
        }
    }

    /**
     * 点击的回调。TextView是被点中的公告。
     */
    public interface OnItemClickListener {
        void onItemClick(TextView view, int position);
    }
}
