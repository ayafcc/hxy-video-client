package com.sweetieplayer.vod.ui.pay

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.sweetieplayer.vod.ApiConfig
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.bean.UserInfoBean
import com.sweetieplayer.vod.databinding.ActivityPayBinding
import com.sweetieplayer.vod.netservice.VodService
import com.sweetieplayer.vod.utils.AgainstCheatUtil
import com.sweetieplayer.vod.utils.Retrofit2Utils
import com.sweetieplayer.vod.utils.UserUtils
import org.greenrobot.eventbus.Subscribe

class PayActivity : BaseActivity() {
    private lateinit var payBinding: ActivityPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        payBinding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(payBinding.root)
        changeUserInfo()
        val type = intent.getIntExtra("type", 0)
        if (type == 0) {
            payBinding.tvTaskTitle.setText(R.string.pay_title)
        } else {
            payBinding.tvTaskTitle.setText(R.string.vip_upgrade_title)
        }
        payBinding.vpPay.adapter = PayFragmentPagerAdapter(supportFragmentManager)
        payBinding.tab.setupWithViewPager(payBinding.vpPay)
        if (type == 1) {
            payBinding.vpPay.setCurrentItem(1, true)
        }
    }

    override fun initListener() {
        payBinding.ivTaskBack.setOnClickListener {
            finish()
        }
        payBinding.vpPay.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    payBinding.tvTaskTitle.setText(R.string.pay_title)
                } else {
                    payBinding.tvTaskTitle.setText(R.string.vip_upgrade_title)
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
            payBinding.tvMessage.text = "${it.group?.group_name}：${it.user_nick_name}"
            payBinding.tvExpireTime.text ="VIP有效期："+ TimeUtils.millis2String(it.user_end_time * 1000)
            payBinding.tvCoin.text = StringUtils.getString(R.string.remaining_coin, it.user_gold)
            payBinding.tvPoints.text = StringUtils.getString(R.string.remaining_points, it.user_points.toString())
            if (it.user_portrait.isNotEmpty()) {
                Glide.with(mActivity)
                        .load(ApiConfig.BASE_URL + "/" + it.user_portrait)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(payBinding.tvAvator)
            } else {
                Glide.with(mActivity)
                        .load(R.drawable.ic_default_avator)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(payBinding.tvAvator)
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
