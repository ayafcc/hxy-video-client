package cn.mahua.vod.ui.pay

import android.content.Intent
import android.view.View
import cn.mahua.vod.R
import cn.mahua.vod.bean.*
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.ui.expand.ExpandCenterActivity
import cn.mahua.vod.ui.widget.HitDialog
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import cn.mahua.vod.utils.UserUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import kotlinx.android.synthetic.main.fragment_vip.*
import org.greenrobot.eventbus.EventBus

class VipFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_vip
    }

    override fun initListener() {
        super.initListener()
        rlDay.setOnClickListener {
            upgrade("day", String.format(getString(R.string.day_upgrade_hit_s), tv_score_day.text))
        }
        rlWeek.setOnClickListener {
            upgrade("week", String.format(getString(R.string.week_upgrade_hit_s), tv_score_week.text))
        }
        rlMonth.setOnClickListener {
            upgrade("month", String.format(getString(R.string.month_upgrade_hit_s), tv_score_month.text))
        }
        rlYear.setOnClickListener {
            upgrade("year", String.format(getString(R.string.year_upgrade_hit_s), tv_score_year.text))
        }
        rlProxy.setOnClickListener {
            changeAgents()
        }
        rlPublic.setOnClickListener {
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

                            tv_score_day.text = "${day}积分"
                            tv_score_week.text = "${week}积分"
                            tv_score_month.text = "${month}积分"
                            tv_score_year.text = "${year}积分"
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
                        RequestManager.execute(this@VipFragment, vodService.upgradeGroup(price, UserUtils.userInfo?.group_id.toString()),
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
                        rlProxy.visibility = View.VISIBLE
                        proxy_view.visibility = View.VISIBLE
                        tvProxyPoints.text = "${data.score}积分"
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
