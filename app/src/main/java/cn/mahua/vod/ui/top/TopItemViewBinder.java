package cn.mahua.vod.ui.top;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.App;
import cn.mahua.vod.R;
import cn.mahua.vod.ad.AdWebView;
import cn.mahua.vod.base.BaseItemClickListener;
import cn.mahua.vod.bean.StartBean;
import cn.mahua.vod.bean.TopBean;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.ui.home.MyDividerItemDecoration;
import cn.mahua.vod.ui.home.Vod;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

public class TopItemViewBinder extends ItemViewBinder<TopBean, TopItemViewBinder.ViewHolder> implements View.OnClickListener, BaseItemClickListener {
    private TopItemActionListener actionListener;
    private StartBean.Ad ad;

    public TopItemViewBinder(int index) {
        if (App.startBean != null) {
            if (index == 0) {
                if (App.startBean!=null&&App.startBean.getAds() != null && App.startBean.getAds().getIndex() != null) {
                    ad = App.startBean.getAds().getIndex();
                }
            } else if (index == 1) {
                if (App.startBean!=null&&App.startBean.getAds() != null && App.startBean.getAds().getVod() != null) {
                    ad = App.startBean.getAds().getVod();
                }
            } else if (index == 2) {
                if (App.startBean!=null&&App.startBean.getAds() != null && App.startBean.getAds().getSitcom() != null) {
                    ad = App.startBean.getAds().getSitcom();
                }
            } else if (index == 3) {
                if (App.startBean!=null&&App.startBean.getAds() != null && App.startBean.getAds().getVariety() != null) {
                    ad = App.startBean.getAds().getVariety();
                }
            } else if (index == 4) {
                if (App.startBean!=null&&App.startBean.getAds() != null && App.startBean.getAds().getCartoon() != null) {
                    ad = App.startBean.getAds().getCartoon();
                }
            }
        }
    }

    public TopItemViewBinder setActionListener(TopItemActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater
                                                    inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_top, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TopBean item) {
        holder.change.setOnClickListener(this);
        holder.topChildItemViewBinder.setBaseItemClickListener(this);
        if (ad != null && !StringUtils.isEmpty(ad.getDescription()) && ad.getStatus() == 1) {
            holder.adWebView.setVisibility(View.VISIBLE);
            holder.adWebView.loadHtmlBody(ad.getDescription());
        } else {
            holder.adWebView.setVisibility(View.GONE);
        }
        holder.title.setText(item.getTitle().trim());
        holder.setVodList(item.getVodList());
    }

    @Override
    public void onClick(View view) {
        if (actionListener != null) {
            actionListener.onClickMore(view);
        }
    }

    @Override
    public void onClickItem(View view, Object item) {
        if (actionListener != null) {
            actionListener.onClickItem(view, item);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private @NonNull
        final TextView title;

        private @NonNull
        final AdWebView adWebView;

        private @NonNull
        final TextView change;

        private @NonNull
        final RecyclerView recyclerView;

        private MultiTypeAdapter adapter;
        private TopChildItemViewBinder topChildItemViewBinder;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            adWebView = itemView.findViewById(R.id.adWebView);
            title = itemView.findViewById(R.id.item_tv_top_title);
            change = itemView.findViewById(R.id.item_tv_top_change);
            //listView
            recyclerView = itemView.findViewById(R.id.item_rv_top);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 3, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(itemView.getContext(), RecyclerView.HORIZONTAL, false);
            dividerItemDecoration.setDrawable(itemView.getContext().getResources().getDrawable(R.drawable.divider_image));
            recyclerView.addItemDecoration(dividerItemDecoration);
            adapter = new MultiTypeAdapter();
            adapter.register(Vod.class, topChildItemViewBinder = new TopChildItemViewBinder());
            recyclerView.setAdapter(adapter);
        }

        private void setVodList(List<VodBean> list) {
            if (list == null) return;
            if (list.size() > 3) list = list.subList(0, 3);
            adapter.setItems(list);
            adapter.notifyDataSetChanged();
        }
    }

    @SuppressWarnings("unused")
    public interface TopItemActionListener {

        void onClickMore(View view);

        void onClickChange(View view);

        void onClickItem(View view, Object item);

    }

}
