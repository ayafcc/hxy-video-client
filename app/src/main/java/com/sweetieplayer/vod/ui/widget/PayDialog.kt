package com.sweetieplayer.vod.ui.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.sweetieplayer.vod.App
import com.sweetieplayer.vod.bean.PointPurchseBean
import com.sweetieplayer.vod.bean.StartBean
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.databinding.DialogHitBinding
import com.sweetieplayer.vod.databinding.DialogPayBinding

class PayDialog(context: Context, val data: PointPurchseBean) : Dialog(context, R.style.DefaultDialogStyle) {
    private var onHitDialogClickListener: OnPayDialogClickListener? = null
    private lateinit var hitBinding: DialogHitBinding
    private lateinit var payBinding: DialogPayBinding

    init {
        setContentView(R.layout.dialog_pay)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hitBinding = DialogHitBinding.inflate(layoutInflater)
        payBinding = DialogPayBinding.inflate(layoutInflater)
        setContentView(payBinding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        window!!.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            horizontalMargin = 0.toFloat()
            verticalMargin = 0.toFloat()
            gravity = Gravity.BOTTOM
        }
        window!!.decorView.apply {
            setPadding(0, 0, 0, 0)
        }

        var payments: List<StartBean.Payment> =ArrayList<StartBean.Payment>()
        if (App.startBean != null) {
            payments = App.startBean.payments
        }
        for (payment in payments) {
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            val textView = TextView(context)
            textView.gravity = Gravity.CENTER
            textView.text = payment.name
            textView.setTextColor(ColorUtils.getColor(R.color.textColor))
            textView.setPadding(0, ConvertUtils.dp2px(10f), 0, ConvertUtils.dp2px(10f))
            linearLayout.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            val view = View(context)
            view.setBackgroundColor(ColorUtils.getColor(R.color.lineColor))
            linearLayout.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, 2)
            linearLayout.setOnClickListener {
                onHitDialogClickListener?.onPayTypeClick(this@PayDialog, payment.payment)
            }
            payBinding.llContainer.addView(linearLayout)
        }

        hitBinding.tvCancel.setOnClickListener {
            dismiss()
        }
    }


    fun setOnPayDialogClickListener(onHitDialogClickListener: OnPayDialogClickListener): PayDialog {
        this.onHitDialogClickListener = onHitDialogClickListener
        return this
    }

    abstract class OnPayDialogClickListener {
        open fun onPayTypeClick(dialog: PayDialog, payment: String) {
            dialog.dismiss()
        }
    }
}