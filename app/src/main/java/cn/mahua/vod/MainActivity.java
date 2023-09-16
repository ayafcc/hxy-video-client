package cn.mahua.vod;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.StormWyrm.wanandroid.base.exception.ResponseException;
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver;
import com.github.StormWyrm.wanandroid.base.sheduler.IoMainScheduler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.base.BaseMainFragment;
import cn.mahua.vod.bean.AppUpdateBean;
import cn.mahua.vod.bean.BaseResult;
import cn.mahua.vod.bean.OpenRecommendEvent;
import cn.mahua.vod.bean.StartBean;
import cn.mahua.vod.bean.TitleEvent;
import cn.mahua.vod.netservice.VodService;
import cn.mahua.vod.ui.home.HomeFragment;
import cn.mahua.vod.ui.live.LiveFragment;
import cn.mahua.vod.ui.rank.RankFragment;
import cn.mahua.vod.ui.share.ShareFragment;
import cn.mahua.vod.ui.specialtopic.SpecialtTopicFragment;
import cn.mahua.vod.ui.user.UserFragment;
import cn.mahua.vod.ui.widget.AppUpdateDialog;
import cn.mahua.vod.ui.widget.NoticeDialog2;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.Retrofit2Utils;
import me.yokeyword.fragmentation.SupportFragment;



public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, BaseMainFragment.OnBackToFirstListener {

    @BindView(R.id.bnv_main)
    BottomNavigationView bnv_main;

    public static final int HOME = 0;
    public static final int TOPIC = 1;
    public static final int RANK = 2;
    public static final int LIVE = 3;
    public static final int USER = 4;
    private final SupportFragment[] mFragments = new SupportFragment[6];
    final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};
    final List<String> mPermissionList = new ArrayList<>();
    private static final int PERMISSION_REQUEST = 1;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        //setContentView(R.layout.activity_main);
        //mAdviceLayout = findViewById(R.id.advertLayout);
        bnv_main.setBackgroundColor(Color.parseColor("#ffffff"));


        //初始化
        SupportFragment firstFragment = findFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[HOME] = HomeFragment.newInstance();
//              mFragments[TOPIC] = ShareFragment.newInstance();
            mFragments[LIVE] = LiveFragment.newInstance();
            mFragments[RANK] = RankFragment.newInstance();
            mFragments[USER] = UserFragment.newInstance();
            mFragments[TOPIC] = SpecialtTopicFragment.newInstance();
//            mFragments[GAME] = GameFragment2.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            showFragment(R.id.fl_main_container, mFragments[USER], transaction);
            showFragment(R.id.fl_main_container, mFragments[TOPIC], transaction);
            showFragment(R.id.fl_main_container, mFragments[RANK], transaction);
            showFragment(R.id.fl_main_container, mFragments[LIVE], transaction);
//            showFragment(R.id.fl_main_container, mFragments[TOPIC], transaction);
            showFragment(R.id.fl_main_container, mFragments[HOME], transaction);
            transaction.commit();

//            loadMultipleRootFragment(R.id.fl_main_container, HOME, mFragments[HOME], mFragments[LIVE], mFragments[USER], mFragments[TOPIC], mFragments[GAME]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
//              mFragments[TOPIC] = findFragment(ShareFragment.class);
            mFragments[HOME] = firstFragment;
            mFragments[TOPIC] = findFragment(SpecialtTopicFragment.class);
//            mFragments[GAME] = findFragment(GameFragment2.class);
            mFragments[LIVE] = findFragment(LiveFragment.class);
            mFragments[RANK] = findFragment(RankFragment.class);
            mFragments[USER] = findFragment(UserFragment.class);
        }
        initPermission();
        initView();
        showNotice();
        checkVersion();
