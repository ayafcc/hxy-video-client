package com.sweetieplayer.vod.ui.play

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.sweetieplayer.android.upnpcast.NLUpnpCastManager
import com.sweetieplayer.android.upnpcast.controller.CastObject
import com.sweetieplayer.android.upnpcast.controller.ICastEventListener
import com.sweetieplayer.android.upnpcast.device.CastDevice
import com.sweetieplayer.android.upnpcast.util.CastUtils
import com.sweetieplayer.av.play.AvVideoController
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.bean.PlayFromBean
import com.sweetieplayer.vod.bean.UrlBean
import com.sweetieplayer.vod.bean.VodBean
import com.sweetieplayer.vod.databinding.ActivityCastScreenBinding
import com.sweetieplayer.vod.jiexi.BackListener
import com.sweetieplayer.vod.jiexi.JieXiUtils
import com.sweetieplayer.vod.ui.dlan.Constants
import org.fourthline.cling.support.model.MediaInfo
import org.fourthline.cling.support.model.PositionInfo
import org.fourthline.cling.support.model.TransportInfo

class CastScreenActivity : BaseActivity() {
    private lateinit var mVodBean: VodBean
    private lateinit var controller: AvVideoController
    private var curParseIndex = 0//记录上一次解析到的位置，如果出现解析到是视频不能播放的话 自动解析下一条
    private var playSourceIndex = 0;//播放源位置
    private var urlIndex = 0//当前播放的到哪一集
    private var vodurl = ""
    private var vodLong = 1L
    private var device: CastDevice? = null

    private lateinit var playFormList: ArrayList<PlayFromBean>
    private lateinit var playFrom: PlayFromBean //当前播放播放源信息
    private lateinit var playList: List<UrlBean>//当前播放列表
    private var isStartPlay = false
    private lateinit var castScreenBinding: ActivityCastScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        castScreenBinding = ActivityCastScreenBinding.inflate(layoutInflater)
        setContentView(castScreenBinding.root)
    }

    private val onJiexiResultListener = object : BackListener {
        override fun onSuccess(url: String?, curParseIndex: Int, headers: Map<String?, String?>?) {
            this@CastScreenActivity.curParseIndex = curParseIndex
            url?.let {
//                startPlay(it)
            }
        }

        override fun onError() {
            ThreadUtils.runOnUiThread {
                castScreenBinding.tvMsg.text = "获取资源失败,请换来源或者联系客服解决！"
            }
        }

        override fun onProgressUpdate(msg: String?) {
            ThreadUtils.runOnUiThread {
                castScreenBinding.tvMsg.text = msg
            }
        }
    }

    override fun getLayoutResID(): Int {
        BarUtils.setStatusBarVisibility(this, false)
        return R.layout.activity_cast_screen
    }

    override fun initData() {
        super.initData()
        mVodBean = intent.getParcelableExtra("vod")!!
        playSourceIndex = intent.getIntExtra("playSourceIndex", 0)
        urlIndex = intent.getIntExtra("urlIndex", 0)
        vodurl = intent.getStringExtra("vodurl")!!
        vodLong = intent.getLongExtra("vodLong", 1)
//        device = intent.getParcelableExtra("device")
        playFormList = intent.getParcelableArrayListExtra<PlayFromBean>(("playFormList"))!!

        playFrom = playFormList[playSourceIndex]
        playList = playFrom.urls
        println("详情${mVodBean}")
        println("详情视频长度${vodLong}")


//                    NLUpnpCastManager.getInstance().connect(castDevice);
//
        NLUpnpCastManager.getInstance().cast(
            CastObject
                .newInstance(vodurl, Constants.CAST_ID, mVodBean.vod_name)
                .setDuration(vodLong)
        );
        changeTitle()
        NLUpnpCastManager.getInstance().addCastEventListener(mControlListener)
//        parseData()

        startPlay(vodurl)
    }

    override fun initListener() {
        super.initListener()
        castScreenBinding.ivAvPlay.setOnClickListener {
            if (isStartPlay) {
                if (castScreenBinding.ivAvPlay.isSelected) {
                    NLUpnpCastManager.getInstance().pause()


                } else {
                    NLUpnpCastManager.getInstance().start()
                }
            } else {
                ToastUtils.showShort("还未加载完成，请稍等")
            }

        }
        castScreenBinding.ivAvNext.setOnClickListener {
            if (isStartPlay) {
                playNext()
            }

        }
        castScreenBinding.tvExit.setOnClickListener {
            NLUpnpCastManager.getInstance().stop()
            finish()
        }
        castScreenBinding.ivAvBack.setOnClickListener {

            onBackPressedSupport()
            //NLUpnpCastManager.getInstance().stop() //返回退出投屏
        }
    }


    private fun changeTitle() {
        ThreadUtils.runOnUiThread {
            var title = mVodBean.vod_name
            if (mVodBean.type_id == 2) {
                title += " 第${playList[urlIndex].name}集"
            }
            castScreenBinding.tvTitle.text = title
        }
    }

    private fun chengeNextLine() {
        curParseIndex++
        parseData()
    }

    //
