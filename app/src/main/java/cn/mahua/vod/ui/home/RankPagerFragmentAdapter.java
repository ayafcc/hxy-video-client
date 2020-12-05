package cn.mahua.vod.ui.home;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import cn.mahua.vod.bean.TypeBean;

@SuppressWarnings("WeakerAccess")
public class RankPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private List<TypeBean> mDataList = new ArrayList<>();

    public RankPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addData(List<TypeBean> types) {
        if (types == null || types.size() <= 0) return;
        mDataList.clear();
        mDataList.addAll(types);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return RankChildFragment.newInstance(mDataList.get(position).getType_id());
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mDataList.isEmpty()) {
            return "";
        } else {
            return mDataList.get(position).getTypeName();
        }
    }
}
