package cn.mahua.vod.ui.play

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import cn.mahua.vod.R
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_play_list.*
import java.lang.ref.WeakReference

class SpeedListDialog(context: Context,listener:OnSpeedItemClickListener,var urlIndex:Int) : Dialog(context, R.style.PlayListDialogStyle) {
    var playList = ArrayList<String>()

    init {
        playList.add("2.0X")
        playList.add("1.5X")
        playList.add("1.25X")
        playList.add("1.0X")
        playList.add("0.75X")
        playList.add("0.5X")
    }

    private var playActivity: WeakReference<NewPlayActivity> = WeakReference(context as NewPlayActivity)

    private val selectionAdapter: SelectionAdapter by lazy {
        SelectionAdapter(playList).apply {
            setOnItemClickListener { adapter, view, position ->
                if (urlIndex != position) {
                    urlIndex = position
                    listener.onSpeedItemClick(playList[position])
                    notifyDataSetChanged()
                    dismiss()
                }
            }
        }
    }

    init {
        setContentView(R.layout.dialog_speed_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.attributes = window!!.attributes.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.MATCH_PARENT
            gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
        }

        rvSelectWorks.layoutManager = LinearLayoutManager(context)
        rvSelectWorks.adapter = selectionAdapter

    }


    inner class SelectionAdapter(data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_dialog_speed_list, data) {

        override fun convert(helper: BaseViewHolder, item: String) {
            helper.itemView.layoutParams = helper.itemView.layoutParams.apply {
                width = ConvertUtils.dp2px(50f)
            }

            val position = helper.layoutPosition
            if (position == urlIndex) {
                helper.setTextColor(R.id.tv, ColorUtils.getColor(R.color.userTopBg))
            } else {
                helper.setTextColor(R.id.tv, ColorUtils.getColor(R.color.white))
            }
            helper.setText(R.id.tv, data[position])
        }
    }
}

interface OnSpeedItemClickListener{

    fun onSpeedItemClick(speed:String)
}
