package com.sweetieplayer.vod.ui.dlan;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sweetieplayer.android.upnpcast.device.CastDevice;

import com.sweetieplayer.vod.R;


/**
 */
public class DeviceHolder extends RecyclerView.ViewHolder implements OnClickListener, OnCheckedChangeListener
{
    private final TextView name;
    private final TextView description;
    private final CheckBox selector;
    private final DeviceAdapter.OnItemSelectedListener mOnItemSelectedListener;
    private CastDevice mCastDevice;
    private boolean mBinding = false;

    DeviceHolder(final View itemView, DeviceAdapter.OnItemSelectedListener listener)
    {
        super(itemView);

        mOnItemSelectedListener = listener;

        itemView.setOnClickListener(this);
        name = itemView.findViewById(R.id.device_name);
        description = itemView.findViewById(R.id.device_description);
        selector = itemView.findViewById(R.id.device_selector);
        selector.setOnCheckedChangeListener(this);
    }

    public void setData(CastDevice castDevice, boolean isSelected)
    {
        mBinding = true;
        mCastDevice = castDevice;
        name.setText(castDevice.getName());
        description.setText(castDevice.getDescription());
        selector.setChecked(isSelected);
        mBinding = false;
    }

    @Override
    public void onClick(View v)
    {
        selector.setChecked(!selector.isChecked());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (mOnItemSelectedListener != null && !mBinding)
        {
            mOnItemSelectedListener.onItemSelected(mCastDevice, isChecked);
        }
    }
}
