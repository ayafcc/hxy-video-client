package com.sweetieplayer.android.upnpcast;

import com.sweetieplayer.android.upnpcast.controller.ICastControl;

import org.fourthline.cling.model.types.DeviceType;

/**
 */
public interface IUpnpCast extends ICastControl
{
    int DEFAULT_MAX_SECONDS = 60;

    void search();

    void search(DeviceType type, int maxSeconds);

    void clear();
}
