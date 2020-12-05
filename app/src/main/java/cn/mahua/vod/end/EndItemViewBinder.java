//package cn.mahua.vod.end;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import cn.mahua.vod.R;
//import cn.mahua.vod.bean.EndBean;
//import me.drakeet.multitype.ItemViewBinder;
//
//public class EndItemViewBinder extends ItemViewBinder<EndBean, EndItemViewBinder.ViewHolder> {
//
//    @NonNull
//    @Override
//    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
//        return new ViewHolder(inflater.inflate(R.layout.item_end, parent, false));
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EndBean item) {
//        holder.textView.setText(item.getText());
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        private @NonNull
//        final TextView textView;
//
//        ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(R.id.item_tv_end);
//        }
//    }
//
//}
