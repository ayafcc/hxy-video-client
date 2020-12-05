package cn.mahua.vod.ui.pay

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import cn.mahua.vod.ApiConfig
import cn.mahua.vod.R
import cn.mahua.vod.bean.*
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.ui.browser.BrowserActivity
import cn.mahua.vod.ui.widget.PayDialog
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.github.StormWyrm.wanandroid.base.sheduler.IoMainScheduler
import kotlinx.android.synthetic.main.fragment_pay.*
import org.greenrobot.eventbus.EventBus

class PayFragment : BaseFragment() {

    private var orderCode: String? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_pay
    }

    override fun initListener() {
        super.initListener()
        tvMoney.setOnClickListener {
            pointPurchase()
        }
        tvCard.setOnClickListener {
            cardBuy()
        }
    }

    override fun initLoad() {
        super.initLoad()
        getPayTip()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PAY) {
            checkOrder()
        }
    }

    private fun getPayTip() {
        val vodService= Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        vodService.payTip()
                .compose(IoMainScheduler())
                .subscribe(object : BaseObserver<BaseResult<PayTipBean>>() {
                    override fun onSuccess(data: BaseResult<PayTipBean>) {
                        if (data.isSuccessful) {
                            tvHit.text = data.msg
                            tvOnlinePay.setOnClickListener {
                                ActivityUtils.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.data.url)))
                            }
                        }
                    }

                    override fun onError(e: ResponseException) {
                    }

                })

    }

    private fun pointPurchase() {
        val money = etMoney.text.trim().toString()
        if (TextUtils.isEmpty(money)) {
            ToastUtils.showShort("输入金额不能为空")
            return
        }
        try {
            money.toInt()
        } catch (e: Exception) {
            ToastUtils.showShort("输入金额不能必须为数字")
            return
        }
        val vodService=Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(this, vodService.pointPurchase(money), object : LoadingObserver<PointPurchseBean>(mActivity) {
            override fun onSuccess(data: PointPurchseBean) {
                orderCode = data.order_code//记录order_code
                PayDialog(mActivity, data)
                        .setOnPayDialogClickListener(object : PayDialog.OnPayDialogClickListener() {
                            override fun onPayTypeClick(dialog: PayDialog, payment: String) {
                                super.onPayTypeClick(dialog, payment)
                                val payUrl = "${ApiConfig.BASE_URL + ApiConfig.PAY}?payment=${payment}&order_code=${data.order_code}"
                                Intent(context, BrowserActivity::class.java).apply {
                                    putExtra("url", payUrl)
                                    startActivityForResult(this, REQUEST_PAY)
                                }
                            }
                        })
                        .show()
            }

            override fun onError(e: ResponseException) {
            }

        })
    }

    private fun checkOrder() {
        val vodService=Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        orderCode?.let {
            RequestManager.execute(
                    this@PayFragment,
                    vodService.order(it),
                    object : BaseObserver<OrderBean>() {
                        override fun onSuccess(data: OrderBean) {
                            orderCode = null //更新状态后 重置ordercode
                            if (data.order_status == 0) {
                                ToastUtils.showShort("支付失败")
                            } else {
                                ToastUtils.showShort("支付成功")
                                EventBus.getDefault().post(LoginBean())
                            }
                        }

                        override fun onError(e: ResponseException) {
                            orderCode = null
                        }

                    }

            )
        }

    }

    private fun cardBuy() {
        val card = etCardPassword.text.trim().toString()
        if (TextUtils.isEmpty(card)) {
            ToastUtils.showShort("卡密不能为空")
            return
        }
        val vodService=Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(this, vodService.cardBuy(card), object : LoadingObserver<CardBuyBean>(mActivity) {
            override fun onSuccess(data: CardBuyBean) {
                ToastUtils.showShort(data.msg)
                EventBus.getDefault().post(LoginBean())
            }

            override fun onError(e: ResponseException) {
            }

        })
    }

    companion object {
        const val REQUEST_PAY = 0
    }
}
