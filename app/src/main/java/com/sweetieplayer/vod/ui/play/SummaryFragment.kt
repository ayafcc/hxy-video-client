package com.sweetieplayer.vod.ui.play

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.bean.VodBean
import com.sweetieplayer.vod.databinding.FragmentSummaryBinding

class SummaryFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_summary
    }

    private lateinit var playActivity : NewPlayActivity
    private lateinit var summaryBinding: FragmentSummaryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        summaryBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        return summaryBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        playActivity = mActivity as NewPlayActivity
        val vodBean = arguments?.getParcelable(VOD_BEAN) as? VodBean
        vodBean?.run {
            summaryBinding.tvTitle.text = vodName
            summaryBinding.tvYear.text = "年代：${vod_year}.${type.typeName}.${vod_area}"
            summaryBinding.tvActor.text = "主演：$vod_actor"
            summaryBinding.tvType.text = "类型：" + type_id
            summaryBinding.tvStatus.text = "状态：$vodRemarks"
            summaryBinding.tvPlayNumber.text = "播放：" + vod_hits + "次"
            summaryBinding.tvScore.text = "评分：$vod_score"
            summaryBinding.tvSummary.text = "   "+vod_blurb
            summaryBinding.ivCloseIntro.setOnClickListener {
                playActivity.hideSummary()
                playActivity.showVideoDetail()
            }
        }
    }

    companion object {
        const val VOD_BEAN = "vodBean"

        @JvmStatic
        fun newInstance(vodBean: VodBean): SummaryFragment = SummaryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(VOD_BEAN, vodBean)
            }
        }
    }
}
