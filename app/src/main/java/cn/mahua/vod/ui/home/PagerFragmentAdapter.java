package cn.mahua.vod.ui.home;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

@SuppressWarnings("WeakerAccess")
public class PagerFragmentAdapter extends FragmentStatePagerAdapter {

    private String firstName;
    private List<Type> mTitles = new ArrayList<>();

    public PagerFragmentAdapter(FragmentManager fm, @NonNull String name) {
        super(fm);
        firstName = name;
    }

    public void addData(List<? extends Type> types) {
        if (types == null || types.size() <= 0) return;
        mTitles.addAll(types);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HomeFirstChildFragment2.newInstance(position, firstName);
        } else {
            return HomeOtherChildFragment.newInstance(position, mTitles.get(position - 1));
        }
    }

    @Override
    public int getCount() {
        return mTitles.size() + 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return firstName;
        } else {
            return mTitles.get(position - 1).getTypeName();
        }
    }
}
