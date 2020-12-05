package cn.mahua.vod.ui.widget

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import cn.mahua.vod.R
import cn.mahua.vod.bean.AppUpdateBean
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.dialog_app_update.*

class AppUpdateDialog(context: Context, val data: AppUpdateBean) : Dialog(context, R.style.DefaultDialogStyle) {
    init {
        setContentView(R.layout.dialog_app_update_tip)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCanceledOnTouchOutside(false)
        setCancelable(false)

        window!!.attributes?.apply {
            gravity = Gravity.CENTER
            // width = (ScreenUtils.getScreenWidth() * 0.8).toInt()
            width = (ScreenUtils.getScreenWidth() * 0.98).toInt()
        }


        tvMsg.text = data.summary
        println("data.url"+data.url)
        tvUpdate.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(data.url)).run {
                ActivityUtils.startActivity(this)
            }
//            dismiss();
        }
    }
}