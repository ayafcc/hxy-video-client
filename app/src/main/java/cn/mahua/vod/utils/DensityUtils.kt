package cn.mahua.vod.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

/**
 * 单位转换 工具类<br></br>
 */
object DensityUtils {

    /**
     * dp转px
     */

    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources
                .displayMetrics
        ).toInt()
    }

    /**
     * sp转px
     */
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spVal, context.resources
                .displayMetrics
        ).toInt()
    }

    /**
     * px转dp
     */
    fun px2dp(context: Context, pxVal: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxVal / scale).toInt()
    }

    /**
     * px转sp
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        val result =
            (pxVal / context.resources.displayMetrics.scaledDensity).toInt()
        return result.toFloat()
    }

    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics() // 创建了一张白纸
        windowManager.defaultDisplay.getMetrics(outMetrics) // 给白纸设置宽高
        return outMetrics.widthPixels
    }

    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    fun getScreenHeight(context: Context): Int {
        val windowManager =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics() // 创建了一张白纸
        windowManager.defaultDisplay.getMetrics(outMetrics) // 给白纸设置宽高
        return outMetrics.heightPixels
    }
}