package com.sweetieplayer.vod.banner;


import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sweetieplayer.vod.R;
import com.sweetieplayer.vod.bean.BannerBean;
import com.sweetieplayer.vod.bean.VodBean;
import me.drakeet.multitype.ItemViewBinder;

@SuppressWarnings("unused")
public class BannerItemViewBinder extends ItemViewBinder<BannerBean, BannerItemViewBinder.ViewHolder> implements BlurBanner.onBannerActionListener{

    private BlurBanner.onBannerActionListener onActionListener;
    public BannerItemViewBinder setOnActionListener(BlurBanner.onBannerActionListener onActionListener) {
        this.onActionListener = onActionListener;
        return this;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_banner, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BannerBean item) {
        holder.myBanner.setOnBannerActionListener(this);
        holder.myBanner.setDataList(item.getBannerList());
        holder.myBanner.start();
    }

    @Override
    public void onPageChange(int position, Bitmap bitmap) {
        if (onActionListener != null) onActionListener.onPageChange(position, bitmap);
    }

    @Override
    public void onBannerClick(int position, Object o) {
        if (onActionListener != null) onActionListener.onBannerClick(position, o);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private @NonNull
        final BlurBanner<VodBean> myBanner;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            myBanner = itemView.findViewById(R.id.item_banner);
        }

    }

}

