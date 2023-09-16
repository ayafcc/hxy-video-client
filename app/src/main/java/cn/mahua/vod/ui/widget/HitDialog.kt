package cn.mahua.vod.ui.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import cn.mahua.vod.R
import cn.mahua.vod.databinding.DialogHitBinding

class HitDialog: Dialog {
    private lateinit var hitBinding: DialogHitBinding
    private var onHitDialogClickListener: OnHitDialogClickListener? = null

    constructor(context : Context) : super(context){
        setContentView(R.layout.dialog_hit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        hitBinding = DialogHitBinding.inflate(layoutInflater)
        setContentView(hitBinding.root)

        hitBinding.tvOk.setOnClickListener {
            onHitDialogClickListener?.onOkClick(this@HitDialog)
        }
        hitBinding.tvCancel.setOnClickListener {
            onHitDialogClickListener?.onCancelClick(this@HitDialog)
        }

    }

    fun setTitle(title : String) : HitDialog{
        hitBinding.tvTitle.text = title
        return this
    }


    fun setMessage(message : String) : HitDialog{
        hitBinding.tvMessage.text = message
        return this
    }

    fun setOnHitDialogClickListener(onHitDialogClickListener: OnHitDialogClickListener): HitDialog {
        this.onHitDialogClickListener = onHitDialogClickListener
        return this
    }

    abstract class OnHitDialogClickListener {
        open fun onCancelClick(dialog: HitDialog) {
            dialog.dismiss()
        }

        open fun onOkClick(dialog: HitDialog) {
            dialog.dismiss()
        }
    }
}