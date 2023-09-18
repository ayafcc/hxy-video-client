package com.sweetieplayer.vod.ui.home;

import android.graphics.Bitmap;

@SuppressWarnings("WeakerAccess")
public class TopBarEvent {

    private Bitmap TopBarBg;

    public Bitmap getTopBarBg() {
        return TopBarBg;
    }

    public TopBarEvent setTopBarBg(Bitmap topBarBg) {
        TopBarBg = topBarBg;
        return this;
    }

}
