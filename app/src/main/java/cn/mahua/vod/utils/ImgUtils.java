package cn.mahua.vod.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cn.mahua.vod.App;

public class ImgUtils {

    public static Bitmap setGlow(int resourceId) {
        Bitmap bmp = null;
        try {
            int margin = 30;
            int halfMargin = margin / 2;
            int glowRadius = 15;
            int glowColor = Color.RED;
            Bitmap src = BitmapFactory.decodeResource(App.getInstance().getContext().getResources(), resourceId);
            Bitmap alpha = src.extractAlpha();
            bmp = Bitmap.createBitmap(src.getWidth() + margin, src.getHeight() + margin, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            Paint paint = new Paint();
            paint.setColor(glowColor);
            paint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.OUTER));
            canvas.drawBitmap(alpha, halfMargin, halfMargin, paint);
            canvas.drawBitmap(src, halfMargin, halfMargin, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bmp;
    }
}
