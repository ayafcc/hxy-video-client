package cn.mahua.vod.ui.play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.R;
import cn.mahua.vod.bean.RecommendBean;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.ui.home.MyDividerItemDecoration;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Autor: LiQingfeng
 * Date: 2019/11/12
 * Desc:
 **/
public class RecommendViewBinder extends ItemViewBinder<RecommendBean, RecommendViewBinder.ViewHolder> {
    private OnTypeChangeLisenter onTypeChangeLisenter;

    public RecommendViewBinder setOnTypeChangeLisenter(OnTypeChangeLisenter onTypeChangeLisenter) {
        this.onTypeChangeLisenter = onTypeChangeLisenter;
        return this;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new RecommendViewBinder.ViewHolder(inflater.inflate(R.layout.item_recommend, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RecommendBean item) {
        if(item.getType() == 0){
            holder.tvLikeRecommend.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
            holder.tvLikeActor.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.gray_999));
        }else{
            holder.tvLikeRecommend.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.gray_999));
            holder.tvLikeActor.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
        }
        holder.tvLikeRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTypeChangeLisenter != null){
                    onTypeChangeLisenter.onTypeChange(0);
                }
            }
        });
        holder.tvLikeActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTypeChangeLisenter != null){
                    onTypeChangeLisenter.onTypeChange(1);
                }
            }
        });
        //设置一行显示的数目18
        GridLayoutManager gridLayoutManager = new GridLayoutManager(holder.itemView.getContext(), 3, RecyclerView.VERTICAL, false);
        //根据数据类型显示占据的item个数
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 3;
            }
        });
        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(holder.itemView.getContext(), RecyclerView.HORIZONTAL, false);
        dividerItemDecoration.setDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.divider_image));
        holder.rvLikeRecommend.addItemDecoration(dividerItemDecoration);
        holder.rvLikeRecommend.setLayoutManager(gridLayoutManager);

        holder.rvLikeRecommend.setAdapter(new LastestAdapter(item.getVodBeanList()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLikeRecommend;
        TextView tvLikeActor;
        TextView tvChange;
        RecyclerView rvLikeRecommend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLikeRecommend = itemView.findViewById(R.id.tvSameType);
            tvLikeActor = itemView.findViewById(R.id.tvSameActor);
            tvChange = itemView.findViewById(R.id.tvChange);
            rvLikeRecommend = itemView.findViewById(R.id.rvRecommand);
        }

    }

    class LastestAdapter extends BaseQuickAdapter<VodBean, BaseViewHolder> {

        public LastestAdapter(List<VodBean> data) {
            super(R.layout.item_card_child,data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, VodBean item) {
            helper.setVisible(R.id.item_tv_card_child_tip, false);
            helper.setVisible(R.id.item_tv_card_child_up_title, false);
            helper.setText(R.id.item_tv_card_child_title, item.getVodName());
            String img = item.getVodPicSlide();
            if (StringUtils.isEmpty(img)) {
                img = item.getVodPic();
            }
            Glide.with(helper.itemView.getContext())
                    .load(img)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) helper.getView(R.id.item_iv_card_child_icon));
        }
    }
    interface OnTypeChangeLisenter{
        void onTypeChange(int type);
    }
}
