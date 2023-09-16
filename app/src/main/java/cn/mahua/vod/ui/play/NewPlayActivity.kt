package cn.mahua.vod.ui.play


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.mahua.av.CheckVodTrySeeBean
import cn.mahua.av.play.AvVideoController
import cn.mahua.av.play.AvVideoController.RECEIVER_TYPE_REPLAY
import cn.mahua.av.play.AvVideoController.RECEIVER_TYPE_TIMER
import cn.mahua.vod.App
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.*
import cn.mahua.vod.jiexi.BackListener
import cn.mahua.vod.jiexi.JieXiUtils2
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.ui.dlan.DlanListPop
import cn.mahua.vod.ui.login.LoginActivity
import cn.mahua.vod.ui.pay.PayActivity
import cn.mahua.vod.ui.widget.HitDialog
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.OkHttpUtils
import cn.mahua.vod.utils.Retrofit2Utils
import cn.mahua.vod.utils.UserUtils
import com.blankj.utilcode.util.*
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.base.net.observer.PlayLoadingObserver
import com.liuwei.android.upnpcast.NLUpnpCastManager
import com.liuwei.android.upnpcast.device.CastDevice
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_new_play.*
import okhttp3.Call
import okhttp3.Response
import pro.dxys.fumiad.FuMiAd
import pro.dxys.fumiad.FumiRewardVideoSimpleListener
import xyz.doikki.videoplayer.player.BaseVideoView
import xyz.doikki.videoplayer.player.VideoView
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.collections.ArrayList

class NewPlayActivity : BaseActivity(), OnSpeedItemClickListener {
    private val TAG = "NewPlayActivity"

    private lateinit var controller: AvVideoController
    private lateinit var mVodBean: VodBean
    private var isShowPlayProgress = false
    private var curPlayUrl: String = ""
    private var isAllowCastScreen: Boolean = false//根据视频的类型，来判断是否可以投屏
    private var isAllowAdScreen: Boolean = false // 根据观看广告判断是否可以...
    private var curParseIndex = 0//记录上一次解析到的位置，如果出现解析到是视频不能播放的话 自动解析下一条
    private var curFailIndex = -1
    private var isPlay = false//是否正在播放
    private var playSourceIndex = 0//播放源位置
    private var urlIndex = 0//当前播放的到哪一集
    private var curindex = 0
    private lateinit var playFormList: List<PlayFromBean>
    private lateinit var playFrom: PlayFromBean //当前播放播放源信息
    private var playList: List<UrlBean>? = null//当前播放列表
    private var playScoreInfo: PlayScoreBean? = null
    private var isParseSuccess = false
    private var isSeekToHistory: Boolean = false
    private var curProgressHistory: Long = 0
    private var vodDuration: Long = 0
    private var videoNetProgress: Long = 0L
    private var avheaders: MutableMap<String?, String?>? = null

    private val onJiexiResultListener = object : BackListener {
        override fun onSuccess(url: String?, curParseIndex: Int,headers: Map<String?, String?>?) {
            println("===Jiexi onSuccess 坐标：$curParseIndex url=${url}")
            println("---play----onSuccess" + url)
            if (isSuccess) {
                Log.d(TAG, "====ParseonFail  " + "\n url=" + url)
                return
            } else {
                Log.d(TAG, "====ParseonSuccess  " + "\n url=" + url)
            }
            curFailIndex = curParseIndex
            headers?.let {
                avheaders = it as MutableMap<String?, String?>?
            }


            println("---play----onSuccess=false curFailIndex=" + curFailIndex + " 当前url" + url)
            if (url == null || url.isEmpty()) {
                println("===修复onSuccess")
                runOnUiThread {
                    chengeNextLine()
                }
                return
            }
            println("---play----isPlay=" + isPlay)
            url?.let {
                if (!isPlay) {
                    Log.d(TAG, "====ParseonSuccess  play（）" + " url=" + it)
                    play(it)
                    curPlayUrl = it
                    isPlay = true
                }
            }


        }

        override fun onError() {
            controller.updateJiexiProgess("获取资源失败,请换来源或者联系客服解决！")
        }

        override fun onProgressUpdate(msg: String?) {
            controller.updateJiexiProgess(msg)
        }
    }
    private var videoDetailFragment: VideoDetailFragment? = null
    private var summaryFragment: SummaryFragment? = null
    private var playListFragment: PlayListFragment? = null
    private var isParsed: Boolean = false
    private var isLandscape = false//当前是否为横屏

    override fun getLayoutResID(): Int {
        return R.layout.activity_new_play
    }

