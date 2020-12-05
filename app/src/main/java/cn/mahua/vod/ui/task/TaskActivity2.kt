package cn.mahua.vod.ui.task

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.OpenRecommendEvent
import cn.mahua.vod.bean.OpenShareEvent
import cn.mahua.vod.bean.TaskBean
import cn.mahua.vod.bean.TaskItemBean
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.DensityUtils
import cn.mahua.vod.utils.Retrofit2Utils
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import kotlinx.android.synthetic.main.activity_task2.*
import org.greenrobot.eventbus.EventBus

class TaskActivity2 : BaseActivity(), View.OnClickListener {
    private val taskAdapter by lazy {
        TaskAdapter(this@TaskActivity2)
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_task2
    }

    override fun initView() {
        super.initView()
        rvTask.layoutManager = LinearLayoutManager(mActivity)
        rvTask.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val paddingTop = DensityUtils.dp2px(application, 5f)
                val paddingLeft = DensityUtils.dp2px(application, 15f)
                outRect.set(paddingLeft, paddingTop, paddingLeft, paddingTop)
            }
        })
        rvTask.adapter = taskAdapter

        rl_day_task.setOnClickListener(this)
        rl_more_task.setOnClickListener(this)
    }

    override fun initListener() {
        super.initListener()
        ivBack.setOnClickListener {
            finish()
        }
    }

    override fun initData() {
        super.initData()
        getTaskList()
    }

    private fun getTaskList() {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(mActivity, vodService.getTaskList(),
                object : LoadingObserver<TaskBean>(mActivity) {
                    override fun onSuccess(data: TaskBean) {
                        val taskItems = ArrayList<TaskItemBean>()
                        taskItems.add(TaskItemBean(data.sign))
                        taskItems.add(TaskItemBean(data.mark))
                        taskItems.add(TaskItemBean(data.danmu))
                        taskItems.add(TaskItemBean(data.comment))
                        taskItems.add(TaskItemBean(data.view30m))
                        taskItems.add(TaskItemBean(data.share))
                        taskAdapter.setNewData(taskItems)
                    }

                    override fun onError(e: ResponseException) {
                    }

                })
    }


    class TaskAdapter(var activity: Activity) : BaseQuickAdapter<TaskItemBean, BaseViewHolder>(R.layout.item_task2) {
        override fun convert(helper: BaseViewHolder, item: TaskItemBean?) {
            item?.run {
                helper.setText(R.id.tv_name, item.title)
                helper.setText(R.id.tv_num, "+${item.points}")
                helper.getView<ImageView>(R.id.iv_image).setImageResource(item.image)
                val rl_do = (helper.getView(R.id.rl_status) as RelativeLayout)

                if (item.finish == 1) {
                    helper.getView<TextView>(R.id.tv_gotodo).visibility = View.GONE
                    helper.getView<TextView>(R.id.tv_done).visibility = View.VISIBLE

                    rl_do.setOnClickListener(null)
                } else {
                    helper.getView<TextView>(R.id.tv_gotodo).visibility = View.VISIBLE
                    helper.getView<TextView>(R.id.tv_done).visibility = View.GONE

                    rl_do.setTag(R.id.rl_status, item.id)
                    rl_do.setOnClickListener {
                        when (it.getTag(R.id.rl_status)) {
                            1 -> {
                                activity.finish()
                            }
                            2 -> {
                                activity.finish()
                                EventBus.getDefault().postSticky(OpenRecommendEvent())
                            }
                            3 -> {
                                activity.finish()
                                EventBus.getDefault().postSticky(OpenRecommendEvent())
                            }
                            4 -> {
                                activity.finish()
                                EventBus.getDefault().postSticky(OpenRecommendEvent())
                            }
                            5 -> {
                                activity.finish()
                                EventBus.getDefault().postSticky(OpenRecommendEvent())
                            }
                            6 -> {
                                activity.finish()
                                EventBus.getDefault().postSticky(OpenShareEvent())
                            }
                        }
                    }
                }
            }
        }

    }

    companion object {
        fun start() {
            ActivityUtils.startActivity(TaskActivity2::class.java, R.anim.slide_in_right, R.anim.no_anim)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            rl_day_task -> {
            }
            rl_more_task -> {
                finish()
                EventBus.getDefault().postSticky(OpenShareEvent())
            }
        }
    }
}
