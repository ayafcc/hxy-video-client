package cn.mahua.vod.ui.screen;

import android.app.Activity;
import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseItemClickListener;
import cn.mahua.vod.base.BaseItemClickListener2;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.card.CenterLayoutManager;
import cn.mahua.vod.ui.home.Vod;
import cn.mahua.vod.ui.play.PlayActivity;
import cn.mahua.vod.utils.AppColorUtils;
import kotlin.Pair;
import me.drakeet.multitype.MultiTypeAdapter;

import static org.litepal.LitePalApplication.getContext;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    //viewType
    private final static int VIEW_TYPE_HEADER = 0;
    private final static int VIEW_TYPE_DATA = 1;

    //data
    private Activity mActivity;
    private List<VodBean> dataList;
    //ui
    private FrameLayout headerContainer;
    private RecyclerView mRecyclerView;
    MultiTypeAdapter adapter1;
    MultiTypeAdapter adapter2;
    MultiTypeAdapter adapter3;
    MultiTypeAdapter adapter4;
    TitleItemViewBinder titleItemViewBinder1;
    TitleItemViewBinder titleItemViewBinder2;
    TitleItemViewBinder titleItemViewBinder3;
    TitleItemViewBinder titleItemViewBinder4;
    CenterLayoutManager centerLm1;
    CenterLayoutManager centerLm2;
    CenterLayoutManager centerLm3;
    CenterLayoutManager centerLm4;
    RecyclerView rv1;
    RecyclerView rv2;
    RecyclerView rv3;
    RecyclerView rv4;
    Titles list01;
    Titles list02;
    Titles list03;
    Titles list04;

    public MainRecyclerViewAdapter(Activity activity, RecyclerView recyclerView) {
        mActivity = activity;
        mRecyclerView = recyclerView;

        createHeaderContainer();
    }

    protected boolean isScrolling = false;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    private void createHeaderContainer() {
        headerContainer = new FrameLayout(mActivity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        headerContainer.setLayoutParams(lp);
    }

    public void setHeader(Titles list1, Titles list2, Titles list3, Titles list4) {
        list01 = list1;
        list02 = list2;
        list03 = list3;
        list04 = list4;
        if (headerContainer == null) {
            return;
        }
        headerContainer.removeAllViews();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_four_rv, null);
        rv1 = (RecyclerView) view.findViewById(R.id.rv_1);
        rv2 = (RecyclerView) view.findViewById(R.id.rv_2);
        rv3 = (RecyclerView) view.findViewById(R.id.rv_3);
        rv4 = (RecyclerView) view.findViewById(R.id.rv_4);

        centerLm1 = new CenterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        centerLm2 = new CenterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        centerLm3 = new CenterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        centerLm4 = new CenterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rv1.setLayoutManager(centerLm1);
        rv2.setLayoutManager(centerLm2);
        rv3.setLayoutManager(centerLm3);
        rv4.setLayoutManager(centerLm4);

        adapter1 = new MultiTypeAdapter();
        titleItemViewBinder1 = new TitleItemViewBinder();
        titleItemViewBinder1.setData(list1);
        titleItemViewBinder1.setBaseItemClickListener(new BaseItemClickListener2() {
            @Override
            public void onClickItem(View view, Object item, int positon) {
//                rv1.smoothScrollToPosition(positon);
                list1.setCurTitle(list1.getTitles().get(positon));
                centerLm1.smoothScrollToPosition(rv1, new RecyclerView.State(), positon);
                adapter1.notifyDataSetChanged();
            }
        });
//        titleItemViewBinder1.setLayoutManager(rv1, centerLm1);
        adapter1.register(Title.class, titleItemViewBinder1);
        adapter1.setItems(list1.getTitles());
        rv1.setNestedScrollingEnabled(false);
        rv1.setAdapter(adapter1);


        adapter2 = new MultiTypeAdapter();
        titleItemViewBinder2 = new TitleItemViewBinder();
        titleItemViewBinder2.setBaseItemClickListener(new BaseItemClickListener2() {
            @Override
            public void onClickItem(View view, Object item, int positon) {
//                rv2.smoothScrollToPosition(positon);
                list2.setCurTitle(list2.getTitles().get(positon));
                centerLm2.smoothScrollToPosition(rv2, new RecyclerView.State(), list2.getCurPos());
                adapter2.notifyDataSetChanged();
            }
        });
        titleItemViewBinder2.setData(list2);
        adapter2.register(Title.class, titleItemViewBinder2);
        adapter2.setItems(list2.getTitles());
        rv2.setNestedScrollingEnabled(false);
        rv2.setAdapter(adapter2);


        adapter3 = new MultiTypeAdapter();
        titleItemViewBinder3 = new TitleItemViewBinder();
        titleItemViewBinder3.setData(list3);
        titleItemViewBinder3.setBaseItemClickListener(new BaseItemClickListener2() {
            @Override
            public void onClickItem(View view, Object item, int positon) {
//                rv3.smoothScrollToPosition(positon);
                list3.setCurTitle(list3.getTitles().get(positon));
                centerLm3.smoothScrollToPosition(rv3, new RecyclerView.State(), positon);
                adapter3.notifyDataSetChanged();
            }
        });
        adapter3.register(Title.class, titleItemViewBinder3);
        adapter3.setItems(list3.getTitles());
        rv3.setNestedScrollingEnabled(false);
        rv3.setAdapter(adapter3);


        adapter4 = new MultiTypeAdapter();
        titleItemViewBinder4 = new TitleItemViewBinder();
        titleItemViewBinder4.setData(list4);
        titleItemViewBinder4.setBaseItemClickListener(new BaseItemClickListener2() {
            @Override
            public void onClickItem(View view, Object item, int positon) {
//                rv4.smoothScrollToPosition(positon);
                list4.setCurTitle(list4.getTitles().get(positon));
                centerLm4.smoothScrollToPosition(rv4, new RecyclerView.State(), positon);
                adapter4.notifyDataSetChanged();
            }
        });
        adapter4.register(Title.class, titleItemViewBinder4);
        adapter4.setItems(list4.getTitles());
        rv4.setNestedScrollingEnabled(false);
        rv4.setAdapter(adapter4);


        centerLm1.smoothScrollToPosition(rv1, new RecyclerView.State(), list1.getCurPos());
        centerLm2.smoothScrollToPosition(rv2, new RecyclerView.State(), list2.getCurPos());
        centerLm3.smoothScrollToPosition(rv3, new RecyclerView.State(), list3.getCurPos());
        centerLm4.smoothScrollToPosition(rv4, new RecyclerView.State(), list4.getCurPos());
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
        adapter4.notifyDataSetChanged();

        headerContainer.addView(view);
    }

    public void setDataList(List<VodBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void showSelectedInMiddle() {
        centerLm1.smoothScrollToPosition(rv1, new RecyclerView.State(), list01.getCurPos());
        adapter1.notifyDataSetChanged();
        centerLm2.smoothScrollToPosition(rv2, new RecyclerView.State(), list02.getCurPos());
        adapter2.notifyDataSetChanged();
        centerLm3.smoothScrollToPosition(rv3, new RecyclerView.State(), list03.getCurPos());
        adapter3.notifyDataSetChanged();
        centerLm4.smoothScrollToPosition(rv4, new RecyclerView.State(), list04.getCurPos());
        adapter4.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                itemView = headerContainer;
                return new HeaderHolder(itemView);
            default:
                return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_child, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int position) {
        if (position == 0) {
            return;
        }

        DataViewHolder holder = (DataViewHolder) h;
        VodBean item = dataList.get(position - 1);
        holder.itemView.setTag(R.id.itemData, item);
        holder.itemView.setOnClickListener(this);

        holder.title.setText(item.getVodName());
        if (item.getVodBlurb() == null || item.getVodBlurb().isEmpty()) {
            holder.subTitle.setVisibility(View.GONE);
        } else {
            holder.subTitle.setVisibility(View.VISIBLE);
            holder.subTitle.setText(item.getVodBlurb());
        }
//        if (StringUtils.isEmpty(item.getVod_custom_tag())) {
//            holder.tip.setVisibility(View.GONE);
//        } else {
//            holder.tip.setVisibility(View.VISIBLE);
//            holder.tip.setText(item.getVod_custom_tag());
//            holder.tip.setBackgroundResource(AppColorUtils.getTagBgResId(item.getVod_custom_tag()));
//        }
        Pair<String, Integer> pair = AppColorUtils.getTagBgResId(position, item.getVod_custom_tag());
        if (pair.component1().isEmpty()) {
            holder.tip.setVisibility(View.INVISIBLE);
        } else {
            holder.tip.setVisibility(View.VISIBLE);
            holder.tip.setText(pair.component1());
            holder.tip.setBackgroundResource(pair.component2());
        }

        if (item.getType().getTypeName().equals("电影")) {
            TextPaint tp = holder.up_title.getPaint();
            tp.setFakeBoldText(true);
            holder.up_title.setText(item.getVod_score());
            holder.up_title.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
        } else {
            TextPaint tp = holder.up_title.getPaint();
            tp.setFakeBoldText(false);
            holder.up_title.setTextColor(ColorUtils.getColor(R.color.white));
            holder.up_title.setText(item.getVodRemarks());
        }

        String url = item.getVodPic();
        if (!TextUtils.isEmpty(url) && !isScrolling) {
            // 这里可以用Glide等网络图片加载库
            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.shape_bg_white_icon);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_DATA;
    }


    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 1;
        }
        return dataList.size() + 1;
    }

    //span count
    private final static int HEADER_SPAN_COUNT = 1;
    private final static int DATA_SPAN_COUNT = 3;

    private BaseItemClickListener baseItemClickListener;


    public void setBaseItemClickListener(BaseItemClickListener baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
    }

    @Override
    public void onClick(View v) {
        Object o = v.getTag(R.id.itemData);
        if (o != null) {
            if (o instanceof Vod) {
                Vod vod = (Vod) o;
                PlayActivity.startByVod(vod);
            }
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {

        private @NonNull
        ImageView icon;
        private @NonNull
        TextView tip;
        private @NonNull
        TextView up_title;
        private @NonNull
        TextView title;
        private @NonNull
        TextView subTitle;

        DataViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_iv_card_child_icon);
            tip = itemView.findViewById(R.id.item_tv_card_child_tip);
            up_title = itemView.findViewById(R.id.item_tv_card_child_up_title);
            title = itemView.findViewById(R.id.item_tv_card_child_title);
            subTitle = itemView.findViewById(R.id.item_tv_card_child_vod_blurb);
        }
    }

    public static class GridLayoutManager extends androidx.recyclerview.widget.GridLayoutManager {

        public GridLayoutManager(Context context) {
            super(context, DATA_SPAN_COUNT);

            this.setSpanSizeLookup(new SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == 0 ? DATA_SPAN_COUNT : HEADER_SPAN_COUNT;
                }
            });
        }
    }
}

