package cn.mahua.vod.ui.share

import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import cn.mahua.vod.R
import cn.mahua.vod.bean.LoginBean
import cn.mahua.vod.bean.ShareBean
import cn.mahua.vod.bean.ShareInfoBean
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import cn.mahua.vod.utils.SimpleUtils
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import gdut.bsx.share2.FileUtil
import gdut.bsx.share2.Share2
import gdut.bsx.share2.ShareContentType
import kotlinx.android.synthetic.main.activity_share.*
import org.greenrobot.eventbus.EventBus
import java.io.File

class ShareFragment : BaseFragment(){
    private var shareInfo: ShareInfoBean? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_share
    }

    override fun initView() {
        super.initView()
        ivBack.visibility = View.GONE
    }

    override fun initLoad() {
        super.initLoad()
        getShareUrl()
    }

    override fun initListener() {
        super.initListener()
        ivInviteFriend.setOnClickListener {
            PermissionUtils.permission(PermissionConstants.STORAGE)
                    .callback(object : PermissionUtils.SimpleCallback {
                        override fun onGranted() {
                            inviteFriend()
                        }

                        override fun onDenied() {
                            ToastUtils.showShort("需要开启读写权限后才能分享！")
                        }

                    })
                    .request()

        }
        ivCopyLink.setOnClickListener {
            copyLink()
        }
        tvCopy.setOnClickListener {
            copyShareCode()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ShareActivity.REQOUEST_SHARE) {
            shareScore()
        }
    }

    private fun getShareUrl() {
        val vodService= Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(
                this,
                vodService.getShareInfo(),
                object : LoadingObserver<ShareInfoBean>(mActivity) {
                    override fun onSuccess(data: ShareInfoBean) {
                        shareInfo = data
                        data.run {
                            val bitmap = BarcodeEncoder().encodeBitmap(share_url, BarcodeFormat.QR_CODE,
                                    ConvertUtils.dp2px(125f), ConvertUtils.dp2px(125f))
                            ivQrcode.setImageBitmap(bitmap)
                            if (share_url.contains("=")) {
                                val shareCode = share_url.split("=")[1]
                                if (shareCode.isNotEmpty())
                                    tvSharecode.text = shareCode
                            }
                            if (!share_logo.isNullOrEmpty() && mActivity != null && !mActivity.isFinishing) {
                                Glide.with(mActivity)
                                        .load(share_logo)
                                        .into(object : CustomTarget<Drawable>() {
                                            override fun onLoadCleared(placeholder: Drawable?) {
                                            }

                                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                                rlRoot.setBackgroundDrawable(resource)
                                            }

                                        })
                            }
                        }
                    }

                    override fun onError(e: ResponseException) {
                    }

                }
        )
    }

    private fun inviteFriend() {
        val progressDialog = ProgressDialog.show(mActivity, "", StringUtils.getString(R.string.loading_msg))
        ThreadUtils.executeBySingle(object : ThreadUtils.Task<File>() {
            override fun doInBackground(): File {
                val bitmap = SimpleUtils.getCacheBitmapFromView(rlRoot)
                return SimpleUtils.saveBitmapToSdCard(mActivity, bitmap)
            }

            override fun onSuccess(file: File?) {
                progressDialog.dismiss()
                file?.let {
                    shareSingleImage(it.absolutePath)
//                    Share2.Builder(mActivity)
//                            .setContentType(ShareContentType.IMAGE)
//                            .setShareFileUri(FileUtil.getFileUri(mActivity, null, file))
//                            .setTitle("Share Image")
//                            .setOnActivityResult(ShareActivity.REQOUEST_SHARE)
//                            .build()
//                            .shareBySystem()
                } ?: ToastUtils.showShort("分享失败，请重试")
            }

            override fun onFail(t: Throwable?) {
                progressDialog.dismiss()
                ToastUtils.showShort("分享失败，请重试")
            }

            override fun onCancel() {
                progressDialog.dismiss()
            }

        })


    }
    //分享单张图片
    public fun shareSingleImage(file: String) {
        val imageUri = Uri.parse(MediaStore.Images.Media.insertImage(activity?.contentResolver, file, null, null))
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_STREAM, imageUri)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "分享到"), ShareActivity.REQOUEST_SHARE);
    }

    private fun copyLink() {
        shareInfo?.run {
            val clipData = ClipData.newPlainText("", share_url)
            val clipbrardManager = mActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipbrardManager.setPrimaryClip(clipData)
            ToastUtils.showShort("已经复制到剪切板")
        }

    }

    private fun copyShareCode() {
        val clipData = ClipData.newPlainText("", tvSharecode.text.toString())
        val clipbrardManager = mActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipbrardManager.setPrimaryClip (clipData)
        ToastUtils.showShort("已经复制到剪切板")
    }

    private fun shareScore() {
        val vodService= Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(
                this,
                vodService.shareScore(),
                object : BaseObserver<ShareBean>() {
                    override fun onSuccess(data: ShareBean) {
                        if (data.score != "0") {
                            ToastUtils.showShort("分享成功，获得${data.score}积分")
                        } else {
                            ToastUtils.showShort("分享成功")
                        }
                        EventBus.getDefault().post(LoginBean())
                    }

                    override fun onError(e: ResponseException) {
                    }
                }
        )
    }

    companion object{
        @JvmStatic
        fun newInstance(): ShareFragment {
            val args = Bundle()
            val fragment = ShareFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
