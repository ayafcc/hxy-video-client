package cn.mahua.vod.ui.pay

import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.mahua.vod.ApiConfig
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.UserInfoBean
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import cn.mahua.vod.utils.UserUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import kotlinx.android.synthetic.main.activity_pay.*
import org.greenrobot.eventbus.Subscribe

class PayActivity : BaseActivity() {

    override fun getLayoutResID(): Int {
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.setStatusBarLightMode(this, true)
        return R.layout.activity_pay
    }

    override fun onResume() {
        super.onResume()
        var vodService= Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.userInfo(), object : BaseObserver<UserInfoBean>() {
            override fun onSuccess(data: UserInfoBean) {
                UserUtils.userInfo = data
                changeUserInfo()
            }

            override fun onError(e: ResponseException) {
            }
        })
    }

    override fun initView() {
        changeUserInfo()
        val type = intent.getIntExtra("type", 0)
        if (type == 0) {
            tv_task_title.setText(R.string.pay_title)
        } else {
            tv_task_title.setText(R.string.vip_upgrade_title)
        }
        vpPay.adapter = PayFragmentPagerAdapter(supportFragmentManager)
        tab.setupWithViewPager(vpPay)
        if (type == 1) {
            vpPay.setCurrentItem(1, true)
        }
    }

    override fun initListener() {
        iv_task_back.setOnClickListener {
            finish()
        }
        vpPay.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    tv_task_title.setText(R.string.pay_title)
                } else {
                    tv_task_title.setText(R.string.vip_upgrade_title)
                }
            }

        })
    }

    override fun isUseEventBus(): Boolean {
        return true
    }

    @Subscribe
    fun onUserInfoChanged(userinfo: UserInfoBean? = null) {
        changeUserInfo()
    }

    private fun changeUserInfo() {
        UserUtils.userInfo?.let {
            tvMessage.text = "${it.group?.group_name}：${it.user_nick_name}"
            tvExpireTime.text ="VIP有效期："+ TimeUtils.millis2String(it.user_end_time * 1000)
            tvCoin.text = StringUtils.getString(R.string.remaining_coin, it.user_gold)
            tvPoints.text = StringUtils.getString(R.string.remaining_points, it.user_points.toString())
            if (it.user_portrait.isNotEmpty()) {
                Glide.with(mActivity)
                        .load(ApiConfig.BASE_URL + "/" + it.user_portrait)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(tvAvator)
            } else {
                Glide.with(mActivity)
                        .load(R.drawable.ic_default_avator)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(tvAvator)
            }
        }
    }

    private class PayFragmentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                PayFragment()
            } else {
                VipFragment()
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                StringUtils.getString(R.string.pay_hit)
            } else {
                StringUtils.getString(R.string.vip_pay_hit)
            }
        }
    }
}
