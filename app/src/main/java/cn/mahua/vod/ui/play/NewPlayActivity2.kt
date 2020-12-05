//package cn.mahua.vod.ui.play
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.util.Log
//import android.view.KeyEvent
//import androidx.localbroadcastmanager.content.LocalBroadcastManager
//import cn.whiner.av.CheckVodTrySeeBean
//import cn.whiner.av.play.AvVideoController
//import cn.mahua.vod.App
//import cn.mahua.vod.R
//import cn.mahua.vod.base.BaseActivity
//import cn.mahua.vod.bean.PlayFromBean
//import cn.mahua.vod.bean.PlayScoreBean
//import cn.mahua.vod.bean.UrlBean
//import cn.mahua.vod.bean.VodBean
//import cn.mahua.vod.jiexi.BackListener
//import cn.mahua.vod.jiexi.JieXiUtils
//import cn.mahua.vod.jiexi.JieXiUtils2
//import cn.mahua.vod.ui.login.LoginActivity
//import cn.mahua.vod.ui.pay.PayActivity
//import cn.mahua.vod.ui.widget.HitDialog
//import cn.mahua.vod.utils.UserUtils
//import com.blankj.utilcode.util.*
//import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener
//import com.dueeeke.videoplayer.player.VideoView
//import com.github.StormWyrm.wanandroid.base.exception.ResponseException
//import com.github.StormWyrm.wanandroid.base.net.RequestManager
//import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
//import com.github.StormWyrm.wanandroid.base.net.observer.PlayLoadingObserver
//import kotlinx.android.synthetic.main.activity_new_play.*
//import java.util.*
//import kotlin.collections.ArrayList
//
//class NewPlayActivity2 : BaseActivity() {
//    private val TAG = "NewPlayActivity2"
//
//    private lateinit var controller: AvVideoController
//    private lateinit var mVodBean: VodBean
//    private var isShowPlayProgress = false
//    private var curPlayUrl: String = ""
//    private var isAllowCastScreen: Boolean = false//根据视频的类型，来判断是否可以投屏
//    private var curParseIndex = 0;//记录上一次解析到的位置，如果出现解析到是视频不能播放的话 自动解析下一条
//    private var isPlay = false//是否正在播放
//    private var playSourceIndex = 0;//播放源位置
//    private var urlIndex = 0//当前播放的到哪一集
//    private lateinit var playFormList: List<PlayFromBean>
//    private lateinit var playFrom: PlayFromBean //当前播放播放源信息
//    private var playList: List<UrlBean>? = null//当前播放列表
//    private var playScoreInfo: PlayScoreBean? = null
//
//    private val onJiexiResultListener = object : BackListener {
//        override fun onSuccess(url: String?, curParseIndex: Int) {
//            println("===Jiexi onSuccess 坐标：$curParseIndex url=${url}")
//            if (isSuccess) {
//                return
//            }
//            if (url == null || url.isEmpty()) {
//                chengeNextLine()
//                return
//            }
//            url?.let {
//                if (!isPlay) {
//                    play(it)
//                    curPlayUrl = it
//                    isPlay = true
//                }
//            }
//
//
//        }
//
//        override fun onError() {
//            controller.updateJiexiProgess("嗅探资源失败,请换来源或者联系客服解决！")
//        }
//
//        override fun onProgressUpdate(msg: String?) {
//            controller.updateJiexiProgess(msg)
//        }
//    }
//    private var videoDetailFragment: VideoDetailFragment? = null
//    private var summaryFragment: SummaryFragment? = null
//    private var playListFragment: PlayListFragment? = null
//    private var isParsed: Boolean = false
//    private var isLandscape = false//当前是否为横屏
//
//    override fun getLayoutResID(): Int {
//        return R.layout.activity_new_play
//    }
//
//    override fun initView() {
//        super.initView()
//        BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.player_status_color))
//        mVodBean = intent.getParcelableExtra(PlayActivity.KEY_VOD) as VodBean
//        isShowPlayProgress = intent.getBooleanExtra(PlayActivity.KEY_SHOW_PROGRESS, false)
//        if (mVodBean == null) {
//            finish()
//        }
//        controller = AvVideoController(videoView, this)
//
//        videoView.setVideoController(controller)
//
//        registerReceiver()
//    }
//
//    override fun initListener() {
//        super.initListener()
//        controller.setControllerClickListener {
//            when (it.id) {
////                R.id.tv_av_hd ->
////                    chengeNextLine()
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
//                R.id.tv_av_selected ->
//                    showPlayListDialog()
//                R.id.tvPlaySource ->
//                    showPlaySourceDialog()
//                R.id.iv_av_miracast ->
//                    showCastScreenDialog()
//                R.id.tvPayButton, R.id.tvEndPayButton -> {
//                    payPlay()
//                }
//                R.id.tvUpdateButton, R.id.tvEndUpdateButton ->
//                    updateVip()
//            }
//        }
//
//        videoView.setOnVideoViewStateChangeListener(object : OnVideoViewStateChangeListener {
//            override fun onPlayStateChanged(playState: Int) {
//                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
//                    playNext()
//                } else if (playState == VideoView.STATE_PREPARED) {
//                    if (isShowPlayProgress) {
//                        Log.i("dsd", "iko===${App.curPlayScoreBean?.curProgress ?: 0}")
//
//                        videoView.seekTo(playScoreInfo?.curProgress ?: 0)
//
//                        //从播放记录中点击播放的时候，需要重新插入输入库
////                        playScoreInfo?.let {
////                            LitePal.deleteAll(PlayScoreBean::class.java, "vodId = ?", "${it.vodId}")
////                            playScoreInfo = null
////                        }
//                        isShowPlayProgress = false
//                    }
//                } else if (playState == VideoView.STATE_ERROR) {
//                    curParseIndex++
//                    parseData()
//                    println("===Jiexi ==videoView")
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
//
//    override fun initData() {
//        super.initData()
//        getVideoDetail()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        videoView.resume()
//        if (isParsed) {
//            checkVodTrySee()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        videoView.pause()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        JieXiUtils.INSTANCE.stopGet()
//        controller.onDestroy()
//        videoView.release()
//        lbm?.unregisterReceiver(localReceiver)
//        cancelTimer()
//    }
//
//    override fun onBackPressedSupport() {
//        if (!videoView.onBackPressed()) {
//            try {
//                recordPlay()//播放记录可能为空
//            } catch (e: Exception) {
//            } finally {
//                super.onBackPressedSupport()
//            }
//        }
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            App.curPlayScoreBean = null
//            playScoreInfo = null
//            savePlayRecord(true)
//            setResult(3)
//            finish()
//            false
//        } else {
//            super.onKeyDown(keyCode, event)
//        }
//    }
//
//
//    fun showSummary() {
//        if (summaryFragment == null) {
//            summaryFragment = SummaryFragment.newInstance(mVodBean)
//            supportFragmentManager.beginTransaction()
//                    .add(R.id.flContainer, summaryFragment!!)
//                    .commitAllowingStateLoss()
//        } else {
//            supportFragmentManager.beginTransaction()
//                    .show(summaryFragment!!)
//                    .commitAllowingStateLoss()
//        }
//    }
//
//    fun hideSummary() {
//        supportFragmentManager.beginTransaction()
//                .hide(summaryFragment!!)
//                .commitAllowingStateLoss()
//    }
//
//    fun showVideoDetail() {
//        if (videoDetailFragment == null) {
//            videoDetailFragment = VideoDetailFragment.newInstance(mVodBean, urlIndex, playSourceIndex)
//            supportFragmentManager.beginTransaction()
//                    .add(R.id.flContainer, videoDetailFragment!!)
//                    .commitNowAllowingStateLoss()
//        } else {
//            videoDetailFragment?.changeCurIndex(urlIndex)
//            supportFragmentManager.beginTransaction()
//                    .show(videoDetailFragment!!)
//                    .commitNowAllowingStateLoss()
//        }
//    }
//
//    fun showPlayList() {
//        if (playListFragment == null) {
//            val spanCount = if (mVodBean.type_id == 3) {
//                2
//            } else {
//                5
//            }
//            playListFragment = PlayListFragment.newInstance(spanCount).apply {
//                if (playList != null) {
//                    showPlayList(playList!!, urlIndex)
//                }
//            }
//
//            supportFragmentManager.beginTransaction()
//                    .add(R.id.flContainer, playListFragment!!)
//                    .commitAllowingStateLoss()
//        } else {
//            playListFragment?.run {
//                if (playList != null) {
//                    showPlayList(playList!!, urlIndex)
//                }
//            }
//            supportFragmentManager.beginTransaction()
//                    .show(playListFragment!!)
//                    .commitAllowingStateLoss()
//        }
//    }
//
//    fun hidePlayList() {
//        supportFragmentManager.beginTransaction()
//                .hide(playListFragment!!)
//                .commitAllowingStateLoss()
//    }
//
//    fun showNewVideo(vodBean: VodBean) {
//        recordPlay()//缓存上一个视频的进度
//        App.curPlayScoreBean = null
//        playScoreInfo = null
//        mVodBean = vodBean
//        supportFragmentManager.beginTransaction()
//                .remove(videoDetailFragment!!)
//                .commitNowAllowingStateLoss()
//        videoDetailFragment = null
//
//        if (summaryFragment != null) {
//            supportFragmentManager.beginTransaction()
//                    .remove(summaryFragment!!)
//                    .commitNowAllowingStateLoss()
//            summaryFragment = null
//
//        }
//
//        if (playListFragment != null) {
//            supportFragmentManager.beginTransaction()
//                    .remove(playListFragment!!)
//                    .commitNowAllowingStateLoss()
//            playListFragment = null
//        }
//
//        videoView.release()
//        controller.setTitle(mVodBean.vodName)
//        getVideoDetail()
//    }
//
//    fun changeSelection(position: Int) {
//        urlIndex = position//更改当前选集
//        this.curParseIndex = 0
//        parseData()
//        println("===Jiexi ==changeSelection")
//    }
//
//    fun changeVideoUrlIndex(position: Int = -1) {
//        videoDetailFragment?.changeCurIndex(urlIndex)
//        this.curParseIndex = 0
//    }
//
//    fun changePlaySource(playFromBean: PlayFromBean, playSourceIndex: Int) {
//        this.playFrom = playFromBean
//        this.playList = playFrom.urls
//        this.playSourceIndex = playSourceIndex
//        this.curParseIndex = 0
//        parseData()
//        println("===Jiexi ==changePlaySource")
//        videoDetailFragment?.changePlaysource(playSourceIndex)
//    }
//
//    fun castScreen() {
//        Intent(this, CastScreenActivity::class.java).apply {
//            putExtra("vod", mVodBean)
//            putExtra("playSourceIndex", playSourceIndex)
//            putExtra("urlIndex", urlIndex)
//            val newPlayFromList = ArrayList<PlayFromBean>()
//            playFormList.map {
//                newPlayFromList.add(it)
//            }
//            putParcelableArrayListExtra("playFormList", newPlayFromList)
//            ActivityUtils.startActivity(this)
//        }
//
//    }
//
//    private fun changeTitle() {
//        var title = mVodBean.vod_name
//        if (mVodBean.type_id == 2) {
//            if (playList != null) {
//                title += " ${playList!![urlIndex].name}"
//            }
//        }
//        controller.setTitle(title)
//    }
//
//    private fun chengeNextLine() {
//        curParseIndex++
//        parseData()
//        println("===Jiexi ==chengeNextLine")
//    }
//
//    private fun showPlayListDialog() {
//        if (playList != null) {
//            PlayListDialog(mActivity, urlIndex, playList!!)
//                    .show()
//        }
//    }
//
//    private fun showPlaySourceDialog() {
//        PlaySourceDialog(mActivity, playSourceIndex, playFormList)
//                .show()
//    }
//
//    private fun showCastScreenDialog() {
//        if (isAllowCastScreen) {
//            val dialogHeight = if (isLandscape) {
//                -1
//            } else {
//                videoView.height
//            }
//            CastScreenDialog(mActivity, dialogHeight)
//                    .show()
//        } else {
//            ToastUtils.showShort("暂无投屏权限请升级会员或购买")
//        }
//    }
//
//    private fun getVideoDetail() {
//        RequestManager.execute(this, vodService.getVideoDetail(mVodBean.vod_id, 10),
//                object : PlayLoadingObserver<VodBean>(mActivity) {
//                    override fun onSuccess(data: VodBean) {
//                        isParsed = true
//                        mVodBean = data
//                        curParseIndex = 0
//                        // playScoreInfo = LitePal.where("vodId = ?", data.vod_id.toString()).findFirst(PlayScoreBean::class.java)
//                        playScoreInfo = App.curPlayScoreBean
//                        urlIndex = playScoreInfo?.urlIndex ?: 0//获取播放记录中的选集
//                        playSourceIndex = playScoreInfo?.playSourceIndex ?: 0//获取播放源
//                        playFormList = data.vod_play_list
//                        if (playFormList.isNullOrEmpty()) {
//                            HitDialog(this@NewPlayActivity2)
//                                    .setTitle(StringUtils.getString(R.string.tip))
//                                    .setMessage("无播放地址，联系客服添加")
//                                    .setOnHitDialogClickListener(object : HitDialog.OnHitDialogClickListener() {
//                                        override fun onCancelClick(dialog: HitDialog) {
//                                            super.onCancelClick(dialog)
//                                            finish()
//                                        }
//
//                                        override fun onOkClick(dialog: HitDialog) {
//                                            super.onOkClick(dialog)
//                                            finish()
//                                        }
//                                    })
//                                    .show()
//                            return
//                        }
////                        if (data.vod_play_list != null) {
//                        playFrom = playFormList[playSourceIndex]
////                        }
//                        playList = playFrom.urls
//                        parseData()
//                        println("===Jiexi ==getVideoDetail")
//                        showVideoDetail()
//                    }
//
//                    override fun onError(e: ResponseException) {
//                        finish()
//                    }
//
//                })
//
//    }
//
//    private fun checkVodTrySee() {
//        if (playList == null) {
//            return
//        }
//        RequestManager.execute(
//                mActivity,
//                vodService.checkVodTrySee(mVodBean.vod_id.toString(), 1.toString(), playList!![urlIndex].nid.toString()),
//                object : BaseObserver<CheckVodTrySeeBean>() {
//                    override fun onSuccess(data: CheckVodTrySeeBean) {
//                        var isVip = false
//                        if (UserUtils.isLogin() && UserUtils.userInfo?.group_id == 3) {
//                            isVip = true
//                        }
//                        val status = data.status
//                        isAllowCastScreen = status == 0 || (isVip && status == 1)
//                        controller.CheckVodTrySeeBean(data, isVip, mVodBean.vod_points_play)
//                    }
//
//                    override fun onError(e: ResponseException) {
//                        isAllowCastScreen = false
//                    }
//                })
//    }
//
//    private fun parseData() {
//        if (isPlay) {
//            videoView.release()
//        }
//        isPlay = false
//        showPlayerAd()
//        showAnnouncement()
//        // 开始解析地址
//        val parse = playFrom.player_info.parse2
//        var url: String = ""
//        if (playList != null) {
//            url = playList!![urlIndex].url
//        }
//        checkVodTrySee()
//        changeTitle()
//        if (url.endsWith(".mp4") || url.endsWith(".m3u8")) {
//            isPlay = true
//            curPlayUrl = url
//            play(url)
//        } else {
//            isSuccess = false
//            controller.showJiexi()
//            JieXiUtils2.INSTANCE.getPlayUrl(parse, url,curParseIndex, onJiexiResultListener)
//        }
//    }
//
//    private fun showPlayerAd() {
//        App.playAd?.let {
//            if (it.img.isNotEmpty()) {
//                controller.showAd(it.img, it.url)
//            }
//        }
//    }
//
//    private fun showAnnouncement() {
//        runOnUiThread {
//            App.startBean?.document?.roll_notice?.let {
//                if (it.content.isNotEmpty() && it.status == "1") {
//                    controller.showAnnouncement(it.content)
//                }
//            }
//        }
//    }
//
//    private var isSuccess = false
//
//    private fun play(url: String) {
//        isSuccess = true
//        println("===Jiexi url=" + url)
//        startTimer()
//        controller.hideJiexi()
//        videoView.post {
//            if (url.startsWith("//")) {
//                videoView.setUrl("https:$url")
//            } else {
//                videoView.setUrl(url)
//            }
//            videoView.start()
////            controller.setCurProgress(playScoreInfo?.percentage ?: 0f)
//        }
//    }
//
//    //将当前记录缓存下来
//    private fun recordPlay() {
//        if (playScoreInfo == null) {
//            playScoreInfo = PlayScoreBean().apply {
//                vodId = mVodBean.vod_id
//                typeId = mVodBean.type_id
//                vodName = mVodBean.vod_name
//                vodImgUrl = mVodBean.vod_pic
//                percentage = controller.percentage
//                curProgress = controller.curProgress
//                urlIndex = this@NewPlayActivity2.urlIndex
//                if (playList != null) {
//                    vodSelectedWorks = this@NewPlayActivity2.playList!![urlIndex].name
//                }
//                playSourceIndex = this@NewPlayActivity2.playSourceIndex
//                save()
//            }
//        } else {
//            playScoreInfo?.run {
//                percentage = controller.percentage
//                curProgress = controller.curProgress
//                urlIndex = this@NewPlayActivity2.urlIndex
//                if (playList != null) {
//                    vodSelectedWorks = this@NewPlayActivity2.playList!![urlIndex].name
//                }
//                playSourceIndex = this@NewPlayActivity2.playSourceIndex
//                saveOrUpdate("vodId = ?", mVodBean.vod_id.toString())
//            }
//        }
//    }
//
//
//    private fun savePlayRecord(isClose: Boolean) {
//
//        // var percentage = controller.percentage
//        var curProgress = controller.curProgress
//
//
//        var voidid = mVodBean.vod_id.toString()
//        Log.e(TAG, "======voidid===$voidid")
//
//        var percentage = controller.percentage.toString()
//        var urlIndex = this@NewPlayActivity2.urlIndex
//
//        if (this@NewPlayActivity2.playList.isNullOrEmpty() && isClose) {
//            finish()
//            return
//        }
//
//        var vodSelectedWorks = this@NewPlayActivity2.playList!![urlIndex].name
//
//        var playSourceIndex = this@NewPlayActivity2.playSourceIndex;
//
//        var playSource = ""
//        if (mVodBean != null && mVodBean.vod_play_list != null) {
//            val playFromBean = mVodBean!!.vod_play_list!!.get(playSourceIndex)
//            val player_info = playFromBean.player_info
//            val urls = playFromBean.urls
//            playSource = player_info.show
//        }
//        if (StringUtils.isEmpty(playSource)) {
//            playSource = "默认"
//        }
//
//        Log.i("play", "source=xjjj${playSourceIndex}${curProgress}")
//
//        if (UserUtils.isLogin()) {
//            RequestManager.execute(this, vodService.addPlayLog(voidid, vodSelectedWorks, playSource.toString(), percentage, urlIndex.toString(), curProgress.toString(), playSourceIndex.toString()),
//                    object : BaseObserver<String>() {
//                        override fun onSuccess(data: String) {
//                            Log.i("play", "addPlayLogsucess")
//                            val intent = Intent("android.intent.action.AddPlayScore")
//                            sendBroadcast(intent)
//                            if (isClose) {
//                                finish()
//                            }
//                        }
//
//                        override fun onError(e: ResponseException) {
//                            Log.i("play", "addPlayfaied")
//                            if (isClose) {
//                                finish()
//                            }
//                        }
//                    })
//        } else {
//            if (isClose) {
//                finish()
//            }
//        }
//
//
////        RequestManager.execute(
////                mActivity,
////                vodService.shareScore(),
////                object : BaseObserver<ShareBean>() {
////                    override fun onSuccess(data: ShareBean) {
////                        ToastUtils.showShort(data.info)
////                        EventBus.getDefault().post(LoginBean())
////                    }
////
////                    override fun onError(e: ResponseException) {
////                    }
////                }
////        )
//
//    }
//
//    //播放下一集
//    private fun playNext() {
//        if (++urlIndex >= playFrom.urls.size) {
//            urlIndex = 0
//        }
//        changeVideoUrlIndex()
//        parseData()
//        println("===Jiexi ==playNext")
//
//        videoView.setSpeed(1f)
//        controller.setSpeed("1.00")
//    }
//
//    //升级vip
//    private fun updateVip() {
//        if (!UserUtils.isLogin()) {
//            LoginActivity.start()
//        } else {
//            if (UserUtils.userInfo?.group_id != 3) {
//                val intent = Intent(mActivity, PayActivity::class.java)
//                intent.putExtra("type", 1)
//                ActivityUtils.startActivity(intent)
//            } else {
//                checkVodTrySee()
//            }
//        }
//    }
//
//    //付费观看
//    private fun payPlay() {
//        if (!UserUtils.isLogin()) {
//            LoginActivity.start()
//        } else {
//            if (playList == null) {
//                return
//            }
//            RequestManager.execute(
//                    mActivity,
//                    vodService.buyVideo(4.toString(), mVodBean.vod_id.toString(), playFrom.sid.toString(), playList!![urlIndex].nid.toString(), 1.toString()),
//                    object : BaseObserver<String>() {
//                        override fun onSuccess(data: String) {
//                            ToastUtils.showShort("购买成功！")
//                            checkVodTrySee()
//                        }
//
//                        override fun onError(e: ResponseException) {
//                        }
//
//                    }
//            )
//        }
//    }
//
//    private var lbm: LocalBroadcastManager? = null
//    private val localReceiver = LocalReceiver(this@NewPlayActivity2)
//
//
//    private fun registerReceiver() {
//        lbm = LocalBroadcastManager.getInstance(this@NewPlayActivity2)
//        lbm!!.registerReceiver(localReceiver, IntentFilter("cn.whiner.av.AvVideoController"))
//    }
//
//    class LocalReceiver(act: NewPlayActivity2) : BroadcastReceiver() {
//
//        private var act = act
//
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if (intent != null && intent.action == "cn.whiner.av.AvVideoController") {
//                act.chengeNextLine()
//            }
//        }
//    }
//
//    private var timer: Timer? = null
//    private var timerTask: TimerTask? = null
//
//    private fun startTimer() {
//        if (timer == null && timerTask == null) {
//            timer = Timer()
//            timerTask = object : TimerTask() {
//                override fun run() {
//                    savePlayRecord(false)
//                }
//            }
//            timer!!.schedule(timerTask, 0, 1000 * 60 * 2)
//        }
//    }
//
//    private fun cancelTimer() {
//        if (timer != null) {
//            timer!!.cancel()
//            timer = null
//        }
//        if (timerTask != null) {
//            timerTask!!.cancel()
//            timerTask = null
//        }
//    }
//}
