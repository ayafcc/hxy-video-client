package com.sweetieplayer.vod.ui.dlan;

import android.widget.SeekBar;

public interface DlanListener {

    void onLinkListener();


    void onVolume(float deltaY, int volumePercent);

    void onProgress(SeekBar seekBar, int progress, boolean fromUser);

    void onBrightness(float percent);


}
