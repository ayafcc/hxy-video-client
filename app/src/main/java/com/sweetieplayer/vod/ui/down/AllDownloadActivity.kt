package com.sweetieplayer.vod.ui.down

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.databinding.ActivityAllDownloadBinding
import com.sweetieplayer.vod.databinding.ToolBarLayoutBinding
import jaygoo.library.m3u8downloader.M3U8Library
import jaygoo.library.m3u8downloader.view.DownloadItemList
import jaygoo.library.m3u8downloader.view.DownloadingItemList
import jaygoo.library.m3u8downloader.view.adapter.DownloadCenterPagerAdpter

class AllDownloadActivity : BaseActivity() {
    private lateinit var allDownloadBinding: ActivityAllDownloadBinding
    private lateinit var toolBarLayoutBinding: ToolBarLayoutBinding

    private var downloadingItemList: DownloadingItemList? = null
    private var downloadItemList: DownloadItemList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        val fragments = ArrayList<Fragment>()
        allDownloadBinding = ActivityAllDownloadBinding.inflate(layoutInflater)
        setContentView(allDownloadBinding.root)
        toolBarLayoutBinding = ToolBarLayoutBinding.inflate(layoutInflater)

        downloadingItemList = DownloadingItemList()
        downloadItemList = DownloadItemList()
        fragments.add(downloadingItemList!!)
        fragments.add(downloadItemList!!)
        val adpter = DownloadCenterPagerAdpter(supportFragmentManager, fragments, this)
        allDownloadBinding.tabVp.adapter = adpter
        allDownloadBinding.tlDown.setupWithViewPager(allDownloadBinding.tabVp)
        val intentFilter = IntentFilter(M3U8Library.EVENT_REFRESH)
        registerReceiver(receiver, intentFilter)
        toolBarLayoutBinding.backup.setOnClickListener { finish() }
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