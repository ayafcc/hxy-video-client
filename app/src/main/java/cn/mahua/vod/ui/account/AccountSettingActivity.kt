package cn.mahua.vod.ui.account

import android.app.Activity
import android.content.Intent
import android.view.View
import cn.mahua.vod.ApiConfig
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.ChangeAvatorBean
import cn.mahua.vod.bean.LoginBean
import cn.mahua.vod.bean.LogoutBean
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import cn.mahua.vod.utils.UserUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_account_setting.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import java.io.File
class AccountSettingActivity : BaseActivity() {
    override fun getLayoutResID(): Int {
        return R.layout.activity_account_setting
    }

    override fun initView() {
        super.initView()
        val userPortrait = UserUtils.userInfo?.user_portrait
        if (userPortrait.isNullOrEmpty()) {
            ivAvatar.visibility = View.GONE
        } else {
            ivAvatar.visibility = View.VISIBLE
            Glide.with(mActivity)
                    .load(ApiConfig.BASE_URL + "/" + userPortrait)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(ivAvatar)
        }
    }

    override fun initListener() {
        super.initListener()

        rlBack.setOnClickListener {
            finish()
        }

        tvChangeAvator.setOnClickListener {
            openAlbum()
        }
        tvChangeNickname.setOnClickListener {
            ActivityUtils.startActivity(ChangeNicknameActivity::class.java)
        }
        tvLogout.setOnClickListener {
            logout()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            // 图片选择结果回调
            val selectList = PictureSelector.obtainMultipleResult(data)
            if (selectList.size >= 1) {
                val localMedia = selectList[0]
                if (localMedia.isCut) {
                    uploadImage(localMedia.cutPath)
                } else {
                    uploadImage(localMedia.path)
                }
            }
        }
    }

    private fun openAlbum() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(mActivity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_my_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(false)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
    }

    private fun logout() {
        var vodService=Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.logout(),
                object : LoadingObserver<String>(this) {
                    override fun onSuccess(data: String) {
                        UserUtils.logout()
                        EventBus.getDefault().post(LogoutBean())
                        finish()
                    }

                    override fun onError(e: ResponseException) {
                    }

                })
    }

    private fun uploadImage(imagePath: String) {
        val file = File(imagePath)
        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        var vodService=Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.changeAvator(part),
                object : LoadingObserver<ChangeAvatorBean>(this) {
                    override fun onSuccess(data: ChangeAvatorBean) {
                        ToastUtils.showShort(R.string.change_avator_success)
                        EventBus.getDefault().post(LoginBean())
                        ivAvatar.visibility = View.VISIBLE
                        Glide.with(mActivity)
                                .load(file)
                                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                                .into(ivAvatar)
                    }

                    override fun onError(e: ResponseException) {
                    }
                })
    }
}
