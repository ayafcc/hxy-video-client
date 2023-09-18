package com.sweetieplayer.vod.ui.pay

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.bean.*
import com.sweetieplayer.vod.databinding.FragmentVipBinding
import com.sweetieplayer.vod.netservice.VodService
import com.sweetieplayer.vod.ui.expand.ExpandCenterActivity
import com.sweetieplayer.vod.ui.widget.HitDialog
import com.sweetieplayer.vod.utils.AgainstCheatUtil
import com.sweetieplayer.vod.utils.Retrofit2Utils
import com.sweetieplayer.vod.utils.UserUtils
import org.greenrobot.eventbus.EventBus

class VipFragment : BaseFragment() {
    private lateinit var vipBinding: FragmentVipBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_vip
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vipBinding = FragmentVipBinding.inflate(inflater, container, false)
        return vipBinding.root
    }

    override fun initListener() {
        super.initListener()
        vipBinding.rlDay.setOnClickListener {
            upgrade("day", String.format(getString(R.string.day_upgrade_hit_s), vipBinding.tvScoreDay.text))
        }
        vipBinding.rlWeek.setOnClickListener {
            upgrade("week", String.format(getString(R.string.week_upgrade_hit_s), vipBinding.tvScoreWeek.text))
        }
        vipBinding.rlMonth.setOnClickListener {
            upgrade("month", String.format(getString(R.string.month_upgrade_hit_s), vipBinding.tvScoreMonth.text))
        }
        vipBinding.rlYear.setOnClickListener {
            upgrade("year", String.format(getString(R.string.year_upgrade_hit_s), vipBinding.tvScoreYear.text))
        }
        vipBinding.rlProxy.setOnClickListener {
            changeAgents()
        }
        vipBinding.rlPublic.setOnClickListener {
            val intent = Intent(context, ExpandCenterActivity::class.java)
            ActivityUtils.startActivity(intent)
        }
    }

    override fun initLoad() {
        super.initLoad()
        getScoreList()
        getAgentsScore()
    }

    private fun getScoreList() {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(this@VipFragment, vodService.getScoreList(),
            object : LoadingObserver<ScoreListBean>(mActivity) {
                override fun onSuccess(data: ScoreListBean) {
                    data.list?.`_$3`?.let {
                        val day = data.list.`_$3`.group_points_day
                        val week = data.list.`_$3`.group_points_week
                        val month = data.list.`_$3`.group_points_month
                        val year = data.list.`_$3`.group_points_year

                        vipBinding.tvScoreDay.text = "${day}积分"
                        vipBinding.tvScoreWeek.text = "${week}积分"
                        vipBinding.tvScoreMonth.text = "${month}积分"
                        vipBinding.tvScoreYear.text = "${year}积分"
                    }
                }

                override fun onError(e: ResponseException) {
                }

            })
    }


    private fun upgrade(price: String, hitMsg: String) {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        HitDialog(mActivity)
            .setMessage(hitMsg)
            .setOnHitDialogClickListener(object : HitDialog.OnHitDialogClickListener() {
                override fun onOkClick(dialog: HitDialog) {
                    super.onOkClick(dialog)
                    RequestManager.execute(this@VipFragment,
                        vodService.upgradeGroup(price, UserUtils.userInfo?.group_id.toString()),
                        object : LoadingObserver<CardBuyBean>(mActivity) {
                            override fun onSuccess(data: CardBuyBean) {
                                ToastUtils.showShort(data.msg)
                                EventBus.getDefault().post(LoginBean())
                            }

                            override fun onError(e: ResponseException) {
                            }

                        })
                }
            })
            .show()
    }

    private fun getAgentsScore() {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(this@VipFragment, vodService.getAgentsScore(),
            object : LoadingObserver<AgentsScoreBean>(mActivity) {
                override fun onSuccess(data: AgentsScoreBean) {
                    vipBinding.rlProxy.visibility = View.VISIBLE
                    vipBinding.proxyView.visibility = View.VISIBLE
                    vipBinding.tvProxyPoints.text = "${data.score}积分"
                }

                override fun onError(e: ResponseException) {
                }

            })
    }

    private fun changeAgents() {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(this@VipFragment, vodService.changeAgents(),
            object : LoadingObserver<ChangeAgentsBean>(mActivity) {
                override fun onSuccess(data: ChangeAgentsBean) {
                    ToastUtils.showShort(data.msg)
                    EventBus.getDefault().post(LoginBean())
                }

                override fun onError(e: ResponseException) {
                }

            })
    }

}
