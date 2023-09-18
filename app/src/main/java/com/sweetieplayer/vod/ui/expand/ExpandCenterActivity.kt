package com.sweetieplayer.vod.ui.expand

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.sweetieplayer.vod.ApiConfig
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.bean.ExpandCenter
import com.sweetieplayer.vod.bean.UserInfoBean
import com.sweetieplayer.vod.databinding.ActivityExpandCenterBinding
import com.sweetieplayer.vod.netservice.VodService
import com.sweetieplayer.vod.ui.share.ShareActivity
import com.sweetieplayer.vod.utils.AgainstCheatUtil
import com.sweetieplayer.vod.utils.Retrofit2Utils

class ExpandCenterActivity : BaseActivity(), View.OnClickListener {
    private lateinit var expandCenterBinding : ActivityExpandCenterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_expand_center
    }

    override fun initView() {
        super.initView()

        expandCenterBinding = ActivityExpandCenterBinding.inflate(layoutInflater)
        setContentView(expandCenterBinding.root)

        expandCenterBinding.rlBack.setOnClickListener(this)
        expandCenterBinding.tvMyExpand.setOnClickListener(this)
        expandCenterBinding.rlShare.setOnClickListener(this)
    }

    override fun initData() {
        super.initData()
        getUserInfo()
        getExpandCenter()
    }

    private fun getUserInfo() {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.userInfo(), object : BaseObserver<UserInfoBean>() {
            override fun onSuccess(data: UserInfoBean) {
                expandCenterBinding.tvNick.text = data.user_nick_name
                if (data.user_portrait.isNotEmpty()) {
                    Glide.with(mActivity)
                            .load(ApiConfig.BASE_URL + "/" + data.user_portrait)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .into(expandCenterBinding.ivAvatar)
                } else {
                    Glide.with(mActivity)
                            .load(R.drawable.ic_default_avator)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .into(expandCenterBinding.ivAvatar)
                }
                when (data.user_level) {
                    "1" -> {
                        expandCenterBinding.ivStartLevel.setBackgroundResource(R.drawable.vip1)
                        expandCenterBinding.ivEndLevel.setBackgroundResource(R.drawable.vip2)
                        expandCenterBinding.tvNext.text = "距离下一等级还差${data.leave_peoples}人"
                    }
                    "2" -> {
                        expandCenterBinding.ivStartLevel.setBackgroundResource(R.drawable.vip2)
                        expandCenterBinding.ivEndLevel.setBackgroundResource(R.drawable.vip3)
                        expandCenterBinding.tvNext.text = "距离下一等级还差${data.leave_peoples}人"
                    }
                    "3" -> {
                        expandCenterBinding.ivStartLevel.setBackgroundResource(R.drawable.vip3)
                        expandCenterBinding.ivEndLevel.setBackgroundResource(R.drawable.vip4)
                        expandCenterBinding.tvNext.text = "距离下一等级还差${data.leave_peoples}人"
                    }
                    "4" -> {
                        expandCenterBinding.ivStartLevel.setBackgroundResource(R.drawable.vip4)
                        expandCenterBinding.ivEndLevel.setBackgroundResource(R.drawable.vip5)
                        expandCenterBinding.tvNext.text = "距离下一等级还差${data.leave_peoples}人"
                    }
                    "5" -> {
                        expandCenterBinding.ivStartLevel.setBackgroundResource(R.drawable.vip5)
                        expandCenterBinding.ivEndLevel.setBackgroundResource(R.drawable.vip5)
                        expandCenterBinding.tvNext.text = "已达到最高VIP级别"
                    }
                }
            }

            override fun onError(e: ResponseException) {
                ToastUtils.showShort(e.getErrorMessage())
            }
        })
    }

    private fun getExpandCenter() {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(mActivity, vodService.expandCenter(),
                object : LoadingObserver<ExpandCenter>(mActivity) {
                    override fun onSuccess(data: ExpandCenter) {
                        expandCenterBinding.tvCount1.text = "享受每日影片观影${data.v1.view_count}次"

                        expandCenterBinding.tvPerson2.text = "推广${data.v2.people_count}人"
                        expandCenterBinding.tvCount2.text = "享受每日影片观影${data.v2.view_count}次"

                        expandCenterBinding.tvPerson3.text = "推广${data.v3.people_count}人"
                        expandCenterBinding.tvCount3.text = "享受每日影片观影${data.v3.view_count}次"

                        expandCenterBinding.tvPerson4.text = "推广${data.v4.people_count}人"
                        expandCenterBinding.tvCount4.text = "享受每日影片观影${data.v4.view_count}次"

                        expandCenterBinding.tvPerson5.text = "推广${data.v5.people_count}人"
                        expandCenterBinding.tvCount5.text = "享受每日影片观影${data.v5.view_count}次"
                    }

                    override fun onError(e: ResponseException) {
                    }

                })
    }

    override fun onClick(v: View?) {
        when (v) {
            expandCenterBinding.rlBack -> {
                finish()
            }
            expandCenterBinding.tvMyExpand -> {
                val intent = Intent(this@ExpandCenterActivity, MyExpandActivity::class.java)
                startActivity(intent)
            }
            expandCenterBinding.rlShare -> {
                val intent = Intent(this@ExpandCenterActivity, ShareActivity::class.java)
                startActivity(intent)
            }
        }
    }
}