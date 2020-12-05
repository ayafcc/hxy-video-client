package cn.mahua.vod.ui.play

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.mahua.vod.R
import cn.mahua.vod.bean.UrlBean
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_play_list.*
import java.lang.ref.WeakReference

class PlayListDialog(context: Context, var urlIndex: Int, val playList: List<UrlBean>) : Dialog(context, R.style.PlayListDialogStyle) {
    private var playActivity: WeakReference<NewPlayActivity> = WeakReference(context as NewPlayActivity)
    private var spanCount = 4

    private val selectionAdapter: SelectionAdapter by lazy {
        SelectionAdapter(playList).apply {
            setOnItemClickListener { adapter, view, position ->
                if (urlIndex != position) {
                    urlIndex = position
                    playActivity.get()?.run {
                        changeSelection(position)
                        changeVideoUrlIndex(position)
                    }
                    notifyDataSetChanged()
                    dismiss()
                }
            }
        }
    }

    init {
        setContentView(R.layout.dialog_play_list)
        spanCount = if (playList[urlIndex].name.contains("期")) {
            2
        } else
            5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.attributes = window!!.attributes.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.MATCH_PARENT
            gravity = Gravity.TOP or Gravity.RIGHT
        }

        rvSelectWorks.layoutManager = GridLayoutManager(context, spanCount, RecyclerView.VERTICAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return spanCount
                }
            }
        }
        rvSelectWorks.adapter = selectionAdapter

    }


    inner class SelectionAdapter(data: List<UrlBean>) : BaseQuickAdapter<UrlBean, BaseViewHolder>(R.layout.item_dialog_play_list, data) {

        override fun convert(helper: BaseViewHolder, item: UrlBean) {
            if (spanCount == 2) {
                helper.itemView.layoutParams = helper.itemView.layoutParams.apply {
                    width = ConvertUtils.dp2px(130f)
                }
            } else {
                helper.itemView.layoutParams = helper.itemView.layoutParams.apply {
                    width = ConvertUtils.dp2px(50f)
                }
            }

            val position = helper.layoutPosition
            if (position == urlIndex) {
                helper.setTextColor(R.id.tv, ColorUtils.getColor(R.color.userTopBg))
            } else {
                helper.setTextColor(R.id.tv, ColorUtils.getColor(R.color.white))
            }
            val name = item.name.replace("第", "").replace("集", "")
            helper.setText(R.id.tv, name)
        }
    }

}
