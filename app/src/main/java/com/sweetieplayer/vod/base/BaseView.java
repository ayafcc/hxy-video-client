package com.sweetieplayer.vod.base;

public interface BaseView<T> {

    void initView();

    void setPresenter(T presenter);

}
