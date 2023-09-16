package cn.mahua.vod.ui.dlan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liuwei.android.upnpcast.NLUpnpCastManager;
import com.liuwei.android.upnpcast.controller.CastObject;
import com.lxj.xpopup.core.CenterPopupView;

import cn.mahua.vod.R;
import cn.mahua.vod.ui.play.CastScreenActivity2;


/**
 * @author huangyong
 * createTime 2019-10-05
 */
public class DlanListPop extends CenterPopupView {


    private RecyclerView list;
    private View cancle;
    private View help;




    private DeviceAdapter mDeviceAdapter;
    final Context mContext;
    OnSelectDeviceListener onSelectDeviceListener;



    public DlanListPop(@NonNull Context context,OnSelectDeviceListener mOnClickListener) {
        super(context);

        this.mContext = context;
        this.onSelectDeviceListener = mOnClickListener;


    }

    @Override
    protected void onCreate() {
        super.onCreate();

        list = findViewById(R.id.device_list);
        cancle = findViewById(R.id.dlan_to_cancel);
        help = findViewById(R.id.dlan_to_help);
        mDeviceAdapter = new DeviceAdapter((Activity) mContext, mOnClickListener);
        NLUpnpCastManager.getInstance().clear(); //TODO, need clear first?

        //NLUpnpCastManager.getInstance().search();
        NLUpnpCastManager.getInstance().bindUpnpCastService((Activity) mContext);
        NLUpnpCastManager.getInstance().search(NLUpnpCastManager.DEVICE_TYPE_DMR, 60);
        NLUpnpCastManager.getInstance().addRegistryDeviceListener(mDeviceAdapter);


        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(mDeviceAdapter);

        cancle.setOnClickListener(v -> {
            dismiss();
            mDeviceAdapter.setSelectedDevice(null);

            NLUpnpCastManager.getInstance().disconnect();
        });
//        help.setOnClickListener(v -> {
//            Intent airPlay = new Intent();
//            if (getContext() != null) {
//                airPlay.setClass(getContext(), CastScreenActivity2.class);
////                airPlay.setClassName(getContext(), "com.movtalent.app.view.CastDescriptionnActivity");
//
//            }
//        });


    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dlan_ui_device_pop_layout;
    }


    public final DeviceAdapter.OnItemSelectedListener mOnClickListener = (castDevice, selected) -> {
        if (selected) {
            mDeviceAdapter.setSelectedDevice(castDevice);
//
            NLUpnpCastManager.getInstance().connect(castDevice);
//
//            NLUpnpCastManager.getInstance().cast(
//                    CastObject
//                            .newInstance(Constants.CAST_URL, Constants.CAST_ID, Constants.CAST_NAME)
//                            .setDuration(Constants.CAST_VIDEO_DURATION)
//            );
            onSelectDeviceListener.onDownloadSize(castDevice);
            dismiss();



        } else {
            mDeviceAdapter.setSelectedDevice(null);

//            NLUpnpCastManager.getInstance().disconnect();


        }
    };

    @Override
    public void dismiss() {
        super.dismiss();
        // getContext().unbindService(mUpnpServiceConnection);
    }
}
