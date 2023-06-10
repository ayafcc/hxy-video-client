package cn.mahua.av.play;

import android.content.Context;

import com.dueeeke.videoplayer.ijk.IjkPlayer;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;


public class MyIjkPlayerFactory extends IjkPlayerFactory {
    private Context mContext;

    @Override
    public IjkPlayer createPlayer(Context context) {
        mContext = context.getApplicationContext();
        return new IjkPlayer(context);
    }
}
