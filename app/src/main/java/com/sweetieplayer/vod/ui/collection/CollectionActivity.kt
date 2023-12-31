package com.sweetieplayer.vod.ui.collection

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
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
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.bean.CollectionBean
import com.sweetieplayer.vod.bean.Page
import com.sweetieplayer.vod.databinding.ActivityCollectionBinding
import com.sweetieplayer.vod.netservice.VodService
import com.sweetieplayer.vod.ui.login.LoginActivity
import com.sweetieplayer.vod.ui.play.PlayActivity
import com.sweetieplayer.vod.utils.AgainstCheatUtil
import com.sweetieplayer.vod.utils.Retrofit2Utils
import com.sweetieplayer.vod.utils.UserUtils
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class CollectionActivity : BaseActivity() {
    private var curCollectionPage = 1
    private var isEditMode = false
    private var isAllSelect: Boolean = false
    private lateinit var collectionBinding: ActivityCollectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val collectionAdapter: CollectionAdapter by lazy {
        CollectionAdapter().apply {
            setOnItemClickListener { adapter, _, position ->
                val item = adapter.getItem(position) as CollectionBean
                if (isEditMode) {
                    item.isSelect = !item.isSelect
                    if (isAllSelect && !item.isSelect) {
                        isAllSelect = false
                        collectionBinding.tvSelect.text = "全选"
                    }
                    adapter.data[position] = item
                    notifyItemChanged(position)
                    changeDeleteNum()
                } else {
                    PlayActivity.startByCollection(item.data.id)
                }
            }
        }
    }


    override fun getLayoutResID(): Int {
        return R.layout.activity_collection
    }

    override fun initView() {
        super.initView()
        collectionBinding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(collectionBinding.root)

        collectionBinding.refreshLayout.setEnableRefresh(false)
        collectionBinding.refreshLayout.setRefreshFooter(ClassicsFooter(mActivity))

        collectionBinding.rvCollection.layoutManager = LinearLayoutManager(mActivity)
        collectionBinding.rvCollection.adapter = collectionAdapter
    }

    override fun initListener() {
        super.initListener()
        collectionBinding.tvSelect.setOnClickListener {
            if (isAllSelect) {
                collectionAdapter.data.map {
                    it.isSelect = false
                }
                isAllSelect = false
                collectionBinding.tvSelect.text = "全选"
            } else {
                collectionAdapter.data.map {
                    it.isSelect = true
                }
                isAllSelect = true
                collectionBinding.tvSelect.text = "取消全选"
            }
            collectionAdapter.notifyDataSetChanged()
            changeDeleteNum()

        }
        collectionBinding.tvSelectCount.setOnClickListener {
            val ids = getSelectCollection()
            if (ids.isNullOrEmpty()) {
                ToastUtils.showShort("未选择任何数据")
            } else {
                deleteCollection(ids)
            }
        }
        collectionBinding.tvEdit.setOnClickListener {
            isEditMode = !isEditMode
            changeEditMode()
        }

        collectionBinding.rlBack.setOnClickListener {
            finish()
        }

        collectionBinding.refreshLayout.setOnLoadMoreListener {
            curCollectionPage++
            getCollectionList()
        }
    }

    override fun initData() {
        super.initData()
        getCollectionList()
    }

    private fun getCollectionList(isFresh: Boolean = false) {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this,
            vodService.getCollectList(curCollectionPage.toString(), 10.toString(), 2.toString()),
            object : BaseObserver<Page<CollectionBean>>() {
                override fun onSuccess(data: Page<CollectionBean>) {
                    if (curCollectionPage == 1) {
                        if (isFresh)
                            collectionAdapter.setNewData(data.list)
                        else
                            collectionAdapter.addData(data.list)
                    }

                    if (curCollectionPage > 1) {
                        collectionAdapter.addData(data.list)
                        if (data.list.isEmpty()) {
                            collectionBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            collectionBinding.refreshLayout.finishLoadMore(true)
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    if (curCollectionPage > 1) {
                        collectionBinding.refreshLayout.finishLoadMore(false)
                    }
                }

            })
    }

    private fun deleteCollection(ids: String) {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this,
            vodService.deleteCollect(ids, 2.toString()),
            object : BaseObserver<String>() {
                override fun onSuccess(data: String) {
                    ToastUtils.showShort("取消成功")
                    isEditMode = false
                    changeEditMode()
                    curCollectionPage = 1
                    getCollectionList(true)
                }

                override fun onError(e: ResponseException) {
                }

            }
        )
    }

    private fun changeEditMode() {
        if (isEditMode) {
            collectionBinding.tvEdit.text = "取消"
            collectionBinding.breakLine.visibility = View.VISIBLE
            collectionBinding.rlEdit.visibility = View.VISIBLE
            collectionAdapter.data.map {
                it.isSelect = false
            }
            isAllSelect = false
            collectionBinding.tvSelect.text = "全选"
            collectionAdapter.notifyDataSetChanged()
        } else {
            collectionBinding.tvEdit.text = "编辑"
            collectionBinding.breakLine.visibility = View.GONE
            collectionBinding.rlEdit.visibility = View.GONE
        }
        collectionAdapter.changeEditMode(isEditMode)
    }

    private fun getSelectCount(): Int {
        var count = 0
        collectionAdapter.data.map {
            if (it.isSelect) {
                count++
            }
        }
        return count
    }


    private fun changeDeleteNum() {
        collectionBinding.tvSelectCount.text = "删除(${getSelectCount()})"
    }

    private fun getSelectCollection(): String {
        var ids = ""
        collectionAdapter.data.map {
            if (it.isSelect) {
                ids += "${it.data.id},"
            }
        }
        if (ids.isNotEmpty()) {
            return ids.substring(0, ids.length - 1)
        }
        return ids
    }

    class CollectionAdapter(var isEditMode: Boolean = false) :
        BaseQuickAdapter<CollectionBean, BaseViewHolder>(R.layout.item_collection) {

        override fun convert(helper: BaseViewHolder, item: CollectionBean?) {
            item?.let {
                helper.setText(R.id.tvName, it.data.name)
                helper.setText(R.id.tvType, it.data.type.type_name)
                val ivAvator = helper.getView<ImageView>(R.id.ivImg)
                if (isEditMode) {
                    helper.setChecked(R.id.cb, item.isSelect)
                }
//                Glide.with(helper.itemView.context)
//                        .load( it.data.pic)
//                        .apply(RequestOptions.bitmapTransform(RoundedCorners(ConvertUtils.dp2px(5f))))
//                        .into(ivAvator)

                //        Glide.with(holder.itemView.getContext())
//                .load(img)
//                .thumbnail(0.1f)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.icon);
                val mation: MultiTransformation<Bitmap> = MultiTransformation(
                    CenterCrop(),
                    RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)
                )
                Glide.with(helper.itemView.context)
                    .load(it.data.pic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions.bitmapTransform(mation))
                    .into(ivAvator)
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

    companion object {
        fun start() {
            if (UserUtils.isLogin()) {
                ActivityUtils.startActivity(CollectionActivity::class.java)
            } else {
                ActivityUtils.startActivity(LoginActivity::class.java)
            }
        }
    }
}
