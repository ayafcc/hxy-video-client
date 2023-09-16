package cn.mahua.vod.ui.score

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import cn.mahua.vod.App
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.Page
import cn.mahua.vod.bean.PlayLogBean
import cn.mahua.vod.bean.PlayScoreBean
import cn.mahua.vod.databinding.ActivityPlayScoreBinding
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.ui.play.PlayActivity
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.MyLinearLayoutManager
import cn.mahua.vod.utils.Retrofit2Utils
import cn.mahua.vod.utils.UserUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class PlayScoreActivity : BaseActivity() {
    private var isEditMode = false
    private var isAllSelect: Boolean = false
    private var curPage: Int = 1
    private lateinit var playScoreBinding: ActivityPlayScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //     private lateinit var refreshLayout: RefreshLayout

    private val playScoreAdapter: PlayScoreAdapter by lazy {
        PlayScoreAdapter().apply {
            setOnItemClickListener { adapter, view, position ->
                val tempItem: Any = adapter.getItem(position) ?: return@setOnItemClickListener
                val item = tempItem as PlayScoreBean
                if (isEditMode) {
                    item.isSelect = !item.isSelect
                    if (isAllSelect && !item.isSelect) {
                        isAllSelect = false
                        playScoreBinding.tvSelect.text = "全选"
                    }
                    adapter.data[position] = item
                    notifyItemChanged(position)
                    changeDeleteNum()
                } else {
                    App.curPlayScoreBean = item
                    PlayActivity.startByPlayScore(item.vodId)
                }
            }
        }
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_play_score
    }

    override fun initView() {
        super.initView()

        playScoreBinding = ActivityPlayScoreBinding.inflate(layoutInflater)
        setContentView(playScoreBinding.root)
        playScoreBinding.rvPlayScore.layoutManager = MyLinearLayoutManager(mActivity)
        playScoreBinding.rvPlayScore.adapter = playScoreAdapter

        playScoreBinding.refreshLayout.setDisableContentWhenRefresh(false) //是否在刷新的时候禁止列表的操作
        playScoreBinding.refreshLayout.setDisableContentWhenLoading(false) //是否在加载的时候禁止列表的操作
        playScoreBinding.refreshLayout.setEnableLoadMore(true) //是否启用上拉加载功能
        playScoreBinding.refreshLayout.setEnableRefresh(true) //是否启用上拉加载功能;
        playScoreBinding.refreshLayout.setEnableAutoLoadMore(true)
        playScoreBinding.refreshLayout.setOnRefreshListener(OnRefreshListener {
            curPage = 1
            if (curPage == 1) {
                playScoreAdapter.data.clear()
            }
            getPlayScore(curPage, false)
        })
        playScoreBinding.refreshLayout.setOnLoadMoreListener(OnLoadMoreListener {

            curPage++
            getPlayScore(curPage, true)

        })


    }

    override fun onResume() {
        super.onResume()
        curPage = 1
        if (curPage == 1) {
            playScoreAdapter.data.clear()
        }
        getPlayScore(curPage, false)
    }

    override fun initListener() {
        super.initListener()
        playScoreBinding.tvSelect.setOnClickListener {
            if (isAllSelect) {
                playScoreAdapter.data.map {
                    it.isSelect = false
                }
                isAllSelect = false
                playScoreBinding.tvSelect.text = "全选"
            } else {
                playScoreAdapter.data.map {
                    it.isSelect = true
                }
                isAllSelect = true
                playScoreBinding.tvSelect.text = "取消全选"
            }
            playScoreAdapter.notifyDataSetChanged()
            changeDeleteNum()
        }

        playScoreBinding.tvSelectCount.setOnClickListener {
            val ids = getSelectCollection()
            if (ids.isNullOrEmpty()) {
                ToastUtils.showShort("未选择任何数据")
            } else {
                deleteCollection(ids)
            }
        }

        playScoreBinding.tvEdit.setOnClickListener {
            isEditMode = !isEditMode
            changeEditMode()
        }

        playScoreBinding.rlBack.setOnClickListener {
            setResult(5)
            finish()
        }
    }

    private fun getSelectCollection(): List<String> {
        var ids = ArrayList<String>()
        playScoreAdapter.data.map {
            if (it.isSelect) {
                ids.add(it.vodId.toString())
            }
        }

        return ids
    }

    private fun changeEditMode() {
        if (isEditMode) {
            playScoreBinding.tvEdit.text = "取消"
            playScoreBinding.breakLine.visibility = View.VISIBLE
            playScoreBinding.rlEdit.visibility = View.VISIBLE
            playScoreAdapter.data.map {
                it.isSelect = false
            }
            isAllSelect = false
            playScoreBinding.tvSelect.text = "全选"
        } else {
            playScoreBinding.tvEdit.text = "编辑"
            playScoreBinding.breakLine.visibility = View.GONE
            playScoreBinding.rlEdit.visibility = View.GONE
        }
        playScoreAdapter.changeEditMode(isEditMode)
    }

    private fun getSelectCount(): Int {
        var count = 0
        playScoreAdapter.data.map {
            if (it.isSelect) {
                count++
            }
        }
        return count
    }

    private fun changeDeleteNum() {
        playScoreBinding.tvSelectCount.text = "删除(${getSelectCount()})"
    }

    private fun deleteCollection(ids: List<String>) {
        ids.forEach {
            deletePlayScore(it)
        }
        var playScoreBeans = ArrayList<PlayScoreBean>()
        playScoreAdapter.setNewData(playScoreBeans)
        isEditMode = false
        changeEditMode()
        changeDeleteNum()
    }


    private fun deletePlayScore(id: String) {
        if(UserUtils.isLogin()) {
            var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
            if (AgainstCheatUtil.showWarn(vodService)) {
                return;
            }
            RequestManager.execute(
                    mActivity,
                    vodService.deletePlayLogList(id),
                    object : LoadingObserver<String>(mActivity) {
                        override fun onSuccess(data: String) {
                            ToastUtils.showShort("删除成功！")
                            getPlayScore(1, false)
                        }

                        override fun onError(e: ResponseException) {
                            ToastUtils.showShort("删除失败！")
                        }

                    }
            )
        }else{
            ToastUtils.showShort("删除失败！")
        }

    }

    private fun getPlayScore(page: Int, isLoadMore: Boolean) {
//        LitePal.order("id desc").findAsync(PlayScoreBean::class.java).listen {
//          playScoreAdapter.setNewData(it)
//        }

        var vodService=Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.getPlayLogList(curPage.toString(), "8"),
                object : LoadingObserver<Page<PlayLogBean>>(mActivity) {
                    override fun onSuccess(data: Page<PlayLogBean>) {

                        var playScoreBeans = ArrayList<PlayScoreBean>()
                        var playLogBeans = data.list
                        playLogBeans.forEach {
                            val playScoreBean: PlayScoreBean = PlayScoreBean()
                            playScoreBean.vodName = it.vod_name
                            playScoreBean.vodImgUrl = it.vod_pic
                            if (it.percent == "NaN") {
                                playScoreBean.percentage = 0.0f
                            } else {
                                try {
                                    playScoreBean.percentage = it.percent.toFloat()
                                } catch (ex: Exception) {
                                }
                            }
                            playScoreBean.typeId = it.type_id;
                            playScoreBean.vodId = it.vod_id.toInt()
                            playScoreBean.isSelect = false
                            playScoreBean.vodSelectedWorks = it.nid.toString()
                            playScoreBean.urlIndex = it.urlIndex
                            playScoreBean.curProgress = it.curProgress
                            playScoreBean.playSourceIndex = it.playSourceIndex


                            var gson: Gson = Gson()
                            var playScoreBeanStr = gson.toJson(playScoreBean).toString();
                            Log.i("playlog", "playScoreBean${playScoreBeanStr}")
                            playScoreBeans.add(playScoreBean)
                            //  Collections.reverse(playScoreBeans)


                        }

                        if (playScoreAdapter.data.size > 0) {
                            playScoreAdapter.addData(playScoreBeans)
                            playScoreAdapter.notifyDataSetChanged()
                        } else {
                            playScoreAdapter.setNewData(playScoreBeans)
                        }

                        Log.i("playlog", "getPlayLogList11${data}")

                        if (isLoadMore) {
                            playScoreBinding.refreshLayout.finishLoadMore(200)
                        } else {
                            playScoreBinding.refreshLayout.finishRefresh(200)
                        }
                    }

                    override fun onError(e: ResponseException) {

                    }
                })


    }

    private class PlayScoreAdapter(var isEditMode: Boolean = false) : BaseQuickAdapter<PlayScoreBean, BaseViewHolder>(R.layout.item_play_score_vertical) {
        override fun convert(helper: BaseViewHolder, item: PlayScoreBean?) {
            item?.run {

                val name = if (item.typeId == 3) {
                    "$vodName $vodSelectedWorks"
                } else if (item.typeId == 1) {
                    "$vodName"
                } else {
                    "$vodName ${vodSelectedWorks}"
                }
                helper.setText(R.id.tvName, name)
                helper.setText(R.id.tvPlayProgress, "观看至${(percentage * 100).toInt()}%")

                if (isEditMode) {
                    helper.setChecked(R.id.cb, isSelect)
                }
//                Glide.with(helper.itemView.context)
//                        .load( vodImgUrl)
//                        .into(helper.getView<View>(R.id.ivImg) as ImageView)

                val mation: MultiTransformation<Bitmap> = MultiTransformation(CenterCrop(), RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL))
                Glide.with(helper.itemView.context)
                        .load(vodImgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(RequestOptions.bitmapTransform(mation))
                        .into(helper.getView<View>(R.id.ivImg) as ImageView)
            }

            if (isEditMode) {
                helper.setGone(R.id.cb, true)
            } else {
                helper.setGone(R.id.cb, false)
            }
        }

        fun changeEditMode(isEditMode: Boolean) {
            this.isEditMode = isEditMode
            notifyDataSetChanged()
        }

    }

}
