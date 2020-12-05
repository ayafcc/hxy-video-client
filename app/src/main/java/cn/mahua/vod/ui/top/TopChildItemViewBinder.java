package cn.mahua.vod.ui.top;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseItemClickListener;
import cn.mahua.vod.ui.home.Vod;
import cn.mahua.vod.utils.DensityUtils;
import me.drakeet.multitype.ItemViewBinder;

import static org.litepal.LitePalApplication.getContext;


@SuppressWarnings("unused,WeakerAccess")
public class TopChildItemViewBinder extends ItemViewBinder<Vod, TopChildItemViewBinder.ViewHolder> implements View.OnClickListener {

    private BaseItemClickListener baseItemClickListener;

    public void setBaseItemClickListener(BaseItemClickListener baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_top_child, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Vod item) {
        holder.itemView.setTag(R.id.itemData, item);
        holder.itemView.setOnClickListener(this);
        holder.title.setText(item.getVodName());

        if( StringUtils.isEmpty(item.getVod_custom_tag())){
            holder.tip.setVisibility(View.GONE);
        }else{
            holder.tip.setVisibility(View.VISIBLE);
            holder.tip.setText(item.getVod_custom_tag());
        }
        if (item.getType().getTypeName().equals("电影")) {
            holder.up_title.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
            TextPaint tp = holder.up_title.getPaint();
            tp.setFakeBoldText(true);
            holder.up_title.setText(item.getVod_score());
        } else {
            holder.up_title.setTextColor(ColorUtils.getColor(R.color.white));
            holder.up_title.setText(item.getVodRemarks());
            TextPaint tp = holder.up_title.getPaint();
            tp.setFakeBoldText(false);
        }

        ViewGroup.LayoutParams lp = holder.icon.getLayoutParams();
        int per_width = (DensityUtils.INSTANCE.getScreenWidth(getContext()) - DensityUtils.INSTANCE.dp2px(getContext(), 4)) / 3;
        lp.height = (int) (per_width * 1f);
        Glide.with(holder.itemView)
                .load(ApiConfig.BASE_URL + item.getVodPic())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.icon);
    }

    @Override
    public void onClick(View view) {
        Object o = view.getTag(R.id.itemData);
        if (o != null && baseItemClickListener != null) {
            baseItemClickListener.onClickItem(view, o);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private @NonNull
        ImageView icon;
        private @NonNull
        TextView tip;
        private @NonNull
        TextView up_title;
        private @NonNull
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_iv_top_child_icon);
            tip = itemView.findViewById(R.id.item_tv_top_child_tip);
            up_title = itemView.findViewById(R.id.item_tv_top_child_up_title);
            title = itemView.findViewById(R.id.item_tv_top_child_title);
        }
    }

}
