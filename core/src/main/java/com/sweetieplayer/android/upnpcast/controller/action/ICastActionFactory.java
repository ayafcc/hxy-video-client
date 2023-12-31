package com.sweetieplayer.android.upnpcast.controller.action;

import com.sweetieplayer.android.upnpcast.NLUpnpCastManager;
import com.sweetieplayer.android.upnpcast.controller.action.IAVServiceActionFactory.AvServiceActionFactory;
import com.sweetieplayer.android.upnpcast.controller.action.IRenderServiceActionFactory.RenderServiceActionFactory;
import com.sweetieplayer.android.upnpcast.device.CastDevice;

/**
 */
public interface ICastActionFactory
{
    IAVServiceActionFactory getAvService();

    IRenderServiceActionFactory getRenderService();

    // ------------------------------------------------------------------------------------------
    // Implement
    // ------------------------------------------------------------------------------------------
    class CastActionFactory implements ICastActionFactory
    {
        private final IAVServiceActionFactory mAvService;

        private final IRenderServiceActionFactory mRenderService;

        public CastActionFactory(CastDevice castDevice)
        {
            mAvService = new AvServiceActionFactory(castDevice.getDevice().findService(NLUpnpCastManager.SERVICE_AV_TRANSPORT));

            mRenderService = new RenderServiceActionFactory(castDevice.getDevice().findService(NLUpnpCastManager.SERVICE_RENDERING_CONTROL));
        }

        @Override
        public IAVServiceActionFactory getAvService()
        {
            return mAvService;
        }

        @Override
        public IRenderServiceActionFactory getRenderService()
        {
            return mRenderService;
        }
    }
}
