package cn.mahua.vod.ui.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.mahua.vod.R;
import cn.mahua.vod.bean.TaskBean;
import me.drakeet.multitype.ItemViewBinder;

@SuppressWarnings("unused")
public class TaskItemViewBinder extends ItemViewBinder<TaskBean, TaskItemViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_task, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TaskBean item) {
//        holder.textView1.setText(item.getT1());
//        holder.textView2.setText(item.getT2());
//        holder.textView3.setText(item.getT3());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private @NonNull
        final TextView textView1;

        private @NonNull
        final TextView textView2;

        private @NonNull
        final TextView textView3;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.item_tv_task_t1);
            textView2 = itemView.findViewById(R.id.item_tv_task_t2);
            textView3 = itemView.findViewById(R.id.item_tv_task_t3);
        }

    }

}

