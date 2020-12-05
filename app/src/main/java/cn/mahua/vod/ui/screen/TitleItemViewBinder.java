package cn.mahua.vod.ui.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseItemClickListener2;
import cn.mahua.vod.bean.RefreshEvent;
import me.drakeet.multitype.ItemViewBinder;

@SuppressWarnings("unused")
public class TitleItemViewBinder extends ItemViewBinder<Title, TitleItemViewBinder.ViewHolder> implements View.OnClickListener {

    private BaseItemClickListener2 baseItemClickListener;
    private Titles titles;

    public void setBaseItemClickListener(BaseItemClickListener2 baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
    }


    public void setData(Titles titles) {
        this.titles = titles;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_tv, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Title item) {
        holder.itemView.setTag(R.id.itemData, item);
        holder.itemView.setTag(R.id.itemSelected, getPosition(holder));
        holder.itemView.setOnClickListener(this);

        TextView textView = (TextView) holder.itemView;
        textView.setText(item.getTitle());

        if (item.isSelected()) {
            textView.setBackgroundResource(R.drawable.bg_yellow);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorAccent));
        } else {
            textView.setBackground(null);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.gray_999));
        }

    }


    @Override
    public void onClick(View view) {
        Object o = view.getTag(R.id.itemData);
        int pos = (int)view.getTag(R.id.itemSelected);
        if (titles != null) {
            titles.setCurTitle((Title) o);
        }
        EventBus.getDefault().postSticky(new RefreshEvent());

        if (o != null && baseItemClickListener != null) {
            baseItemClickListener.onClickItem(view, o,pos);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
