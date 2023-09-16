package cn.mahua.vod.ui.widget

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
import cn.mahua.vod.R
import cn.mahua.vod.databinding.DialogNoticeTip2Binding
import cn.mahua.vod.utils.UrlUtils
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.blankj.utilcode.util.ConvertUtils


class NoticeDialog2(context: Context, val msg: String) : Dialog(context, R.style.DefaultDialogStyle) {
    private lateinit var noticeTip2Binding: DialogNoticeTip2Binding

    init {
        setContentView(R.layout.dialog_notice_tip2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noticeTip2Binding = DialogNoticeTip2Binding.inflate(layoutInflater)
        setContentView(noticeTip2Binding.root)
        window!!.attributes?.apply {
            width = ConvertUtils.dp2px(300f)
            gravity = Gravity.CENTER
        }

        setCanceledOnTouchOutside(false)
        setCancelable(false)

        if (msg.contains("$")) {

            var index = msg.indexOfFirst { it == '$' }
            var index2 = msg.indexOfLast { it == '$' } - 1
            val newMsg: String = msg.replace("$", "")

            if (index < 0 || index2 < 0) {
                return
            }


            val span = SpannableString(newMsg)
            span.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(UrlUtils.converKeywordLoadOrSearch(newMsg.substring(index, index2)))
                    )
                    startActivity(browserIntent)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isAntiAlias = true
                    ds.isUnderlineText = false
                }
            }, index, index2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

            noticeTip2Binding.tvNotice.movementMethod = LinkMovementMethod.getInstance()
            noticeTip2Binding.tvNotice.append(newMsg)

            noticeTip2Binding.tvNotice.text = span
        } else {
            noticeTip2Binding.tvNotice.text = msg
        }
//        tvNotice.setMovementMethod(LinkMovementMethod.getInstance())
        noticeTip2Binding.tvClose.setOnClickListener {
            dismiss()
        }
    }


}