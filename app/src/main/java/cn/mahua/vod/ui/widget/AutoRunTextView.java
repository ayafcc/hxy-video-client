package cn.mahua.vod.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AutoRunTextView extends AppCompatTextView {

    public AutoRunTextView(Context context) {
        super(context);

    }

    public AutoRunTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoRunTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {//必须重写，且返回值是true，表示始终获取焦点
        return true;
    }


}
