package cn.mahua.vod.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseMainFragment extends SupportFragment {

    private OnBackToFirstListener _mBackToFirstListener;

    private Unbinder unbinder;

    @LayoutRes
    protected abstract int getLayoutResID();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResID(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        LogUtils.e("onDestroyView:-" + this.toString());
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnBackToFirstListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (this instanceof BackMainFragment) {   // 如果是 第一个Fragment 则退出app
                ActivityCompat.finishAfterTransition(_mActivity);
            } else {                                    // 如果不是,则回到第一个Fragment
                _mBackToFirstListener.onBackToFirstFragment();
            }
        }
        return true;
    }

    @SuppressWarnings("unused")
    public OnBackToFirstListener getBackToFirstListener() {
        return this._mBackToFirstListener;
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }

}
