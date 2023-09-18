package com.sweetieplayer.vod.ui.seek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sweetieplayer.vod.R;
import com.sweetieplayer.vod.base.BaseItemClickListener;
import me.drakeet.multitype.ItemViewBinder;

@SuppressWarnings("unused")
public class SeekHotItemViewBinder extends ItemViewBinder<String, SeekHotItemViewBinder.ViewHolder> implements View.OnClickListener {

    private BaseItemClickListener mBaseItemClickListener;

    public void setBaseItemClickListener(BaseItemClickListener baseItemClickListener) {
        this.mBaseItemClickListener = baseItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_seek_hot, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item) {
        int p = getPosition(holder);
        TextView textView = holder.itemView.findViewById(R.id.tv);
        TextView tvSort = holder.itemView.findViewById(R.id.tvSort);
        textView.setTag(R.id.itemData,item);
        textView.setOnClickListener(this);
        if(p == 0){
            tvSort.setTextColor(ColorUtils.getColor(R.color.red));
        }else if( p == 1){
            tvSort.setTextColor(ColorUtils.getColor(R.color.colorAccent));
        }else if(p == 2){
            tvSort.setTextColor(ColorUtils.getColor(R.color.yellow));
        }else{
            tvSort.setTextColor(ColorUtils.getColor(R.color.gray_999));
        }
        tvSort.setText((p+1)+"");
        textView.setText(item);
    }

    @Override
    public void onClick(View view) {
        if(mBaseItemClickListener != null){
            String s = view.getTag(R.id.itemData).toString();
            mBaseItemClickListener.onClickItem(view,s);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

