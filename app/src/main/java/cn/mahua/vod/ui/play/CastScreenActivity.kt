package cn.mahua.vod.ui.play

import android.util.Log
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.PlayFromBean
import cn.mahua.vod.bean.UrlBean
import cn.mahua.vod.bean.VodBean
import cn.mahua.vod.jiexi.BackListener
import cn.mahua.vod.jiexi.JieXiUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils

import kotlinx.android.synthetic.main.activity_cast_screen.*

class CastScreenActivity : BaseActivity() {
//    private lateinit var mVodBean: VodBean
//
//    private var curParseIndex = 0//记录上一次解析到的位置，如果出现解析到是视频不能播放的话 自动解析下一条
//    private var playSourceIndex = 0;//播放源位置
//    private var urlIndex = 0//当前播放的到哪一集
//
//    private lateinit var playFormList: ArrayList<PlayFromBean>
//    private lateinit var playFrom: PlayFromBean //当前播放播放源信息
//    private lateinit var playList: List<UrlBean>//当前播放列表
//    private var isStartPlay = false
//    private val onJiexiResultListener = object : BackListener {
//        override fun onSuccess(url: String?, curParseIndex: Int) {
//            this@CastScreenActivity.curParseIndex = curParseIndex
//            url?.let {
//                startPlay(it)
//            }
//        }
//
//        override fun onError() {
//            Utils.runOnUiThread {
//                tvMsg.text = "嗅探资源失败,请换来源或者联系客服解决！"
//            }
//        }
//
//        override fun onProgressUpdate(msg: String?) {
//            Utils.runOnUiThread {
//                tvMsg.text = msg
//            }
//        }
//    }
//
    override fun getLayoutResID(): Int {
        BarUtils.setStatusBarVisibility(this, false)
        return R.layout.activity_cast_screen
    }
//
//    override fun initData() {
//        super.initData()
//        mVodBean = intent.getParcelableExtra("vod") as VodBean
//        playSourceIndex = intent.getIntExtra("playSourceIndex", 0)
//        urlIndex = intent.getIntExtra("urlIndex", 0)
//        playFormList = intent.getParcelableArrayListExtra<PlayFromBean>(("playFormList"))
//
//        playFrom = playFormList[playSourceIndex]
//        playList = playFrom.urls
//
//        changeTitle()
//        parseData()
//
//    }
//
//    override fun initListener() {
//        super.initListener()
//        ivAvPlay.setOnClickListener {
//            if (isStartPlay) {
//                if (ivAvPlay.isSelected) {
//                    LelinkHelper.getInstance().pause()
//                } else {
//                    LelinkHelper.getInstance().resume()
//                }
//            } else {
//                ToastUtils.showShort("还未加载完成，请稍等")
//            }
//
//        }
//        ivAvNext.setOnClickListener {
//            if (isStartPlay) {
//                playNext()
//            }
//
//        }
//        tvExit.setOnClickListener {
//            LelinkHelper.getInstance().disConnect()
//            finish()
//        }
//        iv_av_back.setOnClickListener {
//            onBackPressedSupport()
//        }
//    }
//
//    override fun onBackPressedSupport() {
//        LelinkHelper.getInstance().back()
//        super.onBackPressedSupport()
//    }
//
//    private fun changeTitle() {
//        Utils.runOnUiThread {
//            var title = mVodBean.vod_name
//            if (mVodBean.type_id == 2) {
//                title += " 第${playList[urlIndex].name}集"
//            }
//            tvTitle.text = title
//        }
//    }
//
//    private fun chengeNextLine() {
//        curParseIndex++
//        parseData()
//    }
//
//    //播放下一集
//    private fun playNext() {
//        if (++urlIndex >= playFrom.urls.size) {
//            ToastUtils.showShort("已经是最后一集")
//        } else {
//            curParseIndex = 0
//            changeTitle()
//            parseData()
//        }
//
//    }
//
//    private fun parseData() {
//        LelinkHelper.getInstance().stop()
//        // 开始解析地址
//        val parse = playFrom.player_info.parse2
//        val url = playList[urlIndex].url
//        if (url.endsWith(".mp4") || url.endsWith(".m3u8")) {
//            startPlay(url)
//        } else {
//            JieXiUtils.INSTANCE.getPlayUrl(parse, url, curParseIndex, onJiexiResultListener)
//        }
//    }
//
//    private fun startPlay(videoUrl: String) {
//        val lelinkPlayInfo = LelinkPlayerInfo().apply {
//            type = LelinkPlayerInfo.TYPE_VIDEO
////            url = "http://jiasu-a5.jiankangkuaile99.com/a5/20191019/%E5%9B%9B%E7%9B%AE%E5%A4%A7%E5%B8%88/2000kb/hls/index.m3u8"
//            url = videoUrl
//        }
//        LelinkHelper.getInstance().setPlayerInfo(lelinkPlayInfo)
//        LelinkHelper.getInstance().setLelinkPlayerListener(object : ILelinkPlayerListener {
//            override fun onLoading() {
//                Utils.runOnUiThread {
//                    tvMsg.text = "正在加载中...."
//                }
//                Log.d("CastScreenActivity", "onLoading")
//            }
//
//            override fun onPause() {
//                Utils.runOnUiThread {
//                    tvMsg.text = "已暂停播放...."
//                }
//                Log.d("CastScreenActivity", "onPause")
//                ivAvPlay.isSelected = false
//            }
//
//            override fun onCompletion() {
//                Utils.runOnUiThread {
//                    playNext()
//                }
//                Log.d("CastScreenActivity", "onCompletion")
//            }
//
//            override fun onStop() {
//                Utils.runOnUiThread {
//                    ivAvPlay.isSelected = false
//                }
//                Log.d("CastScreenActivity", "onStop")
//            }
//
//            override fun onSeekComplete(p0: Int) {
//                Log.d("CastScreenActivity", "onSeekComplete p0 =$p0")
//            }
//
//            override fun onInfo(p0: Int, p1: Int) {
//                Log.d("CastScreenActivity", "onInfo  p0 =$p0; p1 = $p1")
//            }
//
//            override fun onVolumeChanged(p0: Float) {
//                Log.d("CastScreenActivity", "onVolumeChanged")
//            }
//
//            override fun onPositionUpdate(p0: Long, p1: Long) {
//                Log.d("CastScreenActivity", "onPositionUpdate  p0 =$p0; p1 = $p1")
//            }
//
//            override fun onError(p0: Int, p1: Int) {
//                chengeNextLine()
//                Log.d("CastScreenActivity", "onError : p0 =$p0; p1 = $p1")
//            }
//
//            override fun onStart() {
//                Log.d("CastScreenActivity", "onStart")
//                Utils.runOnUiThread {
//                    tvMsg.text = "正在投屏中...."
//                    ivAvPlay.isSelected = true
//                }
//
//                isStartPlay = true
//            }
//
//        })
//        LelinkHelper.getInstance().start()
//    }
}