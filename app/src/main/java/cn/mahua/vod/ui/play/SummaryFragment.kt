package cn.mahua.vod.ui.play

import android.annotation.SuppressLint
import android.os.Bundle
import cn.mahua.vod.R
import cn.mahua.vod.bean.VodBean
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_summary.*
class SummaryFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_summary
    }

    private lateinit var playActivity : NewPlayActivity

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        playActivity = mActivity as NewPlayActivity
        val vodBean = arguments?.getParcelable(VOD_BEAN) as? VodBean
        vodBean?.run {
            tvTitle.text = vodName
            tvYear.text = "年代：${vod_year}.${type.typeName}.${vod_area}"
            tvActor.text = "主演：$vod_actor"
            tvType.text = "类型：" + vod_class
            tvStatus.text = "状态：$vodRemarks"
            tvPlayNumber.text = "播放：" + vod_hits + "次"
            tvScore.text = "评分：$vod_score"
            tvSummary.text = "   "+vod_blurb
            ivCloseIntro.setOnClickListener {
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