//    //播放下一集
    private fun playNext() {
        if (++urlIndex >= playFrom.urls.size) {
            ToastUtils.showShort("已经是最后一集")
        } else {
            curParseIndex = 0
            changeTitle()
            parseData()
        }

    }

    private fun parseData() {

        // 开始解析地址
        val parse = playFrom.player_info.parse2
        val url = playList[urlIndex].url
        if (url.endsWith(".mp4") || url.endsWith(".m3u8")) {
            startPlay(url)
        } else {
            JieXiUtils.INSTANCE.getPlayUrl(parse, url, curParseIndex, onJiexiResultListener)
        }
    }

    private fun startPlay(videoUrl: String) {


        NLUpnpCastManager.getInstance().cast(
            CastObject
                .newInstance(videoUrl, Constants.CAST_ID, mVodBean.vod_name)
                .setDuration(vodLong)
        );


    }

    private val mControlListener: ICastEventListener = object : ICastEventListener {
        override fun onConnecting(castDevice: CastDevice) {
//            mCastDeviceInfo.setText(String.format("设备状态: [%s] [正在连接]", castDevice.getName()));
            Toast.makeText(this@CastScreenActivity, "正在连接", Toast.LENGTH_SHORT).show()
            ThreadUtils.runOnUiThread {
                castScreenBinding.tvMsg.text = "正在加载中...."
            }
        }

        override fun onConnected(
            castDevice: CastDevice,
            transportInfo: TransportInfo,
            mediaInfo: MediaInfo?,
            volume: Int
        ) {
            Log.d("SampleControlVideo", String.format("播放状态: [%s]", "已连接"))
            Log.d("SampleControlVideo", String.format("播放状态: [%s]", transportInfo.currentTransportState.value))
            Log.d(
                "SampleControlVideo",
                String.format("视频信息: [%s]", if (mediaInfo != null) mediaInfo.currentURI else "NULL")
            )
//            Log.d("SampleControlVideo", String.format("播放状态: [%s]", "断开连接"))

            Toast.makeText(this@CastScreenActivity, "已连接", Toast.LENGTH_SHORT).show()
            ThreadUtils.runOnUiThread {
                castScreenBinding.tvMsg.text = "正在投屏中...."
                castScreenBinding.ivAvPlay.isSelected = true
            }

            isStartPlay = true
        }

        override fun onDisconnect() {

            Log.d("SampleControlVideo", String.format("播放状态: [%s]", "断开连接"))
            Toast.makeText(this@CastScreenActivity, "断开连接", Toast.LENGTH_SHORT).show()
        }

        override fun onCast(castObject: CastObject) {

//            Toast.makeText(this@CastScreenActivity2, "开始投射 " + castObject.url, Toast.LENGTH_SHORT).show()
//            Toast.makeText(this@CastScreenActivity2, "开始投射 " + castObject.url, Toast.LENGTH_SHORT).show()
        }

        override fun onStart() {
            Toast.makeText(this@CastScreenActivity, "开始播放", Toast.LENGTH_SHORT).show()
            Log.d("SampleControlVideo", String.format("播放状态: [%s]", "开始播放"))

            ThreadUtils.runOnUiThread {
                castScreenBinding.tvMsg.text = "正在投屏中...."
                castScreenBinding.ivAvPlay.isSelected = true
            }

            isStartPlay = true
            //            mCastStatusInfo.setText(String.format("播放状态: [%s]", "开始播放"));
        }

        override fun onPause() {
            Toast.makeText(this@CastScreenActivity, "暂停播放", Toast.LENGTH_SHORT).show()
            Log.d("SampleControlVideo", String.format("播放状态: [%s]", "暂停播放"))

            ThreadUtils.runOnUiThread {
                castScreenBinding.tvMsg.text = "已暂停播放...."
                castScreenBinding.ivAvPlay.isSelected = false
            }
            //            mCastStatusInfo.setText(String.format("播放状态: [%s]", "暂停播放"));
        }

        override fun onStop() {
            Toast.makeText(this@CastScreenActivity, "停止投射", Toast.LENGTH_SHORT).show()
            ThreadUtils.runOnUiThread {
                castScreenBinding.ivAvPlay.isSelected = false
            }
            //clear all UI
            run {
                Log.d("SampleControlVideo", "播放状态: ")
                Log.d("SampleControlVideo", "视频信息: ")
                Log.d("SampleControlVideo", "播放状态: ")
                Log.d("SampleControlVideo", "播放状态: ")
                Log.d("SampleControlVideo", "播放状态: ")
            }
        }

        override fun onSeekTo(position: Long) {
            Toast.makeText(this@CastScreenActivity, "快进 " + CastUtils.getStringTime(position), Toast.LENGTH_SHORT)
                .show()
        }

        override fun onError(errorMsg: String) {
            Toast.makeText(this@CastScreenActivity, "错误：$errorMsg", Toast.LENGTH_SHORT).show()
        }

        override fun onVolume(volume: Int) {
            Toast.makeText(this@CastScreenActivity, "音量：$volume", Toast.LENGTH_SHORT).show()
            Log.d("SampleControlVideo", "播放状态: ")
            //            mVolumeBar.setProgress(volume);
        }

        override fun onBrightness(brightness: Int) {
            Toast.makeText(this@CastScreenActivity, "亮度：$brightness", Toast.LENGTH_SHORT).show()
        }

        override fun onUpdatePositionInfo(positionInfo: PositionInfo) {
//            Log.d("SampleControlVideo", String.format("%s/%s", positionInfo.relTime, positionInfo.trackDuration))
            //            Log.d("SampleControlVideo", (int) (positionInfo.getElapsedPercent() / 100f * mDurationBar.getMax()));
//            mCastPosition.setText(String.format("%s/%s", positionInfo.getRelTime(), positionInfo.getTrackDuration()));

//            mDurationBar.setProgress((int) (positionInfo.getElapsedPercent() / 100f * mDurationBar.getMax()));
        }
    }

    override fun onResume() {
        super.onResume()
        NLUpnpCastManager.getInstance().bindUpnpCastService(this)
    }

    override fun onPause() {
        NLUpnpCastManager.getInstance().unbindUpnpCastService(this)
        super.onPause()
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    override fun onDestroy() {
        NLUpnpCastManager.getInstance().stop()
        NLUpnpCastManager.getInstance().disconnect()
        NLUpnpCastManager.getInstance().removeCastEventListener(mControlListener)
        super.onDestroy()
    }

}