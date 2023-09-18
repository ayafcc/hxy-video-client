package com.sweetieplayer.vod.ui.specialtopic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.sweetieplayer.vod.R;
import com.sweetieplayer.vod.bean.SpecialtTopicBean;
import com.sweetieplayer.vod.utils.CornerTransform;
import com.sweetieplayer.vod.utils.DensityUtils;

public class SpecialtopicAdpter2 extends BaseAdapter {
    private final List<SpecialtTopicBean> coll;// 消息对象数组
    private final LayoutInflater mInflater;
    private final Context context;

    public SpecialtopicAdpter2(Context context, List<SpecialtTopicBean> coll) {
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

    public void refresh(List<SpecialtTopicBean> data) {
        reset(data);
        notifyDataSetChanged();
    }

    public void reset(List<SpecialtTopicBean> data) {
        if (!data.isEmpty()) {
            this.coll.clear();
            for (int i = 0; i < data.size(); i++) {
                this.coll.add(data.get(i));
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        SpecialtTopicBean entity = coll.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.specialtopic_item2, null);
            viewHolder = new ViewHolder();
            viewHolder.coverImg = convertView.findViewById(R.id.topic_cover);
            viewHolder.tvName = convertView.findViewById(R.id.topic_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CornerTransform transformation = new CornerTransform(context, DensityUtils.INSTANCE.dp2px(context, 10));
//只是绘制左上角和右上角圆角
        transformation.setExceptCorner(false, false, false, false);

        Glide.with(context)
                .load(entity.getTopic_pic())
                .skipMemoryCache(true)
                .transform(transformation)
                .into(viewHolder.coverImg);

        viewHolder.tvName.setText(entity.getTopic_name());
        return convertView;
    }

    class ViewHolder {
        public TextView tvName;
        public ImageView coverImg;
    }
}
