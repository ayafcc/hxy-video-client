package cn.mahua.vod.ui.rank;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.mahua.vod.R;
import cn.mahua.vod.bean.RankOrderEvent;
import cn.mahua.vod.utils.DensityUtils;

public class RankPopup extends PopupWindow implements View.OnClickListener {
    private LinearLayout linear_layout;
    private TextView dbp_text;
    private Context context;

    public RankPopup(final Activity context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_rank_popup, null);

        setContentView(view);
        setWidth(DensityUtils.INSTANCE.dp2px(context, 80));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
        setOutsideTouchable(true);
        initView(view);

    }

    private void initView(View view) {
        view.findViewById(R.id.tv_total).setOnClickListener(this);
        view.findViewById(R.id.tv_month).setOnClickListener(this);
        view.findViewById(R.id.tv_week).setOnClickListener(this);
        view.findViewById(R.id.tv_day).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_total:
                EventBus.getDefault().postSticky(new RankOrderEvent(1));
                dismiss();
                break;
            case R.id.tv_month:
                EventBus.getDefault().postSticky(new RankOrderEvent(2));
                dismiss();
                break;
            case R.id.tv_week:
                EventBus.getDefault().postSticky(new RankOrderEvent(3));
                dismiss();
                break;
            case R.id.tv_day:
                EventBus.getDefault().postSticky(new RankOrderEvent(4));
                dismiss();
                break;
        }
    }
}