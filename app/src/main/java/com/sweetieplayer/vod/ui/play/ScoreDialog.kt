package com.sweetieplayer.vod.ui.play

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.databinding.LayoutScoreBinding

class ScoreDialog(context: Context) : Dialog(context, R.style.DefaultDialogStyle) {
    private var onScoreSubmitClickListener: OnScoreSubmitClickListener? = null
    private var score : Float  = 0f
    private lateinit var scoreBinding: LayoutScoreBinding

    init {
        setContentView(R.layout.layout_score)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoreBinding = LayoutScoreBinding.inflate(layoutInflater)
        setContentView(scoreBinding.root)
        window!!.attributes = window!!.attributes?.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        scoreBinding.ratingbar.setOnRatingChangeListener {
            score = it
        }

        scoreBinding.tvSubmit.setOnClickListener {
            onScoreSubmitClickListener?.run {
                onScoreSubmit(this@ScoreDialog,score)
            }
        }
    }

    fun setOnScoreSubmitClickListener(onScoreSubmitClickListener: OnScoreSubmitClickListener) : ScoreDialog {
        this.onScoreSubmitClickListener = onScoreSubmitClickListener
        return this
    }


    interface OnScoreSubmitClickListener {
        fun onScoreSubmit(scoreDialog : ScoreDialog, score: Float)
    }
}
