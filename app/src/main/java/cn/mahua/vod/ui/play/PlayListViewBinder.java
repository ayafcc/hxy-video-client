package cn.mahua.vod.ui.play;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.av.widget.view.SortVodView;
import cn.mahua.vod.R;
import cn.mahua.vod.bean.PlayList;
import me.drakeet.multitype.ItemViewBinder;

public class PlayListViewBinder extends ItemViewBinder<PlayList, PlayListViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new PlayListViewBinder.ViewHolder(inflater.inflate(R.layout.item_playlist, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PlayList item) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView hits;
        final TextView score;
        final SortVodView sortVodView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_tv_playinfo_title);
            hits = itemView.findViewById(R.id.item_tv_playinfo_hits);
            score = itemView.findViewById(R.id.item_tv_playinfo_score);
            sortVodView = itemView.findViewById(R.id.item_svv_playinfo);
        }
    }
}
