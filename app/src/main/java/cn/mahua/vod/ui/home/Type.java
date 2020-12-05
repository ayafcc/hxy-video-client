package cn.mahua.vod.ui.home;

import java.io.Serializable;

import cn.mahua.vod.bean.ExtendBean;

public interface Type extends Serializable {

    String getTypeName();

    int getTypeId();

    ExtendBean getExtend();
}