//        getTabThreeName();
//        getTabFourInfo();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initPermission() {
        mPermissionList.clear();
        /**
         * 判断哪些权限未授予
         */
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST);
        }
    }

    /**
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                break;
            default:
                break;
        }
    }

    private void addAllFragment(SupportFragment[] mFragments, FragmentTransaction transaction, int layoutId) {
        for (SupportFragment fragment : mFragments) {
            if (fragment != null) {
                transaction.add(layoutId, fragment);
            }
        }
    }

    private void showFragment(int layoutId, SupportFragment fragment, FragmentTransaction transaction) {
        if (fragment != null) {
            transaction.add(layoutId, fragment);
        }
    }

    private void hideAllFragment(SupportFragment[] mFragments, FragmentTransaction transaction) {
        for (SupportFragment fragment : mFragments) {
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
    }

    @Override
    protected void initView() {
        bnv_main.setItemIconTintList(null);
        bnv_main.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackToFirstFragment() {
        bnv_main.setSelectedItemId(R.id.navigation_main_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_main_home:
                showHideFragment(mFragments[HOME]);
                return true;

            case R.id.navigation_main_topic:
                showHideFragment(mFragments[TOPIC]);
                return true;

            case R.id.navigation_main_rank:
                showHideFragment(mFragments[RANK]);
                return true;

            case R.id.navigation_main_live:
                showHideFragment(mFragments[LIVE]);
                return true;

//            case R.id.navigation_main_game:
//                showHideFragment(mFragments[GAME]);
//                mFragments[GAME].setUserVisibleHint(true);
//                return true;
//            case R.id.navigation_main_share:
//                if (!UserUtils.isLogin()) {
//                    LoginActivity.Companion.start();
//                    return false;
//                } else {
//                    showHideFragment(mFragments[TOPIC]);
//                    return true;
//                }
            case R.id.navigation_main_user:
                showHideFragment(mFragments[USER]);
                mFragments[USER].setUserVisibleHint(true);
                return true;
            default:
                return false;
        }
    }

    private void showNotice() {
        StartBean startBean = App.startBean;
        if (startBean != null) {
            StartBean.Document document = startBean.getDocument();
            if (document != null) {
                StartBean.Register registerd = document.getRegisterd();
                if (registerd != null && registerd.getStatus().equals("1")) {
                    new NoticeDialog2(mActivity, registerd.getContent())
                            .show();
                }
            }
        }
//        new NoticeDialog(mActivity,"1. 修改了首页 http://www.baidu.com \n2.添加了游戏功能 \n3.添加了专题功能 \n4.修改了bug1.\n1 修改了首页 \n2.添加了游戏功能 \n3.添加了专题功能 \n4.修改了bug\n1 修改了首页 \n2.添加了游戏功能 \n3.添加了专题功能 \n4.修改了bug")
//                .show();
    }

    private void checkVersion() {
//        AppUpdateBean baen = new AppUpdateBean();
//        baen.setSummary("1. 修改了首页 \n2.添加了游戏功能 \n3.添加了专题功能 \n4.修改了bug1.\n1 修改了首页 \n2.添加了游戏功能 \n3.添加了专题功能 \n4.修改了bug\n1 修改了首页 \n2.添加了游戏功能 \n3.添加了专题功能 \n4.修改了bug");
//       new AppUpdateDialog(mActivity, baen).show();
//
//        return;
        VodService vodService = Retrofit2Utils.INSTANCE.createByGson(VodService.class);
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        vodService
                .checkVersion("v" + AppUtils.getAppVersionName(), 1 + "")
                .compose(new IoMainScheduler<>())
                .subscribe(new BaseObserver<BaseResult<AppUpdateBean>>(true) {

                    @Override
                    public void onError(@NotNull ResponseException e) {

                    }

                    @Override
                    public void onSuccess(BaseResult<AppUpdateBean> data) {
                        if (data.getData() != null) {
                            new AppUpdateDialog(mActivity, data.getData())
                                    .show();
                        }
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onOpenRecommendEvent(OpenRecommendEvent event) {
        showHideFragment(mFragments[HOME]);
        bnv_main.setSelectedItemId(R.id.navigation_main_home);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (System.currentTimeMillis() - startTime <= 5000) {
                super.onBackPressedSupport();
            } else {
                startTime = System.currentTimeMillis();
                ToastUtils.showShort("再按一次退出程序");
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private Long startTime = 0L;

    private void getTabThreeName() {
        VodService vodService = Retrofit2Utils.INSTANCE.createByGson(VodService.class);
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        vodService.getTabThreeName()
                .compose(new IoMainScheduler<>())
                .subscribe(new BaseObserver<BaseResult<String>>(true) {

                    @Override
                    public void onError(@NotNull ResponseException e) {

                    }

                    @Override
                    public void onSuccess(BaseResult<String> data) {
                        if (data.getData().equals("0")) {
                            ((BottomNavigationView) findViewById(R.id.bnv_main)).getMenu().removeItem(R.id.navigation_main_live);
                        } else {
                            ((BottomNavigationView) findViewById(R.id.bnv_main)).getMenu().findItem(R.id.navigation_main_live).setTitle(data.getData());
                            EventBus.getDefault().postSticky(new TitleEvent(data.getData()));
                        }
                    }
                });

    }

//    private void getTabFourInfo() {
//        VodService vodService = Retrofit2Utils.INSTANCE.createByGson(VodService.class);
//        if (AgainstCheatUtil.showWarn(vodService)) {
//            return;
//        }
//        vodService.getTabFourInfo()
//                .compose(new IoMainScheduler<>())
//                .subscribe(new BaseObserver<BaseResult<TabFourInfo>>(true) {
//
//                    @Override
//                    public void onError(@NotNull ResponseException e) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(BaseResult<TabFourInfo> data) {
//                        if (data.getData().getList().get(0).getImg().equals("0")) {
//                            ((BottomNavigationView) findViewById(R.id.bnv_main)).getMenu().removeItem(R.id.navigation_main_game);
//                        } else {
//                            ((BottomNavigationView) findViewById(R.id.bnv_main)).getMenu().findItem(R.id.navigation_main_game).setTitle(data.getData().getList().get(0).getName());
//                            TabFourInfo.ListBean bean = data.getData().getList().get(0);
//                            EventBus.getDefault().postSticky(new GetUrlEvent(bean.getUrl(),bean.getName()));
//                        }
//                    }
//                });
//    }
}
