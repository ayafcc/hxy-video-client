package cn.mahua.vod.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * @ClassName: ScreenManager
 * @Desciption: 屏幕管理类
 */
class ScreenManager {
    /**
     * 窗口全屏
     */
    fun setFullScreen(isChange: Boolean, mActivity: AppCompatActivity) {
        if (!isChange) {
            return
        }
        mActivity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    /**
     * 沉浸状态栏
     */
    fun setStatusBar(isChange: Boolean, mActivity: Activity) {
        if (!isChange) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            mActivity.getWindow().setStatusBarColor(mActivity.getResources().getColor(R.color.window_status_bar));
// 透明状态栏
            val window = mActivity.window
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN /*| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun setDeepStatusBar(isChange: Boolean, mActivity: Activity): Boolean {
        if (!isChange) {
            return false
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 透明状态栏
            val window = mActivity.window
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN /*| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.WHITE
            //设置状态栏文字颜色及图标为深色
            mActivity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            true
        } else {
            false
        }
    }

    fun setStatusBar(isChange: Boolean, colorResId: Int, mActivity: Activity) {
        if (!isChange) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivity.window
                .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            mActivity.window.statusBarColor = mActivity.resources.getColor(colorResId)
        }
    }

    /**
     * 旋转屏幕
     */
    fun setScreenRoate(isChange: Boolean, mActivity: AppCompatActivity) {
        if (!isChange) {
            mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    companion object {
        private const val TAG = "ScreenManager"
        @JvmStatic
        @get:Synchronized
        var instance: ScreenManager? = null
            get() {
                if (field == null) {
                    field = ScreenManager()
                }
                return field
            }
            private set
    }
}