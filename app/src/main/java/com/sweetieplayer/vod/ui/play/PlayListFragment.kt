package com.sweetieplayer.vod.ui.play


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.bean.UrlBean
import com.sweetieplayer.vod.databinding.FragmentPlayListBinding

class PlayListFragment : BaseFragment() {
    private var spanCount = 2
    private var urlIndex = 0
    private lateinit var playActivity: NewPlayActivity
    private lateinit var playListBinding: FragmentPlayListBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        playListBinding = FragmentPlayListBinding.inflate(inflater, container, false)
        return playListBinding.root
    }

    private val selectionAdapter: SelectionAdapter by lazy {
        SelectionAdapter().apply {
            setOnItemClickListener { adapter, view, position ->
                if (urlIndex != position) {
                    urlIndex = position
                    playActivity.changeSelection(position)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_play_list
    }

    override fun initView() {
        super.initView()
        playActivity = mActivity as NewPlayActivity

        arguments?.run {
            spanCount = getInt("SPAN_COUNT")
        }

        playListBinding.rvSelectWorks.layoutManager =
            GridLayoutManager(mActivity, spanCount, RecyclerView.VERTICAL, false).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return spanCount
                    }
                }
            }
        playListBinding.rvSelectWorks.adapter = selectionAdapter

    }

    override fun initListener() {
        super.initListener()
        playListBinding.ivClose.setOnClickListener {
            playActivity.hidePlayList()
            playActivity.showVideoDetail()
        }
    }

    fun showPlayList(playList: List<UrlBean>, urlIndex: Int) {
        this.urlIndex = urlIndex
        selectionAdapter.setNewData(playList)
    }


    companion object {
        @JvmStatic
        fun newInstance(spanCount: Int): PlayListFragment = PlayListFragment().apply {
            arguments = Bundle().apply {
                putInt("SPAN_COUNT", spanCount)
            }
        }
    }

    inner class SelectionAdapter : BaseQuickAdapter<UrlBean, BaseViewHolder>(R.layout.item_play_list) {

        override fun convert(helper: BaseViewHolder, item: UrlBean) {
            if (spanCount == 2) {
                helper.itemView.layoutParams = helper.itemView.layoutParams.apply {
                    width = ConvertUtils.dp2px(130f)
                    height = ConvertUtils.dp2px(50f)
                }
            } else {
                helper.itemView.layoutParams = helper.itemView.layoutParams.apply {
                    width = ConvertUtils.dp2px(50f)
                    height = ConvertUtils.dp2px(50f)
                }
            }
            val position = helper.layoutPosition
            if (position == urlIndex) {
                helper.setTextColor(R.id.tv, ColorUtils.getColor(R.color.userTopBg))
            } else {
                helper.setTextColor(R.id.tv, ColorUtils.getColor(R.color.gray_999))
            }
            var name = item.name.replace("第", "").replace("集", "")
            helper.setText(R.id.tv, name)
        }
    }
}
