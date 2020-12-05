package cn.mahua.vod.ui.seek;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import cn.mahua.vod.R;

public class SeekHistoryView extends FlexboxLayout implements View.OnClickListener, View.OnLongClickListener {

    private static final String KEY_SEEK_HISTORY = "KEY_SEEK_HISTORY";
    private SeekHistory mData;
    private OnItemClickListener mOnItemClickListener;

    @LayoutRes
    private static final int mItemLayoutRes = R.layout.item_seek_history;

    public SeekHistoryView(Context context) {
        this(context, null);
    }

    public SeekHistoryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekHistoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setAlignItems(AlignItems.FLEX_START);
        setFlexWrap(FlexWrap.WRAP);
        setFlexDirection(FlexDirection.ROW);
        setJustifyContent(JustifyContent.FLEX_START);
        //初始化
        SeekHistory seekHistory = (SeekHistory) CacheDiskStaticUtils.getSerializable(KEY_SEEK_HISTORY);
        initData(seekHistory);
    }

    private void initData(SeekHistory data) {
        if (data == null) {
            data = new SeekHistory();
        }
        mData = data;
        if (mData.getHistoryList() == null) {
            mData.setHistoryList(new ArrayList<>());
        } else {
            for (String str : mData.getHistoryList()) {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(mItemLayoutRes, this, false);
                textView.setText(str);
                textView.setOnClickListener(this);
                textView.setOnLongClickListener(this);
                addView(textView);
            }
        }
    }

    //这里对数据进行了过滤
    public void addData(String data) {
        if (mData != null && mData.getHistoryList() != null) {
            //存在这个词
            if (mData.getHistoryList().contains(data)) {
                int index = mData.getHistoryList().indexOf(data);

                if (getChildCount() >= (index + 1)) {
                    //移除View
                    removeViewAt(index);
                    //移除data
                    mData.getHistoryList().remove(data);
                }
            }
            TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(mItemLayoutRes, this, false);
            textView.setText(data);
            textView.setOnClickListener(this);
            textView.setOnLongClickListener(this);
            addView(textView, 0);
            mData.getHistoryList().add(0, data);
            if (mData.getHistoryList().size() > 10) {
                mData.getHistoryList().remove(10);
                removeViewAt(10);
            }
            CacheDiskStaticUtils.put(KEY_SEEK_HISTORY, mData);
        }
    }

    public void clear() {
        post(this::removeAllViews);
        CacheDiskStaticUtils.put(KEY_SEEK_HISTORY, new SeekHistory());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        String data = ((TextView) view).getText().toString();
        ToastUtils.showShort(data);
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(view, data);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mData == null || mData.getHistoryList() == null) return true;
        String data = ((TextView) view).getText().toString();
        mData.getHistoryList().remove(data);
        CacheDiskStaticUtils.put(KEY_SEEK_HISTORY, mData);
        removeView(view);
        return true;
    }


    public interface OnItemClickListener {
        void onClick(View view, String data);
    }

    static class SeekHistory implements Serializable {

        private static final long serialVersionUID = -9089707979039572411L;

        List<String> historyList;

        List<String> getHistoryList() {
            return historyList;
        }

        void setHistoryList(List<String> historyList) {
            this.historyList = historyList;
        }
    }

}
