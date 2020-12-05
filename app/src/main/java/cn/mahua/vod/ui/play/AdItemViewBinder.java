package cn.mahua.vod.ui.play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.mahua.vod.R;
import cn.mahua.vod.ad.AdWebView;
import me.drakeet.multitype.ItemViewBinder;

public class AdItemViewBinder extends ItemViewBinder<String, AdItemViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new AdItemViewBinder.ViewHolder(inflater.inflate(R.layout.item_ad, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item) {
        AdWebView adWebView = (AdWebView) holder.itemView;
        adWebView.loadHtmlBody(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
