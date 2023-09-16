package cn.mahua.vod.ui.notice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.MessageDetail
import cn.mahua.vod.databinding.ActivityMessageDetailBinding
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver

class MessageDetailActivity : BaseActivity(), View.OnClickListener {
    private lateinit var messageDetailBinding: ActivityMessageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_message_detail
    }

    companion object {
        var mId: String=""

        @JvmStatic
        fun start(activity: Activity, id: String) {
            mId = id
            val intent = Intent(activity, MessageDetailActivity::class.java)
            activity.startActivity(intent)
        }

    }


    override fun initView() {
        super.initView()
        messageDetailBinding = ActivityMessageDetailBinding.inflate(layoutInflater)
        setContentView(messageDetailBinding.root)

        messageDetailBinding.rlBack.setOnClickListener(this)

    }

    override fun initData() {
        super.initData()
        getMsgDetail()
    }

    private fun getMsgDetail() {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(mActivity, vodService.getMsgDetail(mId),
                object : LoadingObserver<MessageDetail>(mActivity) {
                    override fun onSuccess(data: MessageDetail) {
                        messageDetailBinding.tvTitle.text = data.title
                        messageDetailBinding.tvTime.text = data.create_date
                        messageDetailBinding.tvDesc.text = data.content
                    }

                    override fun onError(e: ResponseException) {
                    }

                })
    }


    override fun onClick(v: View?) {
        when (v) {
            messageDetailBinding.rlBack -> {
                finish()
            }
        }
    }
}