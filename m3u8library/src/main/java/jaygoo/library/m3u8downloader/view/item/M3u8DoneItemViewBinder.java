package jaygoo.library.m3u8downloader.view.item;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;

import jaygoo.library.m3u8downloader.M3U8Downloader;
import jaygoo.library.m3u8downloader.M3U8Library;
import jaygoo.library.m3u8downloader.OnTaskMergeListener;
import jaygoo.library.m3u8downloader.R;
import jaygoo.library.m3u8downloader.WeakHandler;
import jaygoo.library.m3u8downloader.bean.M3U8;
import jaygoo.library.m3u8downloader.bean.M3U8TaskState;
import jaygoo.library.m3u8downloader.utils.FileUtils;
import jaygoo.library.m3u8downloader.utils.MUtils;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-28
 */
public class M3u8DoneItemViewBinder extends ItemViewBinder<M3u8DoneItem, M3u8DoneItemViewBinder.ViewHolder> {
    private ViewHolder holders;
    private static final int WHAT_ON_success = 1001;
    private final WeakHandler dHandler = new WeakHandler(new Handler.Callback(){

        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case WHAT_ON_success:
                    Log.d("merage", "异常2：回调成功" );
                    holders.itemState.setText("已完成");
                    break;
                default:
                    break;
            }
            return true;
        }
    });
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_m3u8_done_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final M3u8DoneItem m3u8DoneItem) {
        String  path=M3U8Downloader.getInstance().getM3U8Path(m3u8DoneItem.getM3u8DoneInfo().getTaskData());
        holder.itemTitle.setText(m3u8DoneItem.getM3u8DoneInfo().getTaskName());
        String json = path.substring(0, path.lastIndexOf(File.separator))+"/json.txt";
        File file = new File(json);
        this.holders = holder;
        String read = FileUtils.readFile2Text(file);
        try {
            M3U8 m3U8 = new Gson().fromJson(read, M3U8.class);
            if(m3U8.getmerage()){
                holder.itemState.setText("已完成");
            }else{
                holder.itemState.setText("视频正在合并中...,切勿关闭软件");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("merage", "异常：" + e.getMessage());
            holder.itemState.setText("已完成");
        }
        //holder.itemState.setText("已完成");
        Glide.with(holder.itemView.getContext()).load(m3u8DoneItem.getM3u8DoneInfo().getTaskPoster()).into(holder.icon);
        MUtils.mergelist( new OnTaskMergeListener(){

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Throwable errorMsg) {

            }

            @Override
            public void onSuccess(M3U8 m3U8) {
                Log.d("merage", "异常：回调成功" );
                dHandler.sendEmptyMessage(WHAT_ON_success);
                //holder.itemState.setText("已完成");
            }

            @Override
            public void onProgress(M3U8 m3U8) {
                //holder.itemState.setText("视频正在合并中...,切勿关闭软件");
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  path=M3U8Downloader.getInstance().getM3U8Path(m3u8DoneItem.getM3u8DoneInfo().getTaskData());
                Log.d("M3u8DoneItemViewBinder", path+"111111111");
                Log.d("M3u8DoneItemViewBinder", m3u8DoneItem.getM3u8DoneInfo().getTaskData());
                Log.d("M3u8DoneItemViewBinder", m3u8DoneItem.getM3u8DoneInfo()+"");
                path=path.substring(0, path.lastIndexOf(File.separator));
//                if (M3U8Downloader.getInstance().checkM3U8IsExist(path)) {

                    Log.d("M3u8DoneItemViewBinder", path+"__");


//                    Intent intent = new Intent(holder.itemView.getContext(), StorePlayActivity.class);
                    Intent intent = new Intent();
                    intent.setClassName(holder.itemView.getContext(), "cn.mahua.vod.ui.play.StorePlayActivity");
                    intent.putExtra("play_url",path);
                    intent.putExtra("play_name",m3u8DoneItem.getM3u8DoneInfo().getTaskName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.itemView.getContext().startActivity(intent);
////                    intent.putExtra(DataInter.Key.KEY_CURRENTPLAY_URL, M3U8Downloader.getInstance().getM3U8Path(m3u8DoneItem.getM3u8DoneInfo().getTaskData()));
////                    intent.putExtra(DataInter.Key.KEY_CURRENTPLAY_TITLE, m3u8DoneItem.getM3u8DoneInfo().getTaskName());
//                    holder.itemView.getContext().startActivity(intent);
//                    Toast.makeText( holder.itemView.getContext(), "播放本地文件", Toast.LENGTH_SHORT).show();
//                    TODO  播放
//                } else {
//                    Toast.makeText(M3U8Library.getContext(), "未发现播放文件，删除了？", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (m3u8DoneItem.clickListener != null) {
                    m3u8DoneItem.clickListener.onLongClick(m3u8DoneItem, getAdapter().getItems().indexOf(m3u8DoneItem));
                }
                return true;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView itemState;
        final TextView itemTitle;
        final ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.m3u8_item_icon);
            itemTitle = itemView.findViewById(R.id.m3u8_title);
            itemState = itemView.findViewById(R.id.m3u8_state);
        }
    }

    public interface OnItemListener {
        void onLongClick(M3u8DoneItem m3u8DoneItem, int doneItem);
    }
}
