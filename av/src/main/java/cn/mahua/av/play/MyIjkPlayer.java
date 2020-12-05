package cn.mahua.av.play;

import android.content.Context;

import com.dueeeke.videoplayer.ijk.IjkPlayer;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MyIjkPlayer extends IjkPlayer {
    public MyIjkPlayer(Context context) {
        super(context);
    }

    @Override
    public void setOptions() {
        super.setOptions();
        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC,"skip_loop_filter",48L);
        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT,"analyzeduration",1);
        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT,"probesize",1024*10);
        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"packet-buffering",1);
        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"max-buffer-size",512);
    }
}
