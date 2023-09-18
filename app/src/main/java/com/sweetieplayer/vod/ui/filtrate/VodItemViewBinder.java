package com.sweetieplayer.vod.ui.filtrate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.R;
import com.sweetieplayer.vod.base.BaseItemClickListener;
import com.sweetieplayer.vod.bean.UpdateEvent;
import com.sweetieplayer.vod.bean.VodBean;
import com.sweetieplayer.vod.utils.AppColorUtils;
import kotlin.Pair;
import me.drakeet.multitype.ItemViewBinder;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@SuppressWarnings("unused")
public class VodItemViewBinder extends ItemViewBinder<VodBean, VodItemViewBinder.ViewHolder> implements View.OnClickListener {

    private BaseItemClickListener baseItemClickListener;

    public void setBaseItemClickListener(BaseItemClickListener baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_card_child, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VodBean item) {
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
        Pair<String, Integer> pair = AppColorUtils.getTagBgResId(holder.getAdapterPosition(), item.getVod_custom_tag());
        if (pair.component1().isEmpty()) {
            holder.tip.setVisibility(View.INVISIBLE);
        } else {
            holder.tip.setVisibility(View.VISIBLE);
            holder.tip.setText(pair.component1());
            holder.tip.setBackgroundResource(pair.component2());
        }

        holder.up_title.setText(item.getVodRemarks());

        String url = ApiConfig.BASE_URL + item.getVodPic();
//        if (!TextUtils.isEmpty(url) && !isScroll) {
//            // 这里可以用Glide等网络图片加载库
////            Glide.with(holder.itemView.getContext())
////                    .load(img)
////                    .thumbnail(0.1f)
//////                    .skipMemoryCache(true)
////                    .diskCacheStrategy(DiskCacheStrategy.ALL)
////                    .dontAnimate()
////                    .into(holder.icon);
//            Glide.with(holder.itemView.getContext())
//                    .load(url)
//                    .thumbnail(0.1f)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
////                    .transform(new GlideRoundTransform(0))
//                    .dontAnimate()
//                    .into(holder.icon);
//
////        Glide.with(holder.itemView.getContext())
////                .load(url)
////                .thumbnail(0.1f)
////                .diskCacheStrategy(DiskCacheStrategy.ALL)
////                .into(holder.icon);
//        } else {
//            holder.icon.setImageResource(R.drawable.shape_bg_white_icon);
//        }
//        if (!TextUtils.isEmpty(url) && !isScroll) {
        // 这里可以用Glide等网络图片加载库
        Glide.with(holder.itemView.getContext())
                .load(url)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.icon);
//        } else {
//            holder.icon.setImageResource(R.drawable.shape_bg_white_icon);
//        }
    }

    @Override
    public void onClick(View view) {
        Object o = view.getTag(R.id.itemData);
        if (o != null && baseItemClickListener != null) {
            baseItemClickListener.onClickItem(view, o);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final @NonNull
        ImageView icon;
        private final @NonNull
        TextView tip;
        private final @NonNull
        TextView up_title;
        private final @NonNull
        TextView title;
        private final @NonNull
        TextView subTitle;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_iv_card_child_icon);
            tip = itemView.findViewById(R.id.item_tv_card_child_tip);
            up_title = itemView.findViewById(R.id.item_tv_card_child_up_title);
            title = itemView.findViewById(R.id.item_tv_card_child_title);
            subTitle = itemView.findViewById(R.id.item_tv_card_child_vod_blurb);
        }
    }

    boolean isScroll;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(UpdateEvent event) {
        isScroll = event.isScroll;
//        System.out.println("=============="+isScroll);
    }

}
