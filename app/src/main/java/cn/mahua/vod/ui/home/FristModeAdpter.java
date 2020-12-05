package cn.mahua.vod.ui.home;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.mahua.vod.R;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.utils.AppColorUtils;
import cn.mahua.vod.utils.DensityUtils;
import kotlin.Pair;

public class FristModeAdpter extends BaseAdapter {
    private List<VodBean> coll;// 消息对象数组
    private LayoutInflater mInflater;
    private Context context;

    public FristModeAdpter(Context context, List<VodBean> coll) {
        this.coll = coll;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        this.coll.clear();
        notifyDataSetChanged();
    }

    public void refresh(List<VodBean> data) {
        reset(data);
        notifyDataSetChanged();
    }

    public void reset(List<VodBean> data) {
        if (!data.isEmpty()) {
            this.coll.clear();
            for (int i = 0; i < data.size(); i++) {
                this.coll.add(data.get(i));
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        VodBean entity = coll.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_card_child, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = convertView.findViewById(R.id.item_iv_card_child_icon);
            ViewGroup.LayoutParams lp = viewHolder.icon.getLayoutParams();
            int per_width = (DensityUtils.INSTANCE.getScreenWidth(parent.getContext()) - DensityUtils.INSTANCE.dp2px(parent.getContext(), 4)) / 3;
            lp.height = (int) (per_width * 1.4f);
            viewHolder.icon.setLayoutParams(lp);
            viewHolder.tip = convertView.findViewById(R.id.item_tv_card_child_tip);
            viewHolder.up_title = convertView.findViewById(R.id.item_tv_card_child_up_title);
            viewHolder.title = convertView.findViewById(R.id.item_tv_card_child_title);
            viewHolder.blurb = convertView.findViewById(R.id.item_tv_card_child_vod_blurb);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//            SimpleUtils.setImageLoading(viewHolder.coverImg, entity.getImg(), R.drawable.topica);
//            viewHolder.tvName.setText(entity.getName());

        VodBean item = coll.get(position);

        viewHolder.title.setText(item.getVodName());
        if (item.getVodBlurb() == null || item.getVodBlurb().isEmpty()) {
            viewHolder.blurb.setVisibility(View.GONE);
        } else {
            viewHolder.blurb.setVisibility(View.VISIBLE);
            viewHolder.blurb.setText(item.getVodBlurb());
        }
//        if (StringUtils.isEmpty(item.getVod_custom_tag())) {
//            viewHolder.tip.setVisibility(View.GONE);
//        } else {
//            viewHolder.tip.setVisibility(View.VISIBLE);
//            viewHolder.tip.setText(item.getVod_custom_tag());
//            viewHolder.tip.setBackgroundResource(AppColorUtils.getTagBgResId(item.getVod_custom_tag()));
//        }
        Pair<String, Integer> pair = AppColorUtils.getTagBgResId(position, item.getVod_custom_tag());
        if (pair.component1().isEmpty()) {
            viewHolder.tip.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tip.setVisibility(View.VISIBLE);
            viewHolder.tip.setText(pair.component1());
            viewHolder.tip.setBackgroundResource(pair.component2());
        }

        if (item.getType().getTypeName().equals("电影")) {
            viewHolder.up_title.setTextColor(ColorUtils.getColor(R.color.colorPrimary));
            TextPaint tp = viewHolder.up_title.getPaint();
            tp.setFakeBoldText(true);
            viewHolder.up_title.setText(item.getVod_score());
        } else {
            viewHolder.up_title.setTextColor(ColorUtils.getColor(R.color.white));
            viewHolder.up_title.setText(item.getVodRemarks());
            TextPaint tp = viewHolder.up_title.getPaint();
            tp.setFakeBoldText(false);
        }
        Glide.with(viewHolder.icon.getContext())
                .load(item.getVodPic())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.icon);
        return convertView;
    }

    class ViewHolder {
        private ImageView icon;
        private TextView tip;
        private TextView up_title;
        private TextView title;
        private TextView blurb;

    }
}
