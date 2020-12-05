package cn.mahua.vod.utils;


import com.orhanobut.hawk.Hawk;

import cn.mahua.vod.bean.SaveLiveDate;

/**
 * 数据持久化（代替SharedPreferences）
 */
public class HawkHelper {

    private static HawkHelper hawkHelper;

    public static HawkHelper getInstance() {
        if (hawkHelper == null) {
            hawkHelper = new HawkHelper();
        }
        return hawkHelper;
    }


    private static final String LIVE_DATA = "LIVE_DATA";


    public void putLiveDate(SaveLiveDate saveLiveDate){
        Hawk.put(LIVE_DATA,saveLiveDate);
    }

    public SaveLiveDate getLiveDate(){
        SaveLiveDate saveLiveDate = Hawk.get(LIVE_DATA,null);
        return saveLiveDate;
    }
}
