package com.sweetieplayer.vod.ui.notice

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.bean.MessageBean
import com.sweetieplayer.vod.netservice.VodService
import com.sweetieplayer.vod.utils.AgainstCheatUtil
import com.sweetieplayer.vod.utils.DensityUtils
import com.sweetieplayer.vod.utils.Retrofit2Utils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.databinding.ActivityMessageCenterBinding

class MessageCenterActivity : BaseActivity(), View.OnClickListener {
    private lateinit var messageCenterBinding: ActivityMessageCenterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val msgAdapter by lazy {
        MsgAdapter(this@MessageCenterActivity)
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_message_center
    }

    override fun initView() {
        super.initView()
        messageCenterBinding = ActivityMessageCenterBinding.inflate(layoutInflater)
        setContentView(messageCenterBinding.root)

        messageCenterBinding.rvMsg.layoutManager = LinearLayoutManager(mActivity)
        messageCenterBinding.rvMsg.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val paddingTop = DensityUtils.dp2px(application, 5f)
                val paddingLeft = DensityUtils.dp2px(application, 15f)
                outRect.set(paddingLeft, paddingTop, paddingLeft, paddingTop)
            }
        })
        messageCenterBinding.rvMsg.adapter = msgAdapter


        messageCenterBinding.rlBack.setOnClickListener(this)
        messageCenterBinding.rlMsgCenter.setOnClickListener(this)
        messageCenterBinding.rlSystemNotice.setOnClickListener(this)
    }

    override fun initData() {
        super.initData()
        getMsgList()
    }

    private fun getMsgList() {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(mActivity, vodService.getMsgList(),
                object : LoadingObserver<MessageBean>(mActivity) {
                    override fun onSuccess(data: MessageBean) {
                        val list = data.list
                        msgAdapter.setNewData(list)
                    }

                    override fun onError(e: ResponseException) {
                    }

                })
    }


    override fun onClick(v: View?) {
        when (v) {
            messageCenterBinding.rlBack -> {
                finish()
            }
            messageCenterBinding.rlMsgCenter -> {

            }
            messageCenterBinding.rlSystemNotice -> {
                ToastUtils.showShort("暂无系统公告")
            }
        }
    }

    class MsgAdapter(var activity: Activity) : BaseQuickAdapter<MessageBean.ListBean, BaseViewHolder>(R.layout.item_msg_list) {
        override fun convert(helper: BaseViewHolder, item: MessageBean.ListBean?) {
            item?.run {
                helper.setText(R.id.tv_title,this.title )
                helper.setText(R.id.tv_desc, this.content)
                helper.setText(R.id.tv_time, this.create_date)
                helper.getView<ConstraintLayout>(R.id.total_view).setOnClickListener {
                    MessageDetailActivity.start(activity, this.id.toString())
                }
            }
        }

    }


}