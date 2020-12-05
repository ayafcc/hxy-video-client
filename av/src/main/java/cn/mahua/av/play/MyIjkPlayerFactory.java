package cn.mahua.av.play;

import android.content.Context;

import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.AbstractPlayer;

public class MyIjkPlayerFactory extends IjkPlayerFactory {
    private Context mContext;

    public MyIjkPlayerFactory(Context context) {
        super(context);
        mContext = context.getApplicationContext();
    }

    @Override
    public AbstractPlayer createPlayer() {
        return new MyIjkPlayer(mContext);
    }
}
