package jaygoo.library.m3u8downloader.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import jaygoo.library.m3u8downloader.M3U8Library;
import jaygoo.library.m3u8downloader.R;
import jaygoo.library.m3u8downloader.view.adapter.DownloadCenterPagerAdpter;

/**
 * @author huangyong
 * createTime 2019-09-27
 */
public class DownloadPageFragment extends Fragment {

    final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(M3U8Library.EVENT_REFRESH)) {
                if (downloadingItemList != null) {
                    downloadingItemList.refreshList();
                }
                if (downloadItemList != null) {
                    downloadItemList.refreshList();
                }
            }
        }
    };
    private DownloadingItemList downloadingItemList;
    private DownloadItemList downloadItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_page_layout, container, false);
        initView(view);
        IntentFilter intentFilter = new IntentFilter(M3U8Library.EVENT_REFRESH);
        if (getActivity() != null) {
            getActivity().registerReceiver(receiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }
        return view;
    }

    private void initView(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.tab_vp);
        ArrayList<Fragment> fragments = new ArrayList<>();
        downloadingItemList = new DownloadingItemList();
        downloadItemList = new DownloadItemList();
        fragments.add(downloadingItemList);
        fragments.add(downloadItemList);
        DownloadCenterPagerAdpter adpter = new DownloadCenterPagerAdpter(getChildFragmentManager(), fragments, getActivity());
        viewPager.setAdapter(adpter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        if (getActivity() != null) {
            getActivity().unregisterReceiver(receiver);
        }
        super.onDestroyView();
    }
}
