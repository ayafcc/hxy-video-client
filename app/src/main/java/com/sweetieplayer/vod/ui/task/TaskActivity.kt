package com.sweetieplayer.vod.ui.task

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.bean.TaskBean
import com.sweetieplayer.vod.bean.TaskItemBean
import com.sweetieplayer.vod.netservice.VodService
import com.sweetieplayer.vod.utils.AgainstCheatUtil
import com.sweetieplayer.vod.utils.Retrofit2Utils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.databinding.ActivityTaskBinding

class TaskActivity : BaseActivity() {
    private lateinit var taskBinding: ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val taskAdapter by lazy {
        TaskAdapter()
    }

    override fun getLayoutResID(): Int {
        BarUtils.setStatusBarLightMode(this, true)
        return R.layout.activity_task
    }

    override fun initView() {
        super.initView()

        taskBinding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(taskBinding.root)
        taskBinding.rvTask.layoutManager = LinearLayoutManager(mActivity)
        taskBinding.rvTask.adapter = taskAdapter
    }

    override fun initListener() {
        super.initListener()
        taskBinding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun initData() {
        super.initData()
        getTaskList()
    }

    private fun getTaskList() {
        var vodService= Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(mActivity, vodService.getTaskList(),
                object : LoadingObserver<TaskBean>(mActivity) {
                    override fun onSuccess(data: TaskBean) {
                        val taskItems = ArrayList<TaskItemBean>()
                        taskItems.add(TaskItemBean(data.sign))
                        taskItems.add(TaskItemBean(data.share))
                        taskItems.add(TaskItemBean(data.comment))
                        taskItems.add(TaskItemBean(data.mark))
                        taskItems.add(TaskItemBean(data.danmu))
                        taskAdapter.setNewData(taskItems)
                    }

                    override fun onError(e: ResponseException) {
                    }

                })
    }

    class TaskAdapter : BaseQuickAdapter<TaskItemBean, BaseViewHolder>(R.layout.item_task) {
        override fun convert(helper: BaseViewHolder, item: TaskItemBean?) {
            item?.run {
                helper.setText(R.id.item_tv_task_t1, item.title)
                helper.setText(R.id.item_tv_task_t2, item.info)
                helper.setText(R.id.item_tv_task_t3, "+${item.points}分")

            }
        }

    }

    companion object {
        fun start() {
            ActivityUtils.startActivity(TaskActivity::class.java, R.anim.slide_in_right, R.anim.no_anim)
        }
    }
}
