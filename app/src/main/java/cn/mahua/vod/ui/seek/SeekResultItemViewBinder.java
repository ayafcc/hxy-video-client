package cn.mahua.vod.ui.seek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseItemClickListener;
import cn.mahua.vod.bean.VodBean;
import me.drakeet.multitype.ItemViewBinder;

@SuppressWarnings("unused")
public class SeekResultItemViewBinder extends ItemViewBinder<VodBean, SeekResultItemViewBinder.ViewHolder> implements View.OnClickListener {

    private BaseItemClickListener mBaseItemClickListener;

    public void setBaseItemClickListener(BaseItemClickListener baseItemClickListener) {
        this.mBaseItemClickListener = baseItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_seek_result, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VodBean item) {
        holder.itemView.setTag(R.id.itemData,item);
        holder.itemView.setOnClickListener(this);

        Glide.with(holder.icon)
                .load( item.getVodPic())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.icon);
        holder.title.setText(item.getVod_name());
        holder.year.setText("年代："+item.getVod_year()+"."+item.getType().getType_name()+"."+item.getVod_area());
        holder.actor.setText("主演："+item.getVod_actor());
        holder.zlass.setText("类型："+item.getVod_class());
        holder.remarks.setText("状态："+item.getVod_remarks());
        holder.hits.setText("播放次数："+item.getVod_hits());
        holder.score.setText(item.getVod_score()+"分");
    }

    @Override
    public void onClick(View view) {
        if(mBaseItemClickListener != null){
            Object o = view.getTag(R.id.itemData);
            mBaseItemClickListener.onClickItem(view,o);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView title,year,actor,zlass,remarks,hits,score;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_iv_seek_result_icon);
            title = itemView.findViewById(R.id.item_tv_seek_result_title);
            year = itemView.findViewById(R.id.item_tv_seek_result_year);
            actor = itemView.findViewById(R.id.item_tv_seek_result_actor);
            zlass = itemView.findViewById(R.id.item_tv_seek_result_zlass);
            remarks = itemView.findViewById(R.id.item_tv_seek_result_remarks);
            hits = itemView.findViewById(R.id.item_tv_seek_result_hits);
            score = itemView.findViewById(R.id.item_tv_seek_result_score);
        }
    }

}

