package cn.mahua.vod.ui.down

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity

import jaygoo.library.m3u8downloader.M3U8Library
import jaygoo.library.m3u8downloader.view.DownloadItemList
import jaygoo.library.m3u8downloader.view.DownloadingItemList
import jaygoo.library.m3u8downloader.view.adapter.DownloadCenterPagerAdpter
import kotlinx.android.synthetic.main.activity_all_download.*
import kotlinx.android.synthetic.main.tool_bar_layout.*

import java.util.*

class AllDownloadActivity : BaseActivity() {

    private var downloadingItemList: DownloadingItemList? = null
    private var downloadItemList: DownloadItemList? = null
    override fun initView() {
        val fragments = ArrayList<Fragment>()
        downloadingItemList = DownloadingItemList()
        downloadItemList = DownloadItemList()
        fragments.add(downloadingItemList!!)
        fragments.add(downloadItemList!!)
        val adpter = DownloadCenterPagerAdpter(supportFragmentManager, fragments, this)
        tab_vp.adapter = adpter
        tl_down.setupWithViewPager(tab_vp)
        val intentFilter = IntentFilter(M3U8Library.EVENT_REFRESH)
        registerReceiver(receiver, intentFilter)
        backup.setOnClickListener { finish() }
    }

    override fun initData() {}
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_all_download
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AllDownloadActivity::class.java)
            context.startActivity(intent)
        }
    }

    var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == M3U8Library.EVENT_REFRESH) {
                if (downloadingItemList != null) {
                    downloadingItemList!!.refreshList()
                }
                if (downloadItemList != null) {
                    downloadItemList!!.refreshList()
                }
            }
        }
    }
}