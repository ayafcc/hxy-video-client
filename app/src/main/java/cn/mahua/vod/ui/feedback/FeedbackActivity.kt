package cn.mahua.vod.ui.feedback

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.FeedbackBean
import cn.mahua.vod.bean.Page
import cn.mahua.vod.databinding.ActivityFeedbackBinding
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.ui.login.LoginActivity
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import cn.mahua.vod.utils.UserUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.scwang.smartrefresh.layout.footer.ClassicsFooter

class FeedbackActivity : BaseActivity() {
    private var curFeedbackPage = 1
    private lateinit var feedbackBinding: ActivityFeedbackBinding

    private val feedbackAdapter: FeedbackAdapter by lazy {
        FeedbackAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_feedback
    }

    override fun initView() {
        super.initView()
        feedbackBinding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(feedbackBinding.root)

        feedbackBinding.tvSubmit.setOnClickListener {
            val comment = feedbackBinding.etComment.text.trim().toString()
            if (comment.isEmpty()) {
                ToastUtils.showShort("反馈内容不能为空")
            } else {
                if (UserUtils.isLogin()) {
                    feedback(comment)
                } else {
                    ActivityUtils.startActivity(LoginActivity::class.java)
                }
            }
        }

        feedbackBinding.refreshLayout.setEnableRefresh(false)
        feedbackBinding.refreshLayout.setRefreshFooter(ClassicsFooter(mActivity))

        feedbackBinding.rvFeedback.layoutManager = LinearLayoutManager(mActivity)
        feedbackBinding.rvFeedback.adapter = feedbackAdapter
    }

    override fun initData() {
        super.initData()
        getFeedbackList()
    }

    override fun initListener() {
        super.initListener()
        feedbackBinding.refreshLayout.setOnLoadMoreListener {
            curFeedbackPage++
            getFeedbackList()
        }
        feedbackBinding.rlBack.setOnClickListener {
            finish()
        }
    }

    private fun feedback(commentContent: String) {
        var vodService= Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this,
                vodService.feedbak(commentContent),
                object : BaseObserver<String>() {
                    override fun onSuccess(data: String) {
                        ToastUtils.showShort("反馈成功")
                        curFeedbackPage = 1
                        getFeedbackList(true)
                    }

                    override fun onError(e: ResponseException) {

                    }

                })
    }


    private fun getFeedbackList(isFresh: Boolean = false) {
        var vodService=Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this,
                vodService.getFeedbackList(curFeedbackPage.toString(), 10.toString()),
                object : BaseObserver<Page<FeedbackBean>>() {
                    override fun onSuccess(data: Page<FeedbackBean>) {
                        if (curFeedbackPage == 1) {
                            if (isFresh)
                                feedbackAdapter.setNewData(data.list)
                            else
                                feedbackAdapter.addData(data.list)
                        }

                        if (curFeedbackPage > 1) {
                            feedbackAdapter.addData(data.list)
                            if (data.list.isEmpty()) {
                                feedbackBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                            } else {
                                feedbackBinding.refreshLayout.finishLoadMore(true)
                            }
                        }
                    }

                    override fun onError(e: ResponseException) {
                        if (curFeedbackPage > 1) {
                            feedbackBinding.refreshLayout.finishLoadMore(false)
                        }
                    }

                })
    }

    private class FeedbackAdapter : BaseQuickAdapter<FeedbackBean, BaseViewHolder>(R.layout.item_feedback) {
        override fun convert(helper: BaseViewHolder, item: FeedbackBean?) {
            helper.let {
                item?.run {
                    it.setText(R.id.tvUser, gbook_name)
                    it.setText(R.id.tvTime, TimeUtils.millis2String(gbook_time * 1000))
                    it.setText(R.id.tvComment, gbook_content)
                    if(gbook_reply.isNotEmpty()){
                        it.setGone(R.id.llReplay,true)
                        it.setText(R.id.tvReplay,gbook_reply)
                    }else{
                        it.setGone(R.id.llReplay,false)
                    }
                }
            }
        }


    }

}
