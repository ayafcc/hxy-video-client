package cn.mahua.vod.utils

import android.app.ActivityManager
import android.content.Context
import android.widget.Toast
import cn.mahua.vod.App


object AgainstCheatUtil {

    @JvmStatic
    fun showWarn(obj: Any?): Boolean {
        if (obj == null) {
            // 1. 通过Context获取ActivityManager
            // 1. 通过Context获取ActivityManager
            val activityManager =  App.getApplication().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

            // 2. 通过ActivityManager获取任务栈
            // 2. 通过ActivityManager获取任务栈
            val appTaskList = activityManager.appTasks

            // 3. 逐个关闭Activity
            // 3. 逐个关闭Activity
            for (appTask in appTaskList) {
                appTask.finishAndRemoveTask()
            }
            // 4. 结束进程
            // System.exit(0);
            Toast.makeText(App.getApplication(), "请关闭代理或者VPN，APP已自动退出", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }
}