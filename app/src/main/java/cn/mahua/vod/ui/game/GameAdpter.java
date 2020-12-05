package cn.mahua.vod.ui.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.mahua.vod.R;
import cn.mahua.vod.bean.GameBean;
import cn.mahua.vod.utils.SimpleUtils;

public class GameAdpter extends BaseAdapter {
    private List<GameBean> coll;// 消息对象数组
    private LayoutInflater mInflater;
    private Context context;

    public GameAdpter(Context context, List<GameBean> coll) {
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

    public void refresh(List<GameBean> data) {
        reset(data);
        notifyDataSetChanged();
    }

    public void reset(List<GameBean> data) {
        if (!data.isEmpty()) {
            this.coll.clear();
            for (int i = 0; i < data.size(); i++) {
                this.coll.add(data.get(i));
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        GameBean entity = coll.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.game_item, null);
            viewHolder = new ViewHolder();
            viewHolder.coverImg = (ImageView)convertView.findViewById(R.id.game_cover);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SimpleUtils.setImageLoading(context,viewHolder.coverImg, entity.getImg(), R.drawable.topica);

        return convertView;
    }

    class ViewHolder {
        public TextView tvGoodsName;
        public TextView tvShopName;
        public TextView tvPrice;
        public ImageView coverImg;
    }
}
