package com.sweetieplayer.vod.ui.home;

import java.io.Serializable;

import com.sweetieplayer.vod.bean.ExtendBean;

public interface Type extends Serializable {

    String getTypeName();

    int getTypeId();

    ExtendBean getExtend();
}
