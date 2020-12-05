package cn.mahua.vod.ui.specialtopic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.mahua.vod.R;
import cn.mahua.vod.bean.SpecialtTopicBean;
import cn.mahua.vod.utils.SimpleUtils;

public class SpecialtopicAdpter extends BaseAdapter {
    private List<SpecialtTopicBean> coll;// 消息对象数组
    private LayoutInflater mInflater;
    private Context context;

    public SpecialtopicAdpter(Context context, List<SpecialtTopicBean> coll) {
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

    public void clearData(){
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
            convertView = mInflater.inflate(R.layout.specialtopic_item, null);
            viewHolder = new ViewHolder();
            viewHolder.coverImg = (ImageView)convertView.findViewById(R.id.topic_cover);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.topic_name);
            viewHolder.tvIntro = (TextView) convertView.findViewById(R.id.topic_info);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            SimpleUtils.setImageLoading(context,viewHolder.coverImg, entity.getTopic_pic(), R.drawable.topica);
            viewHolder.tvName.setText(entity.getTopic_name());
            String content = entity.getTopic_content().replace("<p>", "").replace("</p>", "");
            viewHolder.tvIntro.setText(content);
        return convertView;
    }

    class ViewHolder {
        public TextView tvName;
        public TextView tvIntro;
        public ImageView coverImg;
    }
}
