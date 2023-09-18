package com.sweetieplayer.vod.ui.play

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.databinding.LayoutCommentBinding

class CommentDialog(val mContext : Context) : Dialog(mContext, R.style.DefaultDialogStyle) {
    private var onCommentSubmitClickListener: OnCommentSubmitClickListener? = null
    private lateinit var commentBinding: LayoutCommentBinding
    init {
        setContentView(R.layout.layout_comment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commentBinding = LayoutCommentBinding.inflate(layoutInflater)
        setContentView(commentBinding.root)

        setCanceledOnTouchOutside(false)
        window!!.attributes = window?.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
        }

        commentBinding.tvCancel.setOnClickListener {
            closeSoftInput()
            dismiss()
        }
        commentBinding.etComment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                commentBinding.tvCount.text = "${s?.length ?: 0}/300"
            }
        })

        commentBinding.tvOk.setOnClickListener {
            val comment = commentBinding.etComment.text.trim().toString()
            if (comment.isEmpty()) {
                ToastUtils.showShort("评论内容不能为空")
            } else {
                dismiss()
                closeSoftInput()
                onCommentSubmitClickListener?.onCommentSubmit(comment)
            }
        }
        KeyboardUtils.showSoftInput(commentBinding.etComment)
    }

    fun closeSoftInput(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
                InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun setOnCommentSubmitClickListener(onCommentSubmitClickListener: OnCommentSubmitClickListener) : CommentDialog {
        this.onCommentSubmitClickListener = onCommentSubmitClickListener
        return this
    }


    interface OnCommentSubmitClickListener {
        fun onCommentSubmit(comment: String)
    }
}
