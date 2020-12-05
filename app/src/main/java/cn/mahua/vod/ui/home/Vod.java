package cn.mahua.vod.ui.home;

import android.os.Parcelable;

import cn.mahua.vod.bean.TypeBean;

public interface Vod extends Parcelable {

    String getVodName();

    String getVodBlurb();

    String getVodLang();

    String getVodRemarks();

    String getVodPic();

   String getVodPicThumb();

    String getVodPicSlide();

    String getVod_score();

    String getVod_custom_tag();

    int getVod_copyright();

    String getVod_jumpurl();

    TypeBean getType();

}
