package cn.mahua.vod.ui.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseItemClickListener2;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

@SuppressWarnings("unused")
public class ScreenClassItemViewBinder extends ItemViewBinder<Titles, ScreenClassItemViewBinder.ViewHolder> {

    private BaseItemClickListener2 mBaseItemClickListener;

    public ScreenClassItemViewBinder(BaseItemClickListener2 baseItemClickListener) {
        this.mBaseItemClickListener = baseItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_rv, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Titles item) {
        holder.setData(item.getTitles());
        if (mBaseItemClickListener != null) {
            holder.titleItemViewBinder.setBaseItemClickListener(mBaseItemClickListener);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerView recyclerView;
        private MultiTypeAdapter adapter;
        private TitleItemViewBinder titleItemViewBinder;
        private List<Title> items;

        ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new MultiTypeAdapter();
            titleItemViewBinder = new TitleItemViewBinder();
            adapter.register(Title.class, titleItemViewBinder);
            recyclerView.setAdapter(adapter);
        }

        private void setData(List<Title> list) {
            if (list == null) return;
            adapter.setItems(items = list);
            adapter.notifyDataSetChanged();
        }

    }

}
