package cn.mahua.vod.ui.play

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import cn.mahua.vod.R
import kotlinx.android.synthetic.main.layout_score.*

class ScoreDialog(context: Context) : Dialog(context,R.style.DefaultDialogStyle) {
    private var onScoreSubmitClickListener: OnScoreSubmitClickListener? = null
    private var score : Float  = 0f

    init {
        setContentView(R.layout.layout_score)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window!!.attributes = window!!.attributes?.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        ratingbar.setOnRatingChangeListener {
            score = it
        }

        tvSubmit.setOnClickListener {
            onScoreSubmitClickListener?.run {
                onScoreSubmit(this@ScoreDialog,score)
            }
        }
    }

    fun setOnScoreSubmitClickListener(onScoreSubmitClickListener: OnScoreSubmitClickListener) : ScoreDialog{
        this.onScoreSubmitClickListener = onScoreSubmitClickListener
        return this
    }


    interface OnScoreSubmitClickListener {
        fun onScoreSubmit(scoreDialog : ScoreDialog,score: Float)
    }
}
