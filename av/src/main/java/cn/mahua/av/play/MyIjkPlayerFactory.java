package cn.mahua.av.play;

import android.content.Context;
import xyz.doikki.videoplayer.ijk.IjkPlayer;
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;


public class MyIjkPlayerFactory extends IjkPlayerFactory {
    private Context mContext;

    @Override
    public IjkPlayer createPlayer(Context context) {
        mContext = context.getApplicationContext();
        return new IjkPlayer(context);
    }
}
