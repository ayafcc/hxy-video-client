package com.liuwei.android.upnpcast.device;


import androidx.annotation.NonNull;

import org.fourthline.cling.model.meta.Device;

/**
 */
public interface ICastDevice<T extends Device>
{
    T getDevice();

    @NonNull
    String getId();

    String getName();

    String getDescription();
}
