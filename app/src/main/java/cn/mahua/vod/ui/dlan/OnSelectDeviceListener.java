package cn.mahua.vod.ui.dlan;


import com.liuwei.android.upnpcast.device.CastDevice;

import java.util.ArrayList;

import cn.mahua.vod.download.FileInfo;

/**
 * 作者：朱亮 on 2017/1/17 16:16
 * 邮箱：171422696@qq.com
 * 传递下载进度接口(这里用一句话描述这个方法的作用)
 */

public interface OnSelectDeviceListener {
     void onDownloadSize(CastDevice device);
}
