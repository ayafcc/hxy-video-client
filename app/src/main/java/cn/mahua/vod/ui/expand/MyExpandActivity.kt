package cn.mahua.vod.ui.expand

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.MyExpand
import cn.mahua.vod.databinding.ActivityMyExpandBinding
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.DateUtil
import cn.mahua.vod.utils.DensityUtils
import cn.mahua.vod.utils.Retrofit2Utils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

class MyExpandActivity : BaseActivity(), View.OnClickListener, OnLoadMoreListener, OnRefreshListener {
    private var mPage = 1;
    private var isRefresh = true
    private var mDataList = ArrayList<MyExpand.ListBean>()
    private lateinit var myExpandBinding:ActivityMyExpandBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val msgAdapter by lazy {
        ExpandAdapter(this@MyExpandActivity)
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_my_expand
    }

    override fun initView() {
        super.initView()

        myExpandBinding = ActivityMyExpandBinding.inflate(layoutInflater)
        setContentView(myExpandBinding.root)

        myExpandBinding.refreshLayout.setOnLoadMoreListener(this)
        myExpandBinding.refreshLayout.setOnRefreshListener(this)
        myExpandBinding.rvExpand.layoutManager = LinearLayoutManager(mActivity)
        myExpandBinding.rvExpand.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val paddingLeft = DensityUtils.dp2px(application, 5f)
                outRect.set(paddingLeft, 0, paddingLeft, 0)
            }
        })
        myExpandBinding.rvExpand.adapter = msgAdapter


        myExpandBinding.rlBack.setOnClickListener(this)
    }

    override fun initData() {
        super.initData()
        getExpandList()
    }

    private fun getExpandList() {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(mActivity, vodService.myExpand(mPage.toString(), "20"),
                object : LoadingObserver<MyExpand>(mActivity) {
                    override fun onSuccess(data: MyExpand) {
                        myExpandBinding.refreshLayout.finishLoadMore()
                        myExpandBinding.refreshLayout.finishRefresh()
                        val list = data.list
                        if (list.size < 20) {
                            myExpandBinding.refreshLayout.setNoMoreData(true)
                        } else {
                            myExpandBinding.refreshLayout.setNoMoreData(false)
                        }
                        if (isRefresh) {
                            mDataList.clear()
                        }
                        mDataList.addAll(list)
                        msgAdapter.setNewData(mDataList)
                        myExpandBinding.tvTotal.text = data.total.toString() + "人"
                    }

                    override fun onError(e: ResponseException) {
                    }

                })
    }


    override fun onClick(v: View?) {
        when (v) {
            myExpandBinding.rlBack -> {
                finish()
            }
        }
    }

    class ExpandAdapter(var activity: Activity) : BaseQuickAdapter<MyExpand.ListBean, BaseViewHolder>(R.layout.item_my_expand) {
        override fun convert(helper: BaseViewHolder, item: MyExpand.ListBean?) {
            item?.run {
                helper.setText(R.id.tv_name, this.user_nick_name)
                helper.setText(R.id.tv_id, this.user_id.toString())
                helper.setText(R.id.tv_time, DateUtil.getyyyyMMddHHmm(this.user_reg_time * 1000.toLong()))
            }
        }

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isRefresh = false
        mPage++
        getExpandList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        isRefresh = true
        mPage = 1
        getExpandList()
    }


}