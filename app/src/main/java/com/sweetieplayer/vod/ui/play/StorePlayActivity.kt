package com.sweetieplayer.vod.ui.play

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.sweetieplayer.av.play.AvVideoController
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.base.BaseActivity
import com.sweetieplayer.vod.databinding.ActivityNewPlayBinding
import xyz.doikki.videoplayer.player.BaseVideoView
import xyz.doikki.videoplayer.player.VideoView
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class StorePlayActivity : BaseActivity() {

    private lateinit var controller: AvVideoController
    private lateinit var newPlayBinding: ActivityNewPlayBinding

    private var isSeekToHistory: Boolean = false
    private var curProgressHistory: Long = 0
    private var vodDuration: Long = 0
    private var videoNetProgress: Long = 0L
    private var isLandscape = false//当前是否为横屏

    private var localUrl = ""
    private var localName = ""
    override fun getLayoutResID(): Int {
        return R.layout.activity_store_play
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        newPlayBinding.videoView.pause()
    }

    override fun initView() {
        super.initView()

        newPlayBinding = ActivityNewPlayBinding.inflate(layoutInflater)
        setContentView(newPlayBinding.root)

        controller = AvVideoController(newPlayBinding.videoView, this)

        newPlayBinding.videoView.setVideoController(controller)

        localUrl = intent.getStringExtra("play_url")!!
        localName = intent.getStringExtra("play_name")!!
        var file = File(localUrl)


        var ins: List<File> = file.listFiles().filter { it.name.endsWith(".ts") }



        Log.d(TAG, "ins[0]${ins[0].absolutePath}")
        newPlayBinding.videoView.setUrl(ins[0].absolutePath)
        newPlayBinding.videoView.start()
        newPlayBinding.videoView.setOnStateChangeListener(object : BaseVideoView.OnStateChangeListener {

            override fun onPlayerStateChanged(playerState: Int) {
                if (playerState == VideoView.PLAYER_NORMAL) {
                    isLandscape = false
                } else if (playerState == VideoView.PLAYER_FULL_SCREEN) {
                    isLandscape = true
                }
            }

            override fun onPlayStateChanged(playState: Int) {

            }
        })
        controller.findViewById<ImageView>(R.id.iv_av_miracast).visibility = View.GONE
    }


    companion object {
        const val PLAY_LOCAL_URL = "play_local_url"
        const val TAG = "StorePlayActivity"
        fun start(activity: Activity, url: String) {
            val intent = Intent(activity, StorePlayActivity::class.java)
            intent.putExtra(PLAY_LOCAL_URL, url)
            activity.startActivity(intent)
        }
//        fun start() {
//            ActivityUtils.startActivity(StorePlayActivity::class.java, R.anim.slide_in_right, R.anim.no_anim)
//        }
    }

    override fun initListener() {

        controller.setControllerClickListener {
            when (it.id) {

                R.id.iv_av_back, R.id.iv_av_back1, R.id.iv_av_back2 -> {
                    Log.i("bds", "back===========")
                    finish();
                }


            }
        }
    }

    //        super.initListener()
//        controller.setControllerClickListener {
//            when (it.id) {
//                R.id.tv_av_hd ->
//                    chengeNextLine()
//                R.id.iv_av_back, R.id.iv_av_back1, R.id.iv_av_back2 -> {
//                    Log.i("bds", "back===========")
//                    // finish();
//                    App.curPlayScoreBean = null
//                    playScoreInfo = null
//                    savePlayRecord(true)
//                    setResult(3)
//                }
//
//                R.id.iv_av_next ->
//                    playNext()
//                R.id.tv_av_speed ->
//                    showSpeedListDialog(it.tag as Int)
//                R.id.tv_av_selected ->
//                    showPlayListDialog()
//                R.id.tvPlaySource ->
//                    showPlaySourceDialog()
//                R.id.iv_av_miracast ->
//                    showCastScreenDialog()
//                R.id.tvPayButton, R.id.tvEndPayButton -> {
//                    payPlay()
//                }
//                R.id.tvUpdateButton, R.id.tvEndUpdateButton -> {
//                    updateVip()
//                }
//                R.id.btn_pop_danmaku -> {
//                    val s = it.tag as String
//                    sendDanmu(s)
//                }
//            }
//        }
//
//        videoView.setOnVideoViewStateChangeListener(object : OnVideoViewStateChangeListener {
//            override fun onPlayStateChanged(playState: Int) {
//                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
//                    val percentage = getPercentage(curProgressHistory, vodDuration)
//                    println("进度9：=" + controller.percentage + "  2:" + playScoreInfo?.curProgress + " 3=" + curProgressHistory + " 4=" + percentage)
//                    if (percentage <= 0.01f || percentage >= 0.99f) {
//                        println("进度5：==")
////                        playNext()
////                        TODO("播放完毕做判断如果是电视剧")
//                    } else {
//                        println("进度1：==" + curProgressHistory)
//                        controller.setReplayByCurProgress(true)
//                    }
//                } else if (playState == VideoView.STATE_PREPARED) {
//                    isParseSuccess = true
//                    if (isShowPlayProgress) {
//                        Log.i("dsd", "iko===${App.curPlayScoreBean?.curProgress ?: 0}")
//                        videoView.seekTo(playScoreInfo?.curProgress ?: 0)
//                        println("进度3：==" + playScoreInfo?.curProgress)
//                        //从播放记录中点击播放的时候，需要重新插入输入库
////                        playScoreInfo?.let {
////                            LitePal.deleteAll(PlayScoreBean::class.java, "vodId = ?", "${it.vodId}")
////                            playScoreInfo = null
////                        }
//                        isShowPlayProgress = false
//                    } else {
//                        if (isSeekToHistory) {
//                            videoView.seekTo(curProgressHistory)
//                            println("进度2：==" + curProgressHistory)
////                            isSeekToHistory = false
//                        } else {
//                            //跳过30秒片头
//                            if (videoNetProgress == 0L) {
//                                videoView.seekTo(30000)
//                            } else {
//                                videoView.seekTo(videoNetProgress)
//                            }
//                            println("进度4：== videoNetProgress=" + videoNetProgress)
//                        }
//                    }
//                    vodDuration = controller.duration
//                    println("进度12：==" + vodDuration)
//                    when (SPUtils.getInstance().getInt(AvVideoController.KEY_SPEED_INDEX, 3)) {
//                        0 -> {
//                            videoView.setSpeed(2f)
//                            controller.setSpeed("2.00")
//                        }
//                        1 -> {
//                            videoView.setSpeed(1.5f)
//                            controller.setSpeed("1.50")
//                        }
//                        2 -> {
//                            videoView.setSpeed(1.25f)
//                            controller.setSpeed("1.25")
//                        }
//                        3 -> {
//                            videoView.setSpeed(1f)
//                            controller.setSpeed("1.00")
//                        }
//                        4 -> {
//                            videoView.setSpeed(0.75f)
//                            controller.setSpeed("0.75")
//                        }
//                        5 -> {
//                            videoView.setSpeed(0.5f)
//                            controller.setSpeed("0.50")
//                        }
//                    }
//                } else if (playState == VideoView.STATE_ERROR) {
//                    LogUtils.d("=====问题 video OnError")
//                    controller.setReplayByCurProgress(true)
//                    isSeekToHistory = true
//                    curParseIndex++
//                    parseData()
//                }
//            }
//
//            override fun onPlayerStateChanged(playerState: Int) {
//                if (playerState == VideoView.PLAYER_NORMAL) {
//                    isLandscape = false
//                } else if (playerState == VideoView.PLAYER_FULL_SCREEN) {
//                    isLandscape = true
//                }
//            }
//        })
//    }
    fun getPercentage(curPosition: Long, duration: Long): Float {
        val percentage: Float = curPosition / (duration * 1.0f)
        val df = DecimalFormat("#.00")
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'
        df.decimalFormatSymbols = dfs
        return java.lang.Float.valueOf(df.format(percentage.toDouble()))
    }

    //    private fun chengeNextLine() {
//        curParseIndex++
//        parseData()
//    }
//    private fun showPlayListDialog() {
//        if (playList != null) {
//            PlayListDialog(mActivity, urlIndex, playList!!)
//                    .show()
//        }
//    }
//    private fun showPlaySourceDialog() {
//        PlaySourceDialog(mActivity, playSourceIndex, playFormList)
//                .show()
//    }
    override fun initData() {
        super.initData()
    }

    override fun isUseEventBus(): Boolean {
        return super.isUseEventBus()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}