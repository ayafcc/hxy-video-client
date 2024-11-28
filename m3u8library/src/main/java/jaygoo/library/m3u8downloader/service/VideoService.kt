package jaygoo.library.m3u8downloader.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import jaygoo.library.m3u8downloader.utils.MUtils
import java.io.IOException

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_FOO = "jaygoo.library.m3u8downloader.service.action.FOO"
private const val ACTION_BAZ = "jaygoo.library.m3u8downloader.service.action.BAZ"

// TODO: Rename parameters
private const val EXTRA_PARAM1 = "jaygoo.library.m3u8downloader.service.extra.PARAM1"
private const val EXTRA_PARAM2 = "jaygoo.library.m3u8downloader.service.extra.PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class VideoService : IntentService("VideoService") {

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FOO -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionFoo(param1!!, param2!!)
            }

        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String, param2: String) {
        Log.d(Tag, "我进来了$param1\t$param2")

//        MUtils.decodeFile(param1, "m3u8")
        MUtils.mergeTs2Json(param1, "play")
        Log.d(Tag, "视频合并完成")
    }


    companion object {
        var Tag = "VideoService"

        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, VideoService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }


    }
}
