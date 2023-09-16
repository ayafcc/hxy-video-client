package cn.mahua.vod.ui.live;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.mahua.vod.BuildConfig;
import cn.mahua.vod.R;
import cn.mahua.vod.bean.LiveBean;
import cn.mahua.vod.download.SPUtils;
import cn.mahua.vod.ui.home.Vod;
import cn.mahua.vod.ui.login.LoginActivity;
import cn.mahua.vod.ui.play.PlayActivity;
import cn.mahua.vod.ui.play.X5WebActivity;
import cn.mahua.vod.utils.LoginUtils;
import cn.mahua.vod.utils.UserUtils;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class LiveAdpter extends RecyclerView.Adapter<LiveAdpter.ViewHolder> {
    private final List<LiveBean> coll;// 消息对象数组
    private final LayoutInflater mInflater;
    private final Context context;

    public LiveAdpter(Context context, List<LiveBean> coll) {
        this.coll = coll;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.live_video_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LiveBean entity = coll.get(position);

        MultiTransformation mation = new MultiTransformation<>(new CenterCrop(), new RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL));
        Glide.with(holder.itemView.getContext())
                .load(entity.getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(mation))
                .into(holder.coverImg);
        holder.tvName.setText(entity.getName());
        holder.llTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginUtils.checkLogin(context)){
                        if (entity.getUrl().endsWith("mp4") || entity.getUrl().endsWith("m3u8")) {
                            Intent intent = new Intent(context, X5WebActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", entity.getUrl());
                            bundle.putString("title", "");
                            intent.putExtras(bundle);
                            ActivityUtils.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, X5WebActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", entity.getUrl());
                            bundle.putString("title", entity.getName());
                            intent.putExtras(bundle);
                            ActivityUtils.startActivity(intent);
                        }
                    }

            }
        });

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return coll.size();
    }

    public void clearData() {
        this.coll.clear();
        notifyDataSetChanged();
    }

    public void refresh(List<LiveBean> data) {
        reset(data);
        notifyDataSetChanged();
    }

    public void reset(List<LiveBean> data) {
        if (!data.isEmpty()) {
            this.coll.clear();
            for (int i = 0; i < data.size(); i++) {
                this.coll.add(data.get(i));
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout llTotal;
        public final TextView tvName;
        public final ImageView coverImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llTotal = (LinearLayout) itemView.findViewById(R.id.ll_total);
            coverImg = (ImageView) itemView.findViewById(R.id.live_cover);
            tvName = (TextView) itemView.findViewById(R.id.live_name);
        }
    }
}
