package cn.mahua.vod.ads;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.R;
import cn.mahua.vod.ad.AdWebView;
import cn.mahua.vod.bean.StartBean;
import me.drakeet.multitype.ItemViewBinder;

public class AdViewBinder extends ItemViewBinder<StartBean.Ad, AdViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_ads, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull StartBean.Ad ad) {
        if (ad == null || ad.getStatus() == 0 || ad.getDescription() == null || ad.getDescription().isEmpty()) {
            holder.adWebView.setVisibility(View.GONE);
            holder.blankView.setVisibility(View.GONE);
        } else {
            holder.adWebView.setVisibility(View.VISIBLE);
            holder.blankView.setVisibility(View.VISIBLE);
            holder.adWebView.loadHtmlBody(ad.getDescription());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private @NonNull
        final AdWebView adWebView;
        final View blankView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            adWebView = itemView.findViewById(R.id.adWebView);
            blankView = itemView.findViewById(R.id.blank_view);
        }
    }

}
