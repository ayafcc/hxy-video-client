package cn.mahua.vod.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ImageUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unused")
public class BlurBanner<T extends BannerData> extends Banner implements OnBannerListener, GlideImageLoader.getBitmapListener {

    private List<T> dataList;
    private onBannerActionListener onBannerActionListener;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        this.setImages(dataList);
        this.setBannerTitles(getTextListByList(dataList));
    }

    public void upDataList(List<T> dataList){
        flag_is_first_bitmap = true;
        this.dataList.clear();
        this.dataList.addAll(dataList);
        this.update(this.dataList,getTextListByList(this.dataList));
    }

    public void setOnBannerActionListener(BlurBanner.onBannerActionListener onBannerActionListener) {
        this.onBannerActionListener = onBannerActionListener;
    }


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (onBannerActionListener != null) {
                onBannerActionListener.onPageChange(position, blurBitmaps.get(dataList.get(position).getBannerImg()));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public BlurBanner(Context context) {
        this(context, null);
    }

    public BlurBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //设置banner样式
        this.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        this.setImageLoader(new GlideImageLoader().setBitmapListener(this));
        //设置banner动画效果
        this.setBannerAnimation(Transformer.Accordion);
        this.setOnPageChangeListener(onPageChangeListener);
        this.setOnBannerListener(this);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility == VISIBLE){
            //LogUtils.a(":) 开始自动播放了！");
            startAutoPlay();
        }else {
            //LogUtils.a(":) 停止自动播放了！");
            stopAutoPlay();
        }
    }

    private boolean flag_is_first_bitmap = true;
    private LinkedHashMap<String, Bitmap> blurBitmaps = new LinkedHashMap<>();

    @Override
    public void getBitmap(Bitmap bitmap, String key) {
        if (blurBitmaps.get(key) == null) {
            //bitmap = ImageUtils.compressBySampleSize(bitmap, 15);
            bitmap = ImageUtils.fastBlur(bitmap, 0.3f, 15);
            blurBitmaps.put(key, bitmap);
        }
        //第一次加载好图片的时候立刻调用一次
        if (flag_is_first_bitmap) {
            if (onBannerActionListener != null)
                onBannerActionListener.onPageChange(-1, bitmap);
            flag_is_first_bitmap = false;
        }
    }

    @Override
    public void OnBannerClick(int position) {
        if (onBannerActionListener != null)
            onBannerActionListener.onBannerClick(position, dataList.get(position));
    }

    public interface onBannerActionListener {

        void onPageChange(int position, Bitmap bitmap);

        void onBannerClick(int position, Object o);
    }

    public static <T extends BannerData> List<String> getTextListByList(List<T> list) {
        if (list == null) return null;
        List<String> stringList = new ArrayList<>();
        for (BannerData bannerData : list) {
            stringList.add(bannerData.getBannerName());
        }
        return stringList;
    }

}
