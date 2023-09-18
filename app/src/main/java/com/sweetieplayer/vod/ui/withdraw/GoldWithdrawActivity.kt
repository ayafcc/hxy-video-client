package com.sweetieplayer.vod.ui.withdraw

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.bean.*
import com.sweetieplayer.vod.databinding.ActivityCoinWithdrawBinding
import com.sweetieplayer.vod.netservice.VodService
import com.sweetieplayer.vod.utils.AgainstCheatUtil
import com.sweetieplayer.vod.utils.Retrofit2Utils
import com.sweetieplayer.vod.utils.UserUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class GoldWithdrawActivity : BaseActivity() {
    private val array = arrayListOf("支付宝", "微信")
    private var curPayType = 1// 1 支付宝 2微信
    private var curRecordIndex = 1
    private lateinit var coinWithdrawBinding: ActivityCoinWithdrawBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val recordAdapter: RecordAdapter by lazy {
        RecordAdapter()
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_coin_withdraw
    }

    override fun initView() {
        super.initView()
        coinWithdrawBinding = ActivityCoinWithdrawBinding.inflate(layoutInflater)
        setContentView(coinWithdrawBinding.root)

        onUserInfoChanged()

        coinWithdrawBinding.rvRecord.layoutManager = LinearLayoutManager(mActivity)
        coinWithdrawBinding.rvRecord.adapter = recordAdapter
        coinWithdrawBinding.refreshLayout.setEnableRefresh(false)
        coinWithdrawBinding.refreshLayout.setRefreshFooter(ClassicsFooter(mActivity))

        val adapter = ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, array)
        coinWithdrawBinding.spinner.adapter = adapter
        coinWithdrawBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                curPayType = position + 1
            }

        }
        coinWithdrawBinding.spinner.setSelection(0)
    }

    override fun initListener() {
        super.initListener()
        coinWithdrawBinding.rlBack.setOnClickListener {
            finish()
        }
        coinWithdrawBinding.tvFinish.setOnClickListener {
            withdraw()
        }
        coinWithdrawBinding.refreshLayout.setOnLoadMoreListener {
            curRecordIndex++
            getRecordData()
        }
    }

    override fun initData() {
        super.initData()
        getGlodTip()
        getRecordData()
    }

    override fun isUseEventBus(): Boolean {
        return true
    }

    @Subscribe
    fun onUserInfoChanged(userinfo: UserInfoBean? = null) {
        UserUtils.userInfo?.let {
            coinWithdrawBinding.tvPoints.text = it.user_points.toString()
            coinWithdrawBinding.tvCoin.text = it.user_gold
        }
    }

    private fun getGlodTip() {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.goldTip(),
            object : LoadingObserver<GoldTipBean>(mActivity) {
                override fun onSuccess(data: GoldTipBean) {
                    coinWithdrawBinding.tvWithdrawHit.text = data.info
                }

                override fun onError(e: ResponseException) {

                }

            })
    }

    private fun refreshRecordData() {
        curPayType = 1
        recordAdapter.setNewData(null)
        getRecordData()
    }

    private fun getRecordData() {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.getGoldWithdrawRecord(curRecordIndex.toString(), 10.toString()),
            object : BaseObserver<Page<GoldWithdrawRecordBean>>() {
                override fun onSuccess(data: Page<GoldWithdrawRecordBean>) {
                    recordAdapter.addData(data.list)
                    if (curRecordIndex > 1) {
                        if (data.list.isEmpty()) {
                            coinWithdrawBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            coinWithdrawBinding.refreshLayout.finishLoadMore(true)
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    if (curRecordIndex > 1) {
                        coinWithdrawBinding.refreshLayout.finishLoadMore(false)
                    }
                }

            })

    }

    private fun withdraw() {
        val accout = coinWithdrawBinding.etAccount.text.trim().toString()
        val name = coinWithdrawBinding.etName.text.trim().toString()
        val money = coinWithdrawBinding.etMoney.text.trim().toString()
        if (accout.isNullOrEmpty()) {
            ToastUtils.showShort("收款账号不能为空！")
            return
        }

        if (name.isNullOrEmpty()) {
            ToastUtils.showShort("收款姓名不能为空！")
            return
        }

        if (money.isNullOrEmpty()) {
            ToastUtils.showShort("提现金额不能为空！")
            return
        }
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.goldWithdrawApply(money, curPayType.toString(), accout, name),
            object : LoadingObserver<GoldWithdrawBean>(mActivity) {
                override fun onSuccess(data: GoldWithdrawBean) {
                    ToastUtils.showShort(data.info)
                    EventBus.getDefault().post(LoginBean())
                    refreshRecordData()
                }

                override fun onError(e: ResponseException) {
                }

            })
    }


    private class RecordAdapter : BaseQuickAdapter<GoldWithdrawRecordBean, BaseViewHolder>(R.layout.item_withdraw) {

        override fun convert(helper: BaseViewHolder, item: GoldWithdrawRecordBean?) {
            item?.let {
                helper.setText(R.id.tvPoints, it.gold_num.toString())
                helper.setText(R.id.tvMoney, it.num)
                var status = when (it.status) {
                    0 -> "提现中"
                    1 -> "提现成功"
                    2 -> "提现失败"
                    else -> "未知"
                }
                helper.setText(R.id.tvStatus, status)
                helper.setText(R.id.tvTime, TimeUtils.millis2String(it.created_time * 1000))
            }

        }

    }
}