    override fun initView() {
        super.initView()
        BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.player_status_color))
        mVodBean = intent.getParcelableExtra(PlayActivity.KEY_VOD) as VodBean
        isShowPlayProgress = intent.getBooleanExtra(PlayActivity.KEY_SHOW_PROGRESS, false)
        controller = AvVideoController(videoView, this)

        videoView.setVideoController(controller)

        registerReceiver()


    }

    override fun initListener() {
        super.initListener()
        controller.setControllerClickListener {
            when (it.id) {
                R.id.tv_av_hd ->
                    chengeNextLine()
                R.id.iv_av_back, R.id.iv_av_back1, R.id.iv_av_back2 -> {
                    Log.i("bds", "back===========")
                    // finish();
                    App.curPlayScoreBean = null
                    playScoreInfo = null
                    savePlayRecord(true)
                    setResult(3)
                }

                R.id.iv_av_next ->
                    playNext()
                R.id.tv_av_speed ->
                    showSpeedListDialog(it.tag as Int)
                R.id.tv_av_selected ->
                    showPlayListDialog()
                R.id.tvPlaySource ->
                    showPlaySourceDialog()
                R.id.iv_av_miracast -> {

                    //if (LoginUtils.checkVIP(this@NewPlayActivity, "投屏需要开通vip,是否去开通")) { //去除开通VIP限制
                    showCastScreenDialog()
                    //}
                }


                R.id.tvPayButton, R.id.tvEndPayButton -> {
                    payPlay()
                }
                R.id.tvUpdateButton, R.id.tvEndUpdateButton -> {
                    updateVip()
                    //RewardAd()
                }
                R.id.btn_pop_danmaku -> {
                    val s = it.tag as String
                    sendDanmu(s)
                }
            }
        }

        videoView.setOnStateChangeListener(object : BaseVideoView.OnStateChangeListener {
            override fun onPlayStateChanged(playState: Int) {
                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                    val percentage = getPercentage(curProgressHistory, vodDuration)
                    println("进度9：=" + controller.percentage + "  2:" + playScoreInfo?.curProgress + " 3=" + curProgressHistory + " 4=" + percentage)
                    if (percentage <= 0.01f || percentage >= 0.99f) {
                        println("进度5：==")
                        playNext()
                    } else {
                        println("进度1：==" + curProgressHistory)
                        controller.setReplayByCurProgress(true)
                    }
                } else if (playState == VideoView.STATE_PREPARED) {
                    isParseSuccess = true
                    if (isShowPlayProgress) {
                        Log.i("dsd", "iko===${App.curPlayScoreBean?.curProgress ?: 0}")
                        videoView.seekTo(playScoreInfo?.curProgress ?: 0)
                        println("进度3：==" + playScoreInfo?.curProgress)
                        //从播放记录中点击播放的时候，需要重新插入输入库
//                        playScoreInfo?.let {
//                            LitePal.deleteAll(PlayScoreBean::class.java, "vodId = ?", "${it.vodId}")
//                            playScoreInfo = null
//                        }
                        isShowPlayProgress = false
                    } else {
                        if (isSeekToHistory) {
                            videoView.seekTo(curProgressHistory)
                            println("进度2：==" + curProgressHistory)
//                            isSeekToHistory = false
                        } else {
                            //跳过30秒片头
                            if (videoNetProgress == 0L) {
                                //videoView.seekTo(30000)
                            } else {
                                val islive =controller.duration
                                if(islive == 0L){
                                    controller.addDefaultControlComponent(mVodBean.vodName,true)
                                }else if(islive>videoNetProgress) {
                                    videoView.seekTo(videoNetProgress)
                                }
                            }
                            println("进度4：== videoNetProgress=" + videoNetProgress)
                        }
                    }
                    vodDuration = controller.duration
                    println("进度12：==" + vodDuration)
                    when (SPUtils.getInstance().getInt(AvVideoController.KEY_SPEED_INDEX, 3)) {
                        0 -> {
                            videoView.setSpeed(2f)
                            controller.setSpeed("2.00")
                        }
                        1 -> {
                            videoView.setSpeed(1.5f)
                            controller.setSpeed("1.50")
                        }
                        2 -> {
                            videoView.setSpeed(1.25f)
                            controller.setSpeed("1.25")
                        }
                        3 -> {
                            videoView.setSpeed(1f)
                            controller.setSpeed("1.00")
                        }
                        4 -> {
                            videoView.setSpeed(0.75f)
                            controller.setSpeed("0.75")
                        }
                        5 -> {
                            videoView.setSpeed(0.5f)
                            controller.setSpeed("0.50")
                        }
                    }
                } else if (playState == VideoView.STATE_ERROR) {
                    LogUtils.d("=====问题 video OnError")
                    controller.setReplayByCurProgress(true)
                    isSeekToHistory = true
                    curParseIndex++
                    parseData()
                } else if (playState == VideoView.STATE_PAUSED) {
                    if (App.startBean != null && App.startBean.ads != null && App.startBean.ads.player_pause != null && App.startBean.ads.player_pause.description != null && App.startBean.ads.player_pause.status ==1) {
                        val data = App.startBean.ads.player_pause.description
                        val sourceStrArray = data!!.split(",".toRegex()).toTypedArray()
                        controller.showAd(sourceStrArray[1], sourceStrArray[0])
                    }
                }
            }

            override fun onPlayerStateChanged(playerState: Int) {
                if (playerState == VideoView.PLAYER_NORMAL) {
                    isLandscape = false
                } else if (playerState == VideoView.PLAYER_FULL_SCREEN) {
                    isLandscape = true
                }
            }
        })
    }

    override fun initData() {
        super.initData()
        getVideoDetail()
    }

    override fun onStart() {
        super.onStart()
        videoView.resume()
        if (isParsed) {
            checkVodTrySee()
        }
    }

    override fun onResume() {
        super.onResume()
        checkVodTrySee()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        JieXiUtils2.INSTANCE.stopGet()
        controller.onDestroy()
        videoView.release()
        lbm?.unregisterReceiver(localReceiver)
        cancelTimer()
    }

    override fun onBackPressedSupport() {
        if (!videoView.onBackPressed()) {
            try {
                recordPlay()//播放记录可能为空
            } catch (e: Exception) {
            } finally {
                super.onBackPressedSupport()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(controller.isLocked){
                controller.show()
                ToastUtils.showShort(cn.mahua.av.R.string.av_lock_tip)
                return true
            }
            if(controller.isFullScreen){
                return controller.tzFullScreen()
            }
            App.curPlayScoreBean = null
            playScoreInfo = null
            savePlayRecord(true)
            setResult(3)
            finish()
            return false
        } else {

        }
        return super.onKeyDown(keyCode, event)
    }

    fun  m3u8down(): String {
        return curPlayUrl
    }
    fun   istp(): Boolean {
        return isAllowCastScreen
    }
    fun showSummary() {
        if (summaryFragment == null) {
            summaryFragment = SummaryFragment.newInstance(mVodBean)
            supportFragmentManager.beginTransaction()
                    .add(R.id.flContainer, summaryFragment!!)
                    .commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction()
                    .show(summaryFragment!!)
                    .commitAllowingStateLoss()
        }
    }

    fun hideSummary() {
        supportFragmentManager.beginTransaction()
                .hide(summaryFragment!!)
                .commitAllowingStateLoss()
    }

    fun showVideoDetail() {
        if (videoDetailFragment == null) {
            videoDetailFragment = VideoDetailFragment.newInstance(mVodBean, urlIndex, playSourceIndex)
            supportFragmentManager.beginTransaction()
                    .add(R.id.flContainer, videoDetailFragment!!)
                    .commitNowAllowingStateLoss()
        } else {
            videoDetailFragment?.changeCurIndex(urlIndex)
            supportFragmentManager.beginTransaction()
                    .show(videoDetailFragment!!)
                    .commitNowAllowingStateLoss()
        }
    }
    fun RewardAd(){
        ToastUtils.showShort("广告播放结束后将获得观影权限！")
        FuMiAd.showRewardVideoAutoStart(this, object : FumiRewardVideoSimpleListener() {
            override fun onAdClose(b: Boolean) {

            }

            override fun onReward() {
                //发放奖励
                Log.e("tag", "onReward")
                Toast.makeText(this@NewPlayActivity, "您已获得观影次数1次！", Toast.LENGTH_SHORT).show()
                isAllowAdScreen = true
                checkVodTrySee()
            }

            override fun onError(s: String) {
                super.onError(s)
                Log.e("tag", "onError:$s")

            }
        })
    }
    //    fun RewardAd(){
//        ToastUtils.showShort("广告播放结束后将获得观影权限！")
//        RewardAdSdk.getInstance(this).loadRewardAdvert("观影次数", 1, object : RewardAdListener {
//            override fun onError(i: Int, s: String) {}
//            override fun onAdShow() {}
//            override fun onAdVideoBarClick() {}
//            override fun onAdClose() {}
//            override fun onVideoComplete() {
//                LogUtils.d("=====广告完成")
//                isAllowAdScreen=true
//                checkVodTrySee()
//            }
//            override fun onRewardVerify(b: Boolean, i: Int, s: String) {
//                if(b) {
//                    Toast.makeText(this@NewPlayActivity, "您已获得"+s+i+"次！", Toast.LENGTH_SHORT).show()
//                    isAllowAdScreen = true
//                    checkVodTrySee()
//                }
//            }
//            override fun onAdvertStatusClose() {}
//        })
//    }
    fun showPlayList() {
        if (playListFragment == null) {
            val spanCount = if (mVodBean.type_id == 3) {
                2
            } else {
                5
            }
            playListFragment = PlayListFragment.newInstance(spanCount).apply {
                if (playList != null) {
                    showPlayList(playList!!, urlIndex)
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.flContainer, playListFragment!!)
                    .commitAllowingStateLoss()
        } else {
            playListFragment?.run {
                if (playList != null) {
                    showPlayList(playList!!, urlIndex)
                }
            }
            supportFragmentManager.beginTransaction()
                    .show(playListFragment!!)
                    .commitAllowingStateLoss()
        }
    }

    fun hidePlayList() {
        supportFragmentManager.beginTransaction()
                .hide(playListFragment!!)
                .commitAllowingStateLoss()
    }

    fun showNewVideo(vodBean: VodBean) {
        savePlayRecord(false)
        curProgressHistory = 0
        videoNetProgress = 0
//        isSeekToHistory = false
        recordPlay()//缓存上一个视频的进度
        App.curPlayScoreBean = null
        playScoreInfo = null
        mVodBean = vodBean
        supportFragmentManager.beginTransaction()
                .remove(videoDetailFragment!!)
                .commitNowAllowingStateLoss()
        videoDetailFragment = null

        if (summaryFragment != null) {
            supportFragmentManager.beginTransaction()
                    .remove(summaryFragment!!)
                    .commitNowAllowingStateLoss()
            summaryFragment = null

        }

        if (playListFragment != null) {
            supportFragmentManager.beginTransaction()
                    .remove(playListFragment!!)
                    .commitNowAllowingStateLoss()
            playListFragment = null
        }

        videoView.release()
        controller.setTitle(mVodBean.vodName)
        getVideoDetail()
    }

    fun changeSelection(position: Int) {
        urlIndex = position//更改当前选集
        curProgressHistory = 0
        videoNetProgress = 0
        isSeekToHistory = false
        curFailIndex = -1
        this.curParseIndex = 0
        LogUtils.d("=====问题 changeSelection")
        parseData()
    }

    fun changeVideoUrlIndex(position: Int = -1) {
        videoDetailFragment?.changeCurIndex(urlIndex)
        this.curParseIndex = 0
        curFailIndex = -1
    }

    fun changePlaySource(playFromBean: PlayFromBean, playSourceIndex: Int) {
        curindex = urlIndex +1
        this.playFrom = playFromBean
        this.playList = playFrom.urls
        this.playSourceIndex = playSourceIndex
        this.curParseIndex = 0
        curFailIndex = -1
        if(playFromBean!=null && playFromBean.urls.size >= curindex ) {
            LogUtils.d(playFromBean.urls.size)
            LogUtils.d("=====问题 changePlaySource")
            parseData()
            videoDetailFragment?.changePlaysource(playSourceIndex)
        }else{
            urlIndex = 0
            parseData()
            videoDetailFragment?.changePlaysource(playSourceIndex)
            changeVideoUrlIndex()
            Toast.makeText(this@NewPlayActivity, "此源暂无此集，正在播放第一集，请重新选集", Toast.LENGTH_SHORT).show()
        }
    }

    fun castScreen(device: CastDevice) {
        if (isParseSuccess && curPlayUrl.isNotEmpty()) {
            val vodUrl = if (curPlayUrl.startsWith("//")) {
                "https:$curPlayUrl"
            } else {
                curPlayUrl
            }
            NLUpnpCastManager.getInstance().connect(device);
            Intent(this, CastScreenActivity2::class.java).apply {
                putExtra("vod", mVodBean)
                putExtra("playSourceIndex", playSourceIndex)
                putExtra("urlIndex", urlIndex)
                putExtra("vodurl", vodUrl)
                putExtra("vodLong", controller.duration)
//                putExtra("device", device )
                println("vodurl+=${vodUrl}")
                val newPlayFromList = ArrayList<PlayFromBean>()
                playFormList.map {
                    newPlayFromList.add(it)
                }
                putParcelableArrayListExtra("playFormList", newPlayFromList)
                ActivityUtils.startActivity(this)
            }
        } else {
            runOnUiThread {
                ToastUtils.showShort("正在解析中...")
            }
        }
    }

    private fun changeTitle() {
        var title = mVodBean.vod_name
        if (mVodBean.type_id == 2) {
            if (playList != null) {
                title += " ${playList!![urlIndex].name}"
            }
        }
        controller.setTitle(title)
    }

    private fun chengeNextLine() {
        curParseIndex++
        parseData()
    }

    private fun chengeNextLineFromHead() {
        curParseIndex = 0
        curFailIndex = -1
        LogUtils.d("=====问题 chengeNextLineFromHead")
        parseData()
    }

    private fun showSpeedListDialog(pos: Int) {
        SpeedListDialog(mActivity, this, pos).show()
    }

    private fun showPlayListDialog() {
        if (playList != null) {
            PlayListDialog(mActivity, urlIndex, playList!!)
                    .show()
        }
    }

    private fun showPlaySourceDialog() {
        PlaySourceDialog(mActivity, playSourceIndex, playFormList)
                .show()
    }

    private fun showCastScreenDialog() {
        if (isAllowCastScreen) {
//            val dialogHeight = if (isLandscape) {
//                -1
//            } else {
//                videoView.height
//            }
//            CastScreenDialog(mActivity, dialogHeight)
//                    .show()
//            val url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            val danListPop = DlanListPop(this@NewPlayActivity) {

                castScreen(it)
            }
            XPopup.Builder(this@NewPlayActivity)
                    .asCustom(danListPop)
                    .show()
        } else {
            runOnUiThread {
                ToastUtils.showShort("暂无观影次数请邀请好友或升级会员！")
            }
        }
    }

    private fun getVideoDetail() {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {

        }
        RequestManager.execute(this, vodService.getVideoDetail(mVodBean.vod_id, 10),
                object : PlayLoadingObserver<VodBean>(mActivity) {
                    override fun onSuccess(data: VodBean) {
                        mVodBean = data
                        curParseIndex = 0
                        curFailIndex = -1
                        // playScoreInfo = LitePal.where("vodId = ?", data.vod_id.toString()).findFirst(PlayScoreBean::class.java)
                        playScoreInfo = App.curPlayScoreBean
                        urlIndex = playScoreInfo?.urlIndex ?: 0//获取播放记录中的选集
                        playSourceIndex = playScoreInfo?.playSourceIndex ?: 0//获取播放源

                        val playInfo = data.playInfo
                        if (playInfo != null) {
                            playSourceIndex = playInfo.playSourceIndex
                            urlIndex = playInfo.urlIndex
                            videoNetProgress = playInfo.curProgress
                            curProgressHistory = videoNetProgress
                        }

//                        runOnUiThread {
//                        ToastUtils.showShort("进度：playInfo=" + playInfo + "  videoNetProgress=" + videoNetProgress)
//                        }

                        playFormList = data.vod_play_list
                        if (data.vod_play_list.isNullOrEmpty() || data.vod_play_url.isEmpty() || data.getVod_play_from().equals("no")) {
                            HitDialog(this@NewPlayActivity)
                                    .setTitle(StringUtils.getString(R.string.tip))
                                    .setMessage("无播放地址，联系客服添加")
                                    .setOnHitDialogClickListener(object : HitDialog.OnHitDialogClickListener() {
                                        override fun onCancelClick(dialog: HitDialog) {
                                            super.onCancelClick(dialog)
                                            finish()
                                        }

                                        override fun onOkClick(dialog: HitDialog) {
                                            super.onOkClick(dialog)
                                            finish()
                                        }
                                    })
                                    .show()
                            return
                        }
                        if (data.vod_play_list != null) {
                            playFrom = data.vod_play_list!![playSourceIndex]
                        }
                        playList = playFrom.urls
                        LogUtils.d("=====问题 getVideoDetail")
                        parseData()
                        showVideoDetail()
                    }

                    override fun onError(e: ResponseException) {
                        e.printStackTrace()
                        finish()
                    }

                })

    }

    private fun checkVodTrySee() {
        if (playList == null) {
            return
        }
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(
                mActivity,
                vodService.checkVodTrySee(mVodBean.vod_id.toString(), 1.toString(), playList!![urlIndex].nid.toString()),
                object : BaseObserver<CheckVodTrySeeBean>() {
                    override fun onSuccess(data: CheckVodTrySeeBean) {
                        var isVip = false
                        if (UserUtils.isLogin() && UserUtils.userInfo?.group_id == 3) {
                            isVip = true
                        }
                        val status = data.status
                        isAllowCastScreen = if(data.user_video>0){
                            true
                        }else{
                            status == 0 || (isVip && status == 1) ///会员投屏(isVip && status == 1)
                        }
                        if(isAllowAdScreen){
                            isAllowCastScreen = true
                            data.user_video =1
                        }

                        controller.CheckVodTrySeeBean(data.user_video, data, isVip, mVodBean.vod_points_play)
                    }

                    override fun onError(e: ResponseException) {
                        isAllowCastScreen = false
                    }
                })
    }

    private fun parseData() {
        LogUtils.d("=====问题 parseData")
        if (isPlay) {
            videoView.release()
        }
        isParseSuccess = false
        isPlay = false
        showPlayerAd()
        showAnnouncement()
        // 开始解析地址
        val parse = playFrom.player_info.parse2
        var url: String = ""
        if (playList != null ) {
            url = playList!![urlIndex].url
        }
        LogUtils.d("", "====Parse start url=" + url + "  parse=" + parse)
        checkVodTrySee()
        changeTitle()

        if (url.contains(".mp4") || url.contains(".m3u8")) {


//            getSameActorData(url)
            isPlay = true
            curPlayUrl = url
            play(url)

        } else {
            isSuccess = false
            controller.showJiexi()
            LogUtils.d("", "====Parse start url=" + "正常进这？？")
            JieXiUtils2.INSTANCE.getPlayUrl(parse, url, curParseIndex, onJiexiResultListener, curFailIndex)
        }
    }

    private fun showPlayerAd() {
        App.playAd?.let {
            if (it.img != null && it.img != "") {
                controller.showAd(it.img, it.url)
            }
        }
    }

    private fun showAnnouncement() {
        runOnUiThread {
            App.startBean?.document?.roll_notice?.let {
                if (it.content.isNotEmpty() && it.status == "1") {
                    controller.showAnnouncement(it.content)
                }
            }
        }
    }

    private var isSuccess = false
    private fun play(url: String) {


//        val url="https://vip.fwwmy1.cn/api/data/youku/62ba0f501791adb51d22b699566bda7b.m3u8"
//        val url="https://vip.fwwmy1.cn/zhilian.php?url=https://vip.fwwmy1.cn/api/data/youku/62ba0f501791adb51d22b699566bda7b.m3u8"
        //错误
//        https://v5.monidai.com/20200905/ZWb6ezWP/index.m3u8
//        https://v5.szjal.cn/20200905/ZWb6ezWP/index.m3u8
        println("---play----" + url)

        isSuccess = true
        startTimer()
        controller.hideJiexi()
        LogUtils.d("", "====Parse play url=" + url)
        LogUtils.d("", "====Parse avheaders url=" + avheaders)
        if(url.contains(".m3u8")){
            getSameActorData(url)
        }else {
            var curl: String = ""
            if (playList != null ) {
                curl = playList!![urlIndex].url
            }
            videoView.post {
                if (url.startsWith("//")) {
                    videoView.setUrl("https:$url")
                } else if(curl.contains("bilibili.com")){
                    val airPorts = mapOf(Pair("Referer", curl),Pair("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36"))
                    if(avheaders==null){
                        avheaders = mutableMapOf(Pair("Referer",curl))
                        avheaders!!.putAll(airPorts)
                    }else {
                        avheaders!!.putAll(airPorts)
                    }
                    videoView.setUrl(url,avheaders)
                }else {
                    videoView.setUrl(url)
                }
                videoView.start()
            }
        }
    }

    //将当前记录缓存下来
    private fun recordPlay() {
        if (playScoreInfo == null) {
            playScoreInfo = PlayScoreBean().apply {
                vodId = mVodBean.vod_id
                typeId = mVodBean.type_id
                vodName = mVodBean.vod_name
                vodImgUrl = mVodBean.vod_pic
                percentage = controller.percentage
                curProgress = controller.curProgress
                playSourceIndex = this@NewPlayActivity.playSourceIndex
                if (playList != null) {
                    urlIndex = this@NewPlayActivity.urlIndex
                    vodSelectedWorks = this@NewPlayActivity.playList!![urlIndex].name
                }
                save()
            }
        } else {
            playScoreInfo?.run {
                percentage = controller.percentage
                curProgress = controller.curProgress
                playSourceIndex = this@NewPlayActivity.playSourceIndex
                if (playList != null) {
                    urlIndex = this@NewPlayActivity.urlIndex
                    vodSelectedWorks = this@NewPlayActivity.playList!![urlIndex].name
                }
                saveOrUpdate("vodId = ?", mVodBean.vod_id.toString())
            }
        }
    }


    private fun savePlayRecord(isClose: Boolean) {

        // var percentage = controller.percentage
        val curProgress = controller.curProgress
        if (curProgress == 0L) {
            if (isClose) {
                finish()
            }
            return
        }
//        runOnUiThread {
//        ToastUtils.showShort("保存进度：" + curProgress)
//        }
        if (curProgress != 0L) {
            curProgressHistory = curProgress
        }

        val voidid = mVodBean.vod_id.toString()
        Log.e(TAG, "======voidid===$voidid")

        var percentage = controller.percentage.toString()
        if (percentage == "NaN") {
            percentage = "0.0"
        }

        println("进度 ---savePlayRecord---  curProgress=" + curProgress)

        if (this@NewPlayActivity.playList.isNullOrEmpty() && isClose) {
            finish()
            return
        }

        val urlIndex = this@NewPlayActivity.urlIndex
        val vodSelectedWorks = this@NewPlayActivity.playList!![urlIndex].name


        var playSource = ""
        if (mVodBean.vod_play_list != null) {
            val playFromBean = mVodBean.vod_play_list!!.get(playSourceIndex)
            val playerInfo = playFromBean.player_info
            val urls = playFromBean.urls
            playSource = playerInfo.show
        }
        if (StringUtils.isEmpty(playSource)) {
            playSource = "默认"
        }


        if (UserUtils.isLogin()) {
            val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
            if (AgainstCheatUtil.showWarn(vodService)) {
                return;
            }
            Log.d(TAG, "voidid=${voidid}  vodSelectedWorks=${vodSelectedWorks}  playSource=${playSource}  percentage=${percentage} curProgress=${curProgress}")
            RequestManager.execute(this, vodService.addPlayLog(voidid, vodSelectedWorks, playSource.toString(), percentage, urlIndex.toString(), curProgress.toString(), playSourceIndex.toString()),
                    object : BaseObserver<UserVideo>() {
                        override fun onSuccess(data: UserVideo) {
                            Log.i("play", "addPlayLogsucess")
                            val intent = Intent("android.intent.action.AddPlayScore")
                            sendBroadcast(intent)
                            if (isClose) {
                                finish()
                            }
                        }

                        override fun onError(e: ResponseException) {
                            Log.i("play", "addPlayfaied")
                            if (isClose) {
                                finish()
                            }
                        }
                    })
            println("watchVideoLong==$watchVideoLong")
            if (watchVideoLong != 0) {
                RequestManager.execute(this, vodService.addWatchTime(watchVideoLong),
                        object : BaseObserver<GetScoreBean>() {
                            override fun onSuccess(data: GetScoreBean) {
                                if (data.score != "0") {
                                    runOnUiThread {
                                        ToastUtils.showShort("已观影30分钟，获得${data.score}积分")
                                    }
                                }
                            }

                            override fun onError(e: ResponseException) {
                                println("watchVideoLong==  onError")
                            }
                        })
            }
        } else {
            if (isClose) {
                finish()
            }
        }


//        RequestManager.execute(
//                mActivity,
//                vodService.shareScore(),
//                object : BaseObserver<ShareBean>() {
//                    override fun onSuccess(data: ShareBean) {
//        runOnUiThread {
//                        ToastUtils.showShort(data.info)
//        }
//                        EventBus.getDefault().post(LoginBean())
//                    }
//
//                    override fun onError(e: ResponseException) {
//                    }
//                }
//        )

    }

    //播放下一集
    private fun playNext() {
        curProgressHistory = 0
        isSeekToHistory = false
        videoNetProgress = 0
        if (++urlIndex >= playFrom.urls.size) {
            urlIndex = 0
        }
        changeVideoUrlIndex()
        parseData()


    }

    //升级vip
    private fun updateVip() {
        if (!UserUtils.isLogin()) {
            LoginActivity.start()
        } else {
            if (UserUtils.userInfo?.group_id != 3) {
                val intent = Intent(mActivity, PayActivity::class.java)
                intent.putExtra("type", 1)
                ActivityUtils.startActivity(intent)
            } else {
                checkVodTrySee()
            }
        }
    }

    //付费观看
    private fun payPlay() {
        if (!UserUtils.isLogin()) {
            LoginActivity.start()
        } else {
            if (playList == null) {
                return
            }
            val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
            if (AgainstCheatUtil.showWarn(vodService)) {
                return;
            }
            RequestManager.execute(
                    mActivity,
                    vodService.buyVideo(4.toString(), mVodBean.vod_id.toString(), playFrom.sid.toString(), playList!![urlIndex].nid.toString(), 1.toString()),
                    object : BaseObserver<String>() {
                        override fun onSuccess(data: String) {
                            runOnUiThread {
                                ToastUtils.showShort("购买成功！")
                            }
                            checkVodTrySee()
                        }

                        override fun onError(e: ResponseException) {
                            ToastUtils.showShort("积分不足，请到个人中心充值！")
                        }

                    }
            )
        }
    }

    private var lbm: LocalBroadcastManager? = null
    private val localReceiver = LocalReceiver(this@NewPlayActivity)


    private fun registerReceiver() {
        lbm = LocalBroadcastManager.getInstance(this@NewPlayActivity)
        lbm!!.registerReceiver(localReceiver, IntentFilter("cn.whiner.av.AvVideoController"))
    }

    class LocalReceiver(act: NewPlayActivity) : BroadcastReceiver() {

        private var act = act

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null && intent.action == "cn.whiner.av.AvVideoController") {
                when (intent.getIntExtra("type", RECEIVER_TYPE_TIMER)) {
                    RECEIVER_TYPE_REPLAY -> {
                        act.isSeekToHistory = true
                    }
                    RECEIVER_TYPE_TIMER -> {
                        val isFromHead = intent.getBooleanExtra("isFromHead", false)
                        if (isFromHead) {
                            act.chengeNextLineFromHead()
                        } else {
                            act.chengeNextLine()
                        }
                    }
                }
            }
        }
    }

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var watchVideoLong = 0
    private var index = 0

    private fun startTimer() {
        if (timer == null && timerTask == null) {
            timer = Timer()
            timerTask = object : TimerTask() {
                override fun run() {
                    if (index == 0) {
                        index++
                    } else {
                        savePlayRecord(false)
                        watchVideoLong += 60
                    }
                }
            }
            timer!!.schedule(timerTask, 0, 1000 * 30)
        }
    }

    private fun cancelTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        if (timerTask != null) {
            timerTask!!.cancel()
            timerTask = null
        }
    }

    override fun onSpeedItemClick(speed: String) {
        controller.setSpeedSelect(speed)
    }

    private fun sendDanmu(content: String) {
        if (content.isEmpty()) {
            return
        }
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(
                mActivity,
                vodService.sendDanmu(content, mVodBean.vod_id.toString(), System.currentTimeMillis().toString()),
                object : BaseObserver<GetScoreBean>() {
                    override fun onSuccess(data: GetScoreBean) {
                        if (data.score != "0") {
                            runOnUiThread {
                                ToastUtils.showShort("发送弹幕成功，获得${data.score}积分")
                            }
                        }
                    }

                    override fun onError(e: ResponseException) {
                        runOnUiThread {
                            ToastUtils.showShort(e.getErrorMessage())
                        }
                    }
                })
    }

    fun getPercentage(curPosition: Long, duration: Long): Float {
        val percentage: Float = curPosition / (duration * 1.0f)
        val df = DecimalFormat("#.00")
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'
        df.decimalFormatSymbols = dfs
        return java.lang.Float.valueOf(df.format(percentage.toDouble()))
    }
    private fun getUrlList(parses: String): java.util.ArrayList<String>? {
        val urlStrs = java.util.ArrayList<String>()
        if (parses.contains(",")) {
            val split = parses.split(",".toRegex()).toTypedArray()
            for (url in split) {
                urlStrs.add(url)
            }
        } else {
            urlStrs.add(parses)
        }
        return urlStrs
    }
    fun getSameData(url: String): String {
        videoView.post {
            if( playFrom.player_info.parse2.contains("ikheader=") ||url.contains("mgtv.com")){
                if(playFrom.player_info.parse2.contains("ikheader=")) {
                    var p1: String = ""
                    Log.d("222ssss"+url, playFrom.player_info.parse2 + "_______")
                    //val rgex = "iktoken(.*?)&url="
                    //p1 = cn.mahua.vod.utils.StringUtils.getSubUtil(playFrom.player_info.parse2, rgex)[0]
                    p1 =cn.mahua.vod.jiexi.StringUrlseek.seek_header(playFrom.player_info.parse2)
                    if(avheaders==null){
                        avheaders = mutableMapOf(Pair("Referer",p1))
                    }
                    avheaders?.set("Referer",p1)
                }else{
                    if(avheaders==null){
                        avheaders = mutableMapOf(Pair("Referer","https://baidu.com/"))
                    }
                    if(url.contains("mgtv.com")){
                        avheaders?.set("Referer","https://pcvideoaliyun.titan.mgtv.com")
                    }
                }
                LogUtils.d("", "====Parse avheaders url=" + avheaders)
                videoView.setUrl(url,avheaders)
            }else {
                if (url.startsWith("//")) {
                    videoView.setUrl("https:$url")
                } else {

                    videoView.setUrl(url)

                }
            }
            videoView.start()
//            controller.setCurProgress(playScoreInfo?.percentage ?: 0f)
        }
        return url;
    }
    fun getSameActorData(url: String): String {
//        val urlList: java.util.ArrayList<String>? = getUrlList(playFrom.player_info.parse2)
//        var curpase_url: String?=playFrom.player_info.parse2
//        val curpase_int=curParseIndex+1
//        if(urlList!!.size >=curpase_int){
//            curpase_url=urlList.get(curParseIndex)
//        }
        // Log.d("222ssss1", url + "_______")
        var p1: String = ""
        OkHttpUtils.getInstance().getDataAsynFromNet1(url, object : OkHttpUtils.MyNetCall {
            override fun success(call: Call?, response: Response?) {
                val playUrl: String? = response?.body?.string()
                val playUrl_code: Int? = response?.code
                LogUtils.d("", "====Parse avheaderss1 url=" + playUrl_code)
                videoView.post {
                    if(playUrl_code == 403) {
                        if (url.contains("mgtv.com")) {//需要header
                            val airPorts = mapOf(Pair("Referer", "https://www.mgtv.com/"),Pair("User-Agent", "6666"))
                            if(avheaders==null){
                                avheaders = mutableMapOf(Pair("Referer","https://baidu.com/"))
                                avheaders!!.putAll(airPorts)
                            }else {
                                avheaders!!.putAll(airPorts)
                            }
                            LogUtils.d("", "====Parse  url=" + url)
                            LogUtils.d("", "====Parse avheaderss url=" + avheaders)

                        } else {
                            //videoView.setUrl(url)
                        }
                        videoView.setUrl(url,avheaders)
                    }else{
                        videoView.setUrl(url)
                    }
                    videoView.start()
                }


            }

            override fun failed(call: Call?, e: IOException?) {
                mActivity.runOnUiThread {
                    Toast.makeText(this@NewPlayActivity, "此源暂时无法播放，请更换播放源再试", Toast.LENGTH_SHORT).show()
                }
                //chengeNextLine()
            }
        })
        return p1
    }

}
