package com.sweetieplayer.vod.ui.widget

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.View
import com.sweetieplayer.vod.utils.UrlUtils
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.blankj.utilcode.util.ConvertUtils
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.databinding.DialogNoticeBinding



class NoticeDialog(context: Context, val msg: String) : Dialog(context, R.style.DefaultDialogStyle) {
    private lateinit var noticeBinding: DialogNoticeBinding

    init {
        setContentView(R.layout.dialog_notice_tip)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noticeBinding = DialogNoticeBinding.inflate(layoutInflater)
        setContentView(noticeBinding.root)
        window!!.attributes?.apply {
            width = ConvertUtils.dp2px(300f)
            gravity = Gravity.CENTER
        }

        setCanceledOnTouchOutside(false)
        setCancelable(false)


        var index = msg.indexOf("www")
        var index2 = msg.indexOf("com") + 3

        val span = SpannableString(msg)
        span.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(UrlUtils.converKeywordLoadOrSearch(msg.substring(index, index2))))
                startActivity(browserIntent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isAntiAlias = true
                ds.isUnderlineText = false
            }
        }, index, index2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        noticeBinding.tvNotice.setMovementMethod(LinkMovementMethod.getInstance())
        noticeBinding.tvNotice.append(msg)

        noticeBinding.tvNotice.text = span
//        tvNotice.setMovementMethod(LinkMovementMethod.getInstance())
        noticeBinding.ivClose.setOnClickListener {
            dismiss()
        }
    }


}