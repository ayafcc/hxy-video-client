package com.sweetieplayer.android.upnpcast.controller;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sweetieplayer.android.upnpcast.device.CastDevice;

import org.fourthline.cling.support.model.MediaInfo;
import org.fourthline.cling.support.model.TransportInfo;

/**
 */
public interface ICastEventListener extends ICastControlListener
{
    void onConnecting(@NonNull CastDevice castDevice);

    void onConnected(@NonNull CastDevice castDevice, @NonNull TransportInfo transportInfo, @Nullable MediaInfo mediaInfo, int volume);

    void onDisconnect();
}
