package cn.mahua.vod.ui.filtrate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseItemClickListener;
import cn.mahua.vod.bean.UpdateEvent;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.ui.home.MyDividerItemDecoration;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

import static net.lucode.hackware.magicindicator.ScrollState.SCROLL_STATE_IDLE;

@SuppressWarnings("unused")
public class FiltrateItemViewBinder extends ItemViewBinder<FiltrateResult, FiltrateItemViewBinder.ViewHolder> {

    private BaseItemClickListener baseItemClickListener;

    public void setBaseItemClickListener(BaseItemClickListener baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_filtrate, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull FiltrateResult item) {
        holder.setData(item.getList());
        holder.vodItemViewBinder.setBaseItemClickListener(baseItemClickListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private MultiTypeAdapter adapter;
        private VodItemViewBinder vodItemViewBinder;

        ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(),3);
            MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(itemView.getContext(), RecyclerView.HORIZONTAL, false);
            dividerItemDecoration.setDrawable(itemView.getContext().getResources().getDrawable(R.drawable.divider_image));
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(gridLayoutManager);
            adapter = new MultiTypeAdapter();
            vodItemViewBinder = new VodItemViewBinder();
            adapter.register(VodBean.class, vodItemViewBinder);
            recyclerView.setAdapter(adapter);

            //滑动加载
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
                    if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
                        EventBus.getDefault().postSticky(new UpdateEvent(false));
                        adapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
                    } else {
                        EventBus.getDefault().postSticky(new UpdateEvent(true));
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }

        public void setData(List<?> list) {
            if (list == null) return;
            adapter.setItems(list);
            adapter.notifyDataSetChanged();
        }
    }
}
