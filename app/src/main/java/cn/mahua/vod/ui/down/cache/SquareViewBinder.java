package cn.mahua.vod.ui.down.cache;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Set;

import cn.mahua.vod.App;
import cn.mahua.vod.R;
import me.drakeet.multitype.ItemViewBinder;


import static java.lang.String.valueOf;

/**
 * @author drakeet
 */
public class SquareViewBinder extends ItemViewBinder<Square, SquareViewBinder.ViewHolder> {

    private final @NonNull
    Set<Integer> selectedSet;


    public SquareViewBinder(@NonNull Set<Integer> selectedSet) {
        this.selectedSet = selectedSet;
    }


    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_square, parent, false);
        return new ViewHolder(root);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Square square) {
        holder.square = square;
        holder.squareView.setText(valueOf(square.number));
        if (square.isSelected) {
            holder.statuTag.setImageResource(R.drawable.ic_cache_down);
            holder.statuTag.setVisibility(View.VISIBLE);
        }
        if (square.finished){
            holder.statuTag.setImageResource(R.drawable.ic_succeed);
            holder.statuTag.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (square.clickListener!=null && !square.finished && !square.isSelected){
                    square.clickListener.onClick(v);
                    holder.square.isSelected= true;
                    holder.statuTag.setVisibility(View.VISIBLE);
                    holder.statuTag.setImageResource(R.drawable.ic_cache_down);
                }else {
                    Toast.makeText(App.getApplication(), "当前节目已在缓存列表", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public @NonNull
    Set<Integer> getSelectedSet() {
        return selectedSet;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView squareView;
        private Square square;
        final ImageView statuTag;

        ViewHolder(final View itemView) {
            super(itemView);
            squareView = itemView.findViewById(R.id.square);
            statuTag = itemView.findViewById(R.id.status_tag);
        }
    }
}
