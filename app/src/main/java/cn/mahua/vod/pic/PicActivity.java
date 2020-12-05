package cn.mahua.vod.pic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;

public class PicActivity extends BaseActivity {

    public static final String KEY_USER_PIC = "KEY_USER_PIC";
    private static Object mObject;

    public static void start(Object o) {
        mObject = null;
        mObject = o;
        ActivityUtils.startActivity(PicActivity.class, R.anim.slide_in_right, R.anim.no_anim);
    }

    @BindView(R.id.iv_pic)
    ImageView imageView;

    @OnClick(R.id.iv_pic_back)
    void onBack() {
        finish();
    }

    @OnClick(R.id.iv_pic_edit)
    void openPic() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(PicActivity.this)
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
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            // 图片选择结果回调
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() >= 1) {
                LocalMedia localMedia = selectList.get(0);
                if (localMedia.isCut()) {
                    setPic(localMedia.getCutPath());
                }
            }
        }
    }

    private void setPic(String path) {
        if (StringUtils.isEmpty(path)) return;
        if (FileUtils.isFileExists(path)) {
            Glide.with(imageView)
                    .load(FileUtils.getFileByPath(path))
                    .transform(new CircleCrop(), new CenterCrop())
                    .into(imageView);
        } else {
            Glide.with(imageView)
                    .load(path)
                    .transform(new CircleCrop(), new CenterCrop())
                    .into(imageView);
        }
        SPUtils.getInstance().put(KEY_USER_PIC,path);
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_pic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        BarUtils.setStatusBarLightMode(this, true);
        if (mObject == null) {
            finish();
            return;
        }
        if (mObject instanceof String) {
            if (FileUtils.isFileExists(mObject.toString())) {
                Glide.with(imageView)
                        .load(FileUtils.getFileByPath(mObject.toString()))
                        .transform(new CircleCrop(), new CenterCrop())
                        .into(imageView);
            } else {
                Glide.with(imageView)
                        .load(mObject.toString())
                        .transform(new CircleCrop(), new CenterCrop())
                        .into(imageView);
            }
        } else {
            Glide.with(imageView)
                    .load(mObject)
                    .transform(new CenterCrop())
                    .into(imageView);
        }
    }

}
