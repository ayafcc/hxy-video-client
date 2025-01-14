package com.sweetieplayer.vod.ui.user

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.google.gson.Gson
import com.sweetieplayer.vod.ApiConfig
import com.sweetieplayer.vod.App
import com.sweetieplayer.vod.R
import com.sweetieplayer.vod.bean.*
import com.sweetieplayer.vod.databinding.FragmentUserBinding
import com.sweetieplayer.vod.download.SPUtils
import com.sweetieplayer.vod.netservice.VodService
import com.sweetieplayer.vod.ui.account.AccountSettingActivity
import com.sweetieplayer.vod.ui.collection.CollectionActivity
import com.sweetieplayer.vod.ui.down.AllDownloadActivity
import com.sweetieplayer.vod.ui.expand.ExpandCenterActivity
import com.sweetieplayer.vod.ui.expand.MyExpandActivity
import com.sweetieplayer.vod.ui.login.LoginActivity
import com.sweetieplayer.vod.ui.notice.MessageCenterActivity
import com.sweetieplayer.vod.ui.pay.PayActivity
import com.sweetieplayer.vod.ui.play.PlayActivity
import com.sweetieplayer.vod.ui.score.PlayScoreActivity
import com.sweetieplayer.vod.ui.share.ShareActivity
import com.sweetieplayer.vod.ui.task.TaskActivity2
import com.sweetieplayer.vod.ui.withdraw.GoldWithdrawActivity
import com.sweetieplayer.vod.utils.AgainstCheatUtil
import com.sweetieplayer.vod.utils.LoginUtils
import com.sweetieplayer.vod.utils.Retrofit2Utils
import com.sweetieplayer.vod.utils.UserUtils
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.litepal.LitePal


class UserFragment : BaseFragment() {
    private lateinit var userBinding : FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userBinding = FragmentUserBinding.inflate(inflater, container, false)
        return userBinding.root
    }

    private val playScoreAdapter: PlayScoreAdapter by lazy {
        PlayScoreAdapter().apply {
            setOnItemClickListener { adapter, view, position ->
                val item = adapter.getItem(position) as PlayScoreBean
                if (!UserUtils.isLogin()) {
                    LoginActivity.start()
                } else {
                    App.curPlayScoreBean = item
                    //  PlayActivity.startByPlayScore(item.vodId)
                    PlayActivity.startByPlayScoreResult(this@UserFragment, item.vodId);
                }

            }
        }
    }
    override var isUseEventBus: Boolean = true
    var isInit: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_user
    }


    @JvmField
    public var userFragment = this@UserFragment
    public var playVideoReceiver: PlayVideoReceiver = PlayVideoReceiver()

    override fun initView() {
        super.initView()

        //  mActivity = (context as? AppCompatActivity)!!

        val filter = IntentFilter()
        //给意图过滤器添加action，就是要监听的广播对应的action
        filter.addAction("android.intent.action.AddPlayScore")

        // playVideoReceiver = PlayVideoReceiver()
        mActivity.registerReceiver(playVideoReceiver, filter, Context.RECEIVER_EXPORTED)

        val userTip = App.startBean?.document?.notice?.content ?: ""
        if (userTip.isNotEmpty()) {
            userBinding.tvUserTip.text = userTip
        }
        userBinding.rvPlayScore.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        userBinding.rvPlayScore.adapter = playScoreAdapter
        val ad = App.startBean?.ads?.user_center
        if (ad == null || ad.status == 0 || ad.description.isNullOrEmpty()) {
            userBinding.awvUser.visibility = View.GONE
        } else {
            userBinding.awvUser.visibility = View.VISIBLE
            userBinding.awvUser.setOnClickListener {
                if (!UserUtils.isLogin()) {
                    LoginActivity.start()
                } else {
                    val intent = Intent(activity, ExpandCenterActivity::class.java)
                    ActivityUtils.startActivity(intent)
                }
            }
            Glide.with(mContext).load(ad.description).into(userBinding.awvUser)
        }

//        userRefreshLayout.setDisableContentWhenRefresh(false) //是否在刷新的时候禁止列表的操作
//        userRefreshLayout.setDisableContentWhenLoading(false) //是否在加载的时候禁止列表的操作
//        userRefreshLayout.setEnableLoadMore(false) //是否启用上拉加载功能
//        userRefreshLayout.setEnableRefresh(true) //是否启用上拉加载功能;
//        userRefreshLayout.setEnableAutoLoadMore(false)
//        userRefreshLayout.setOnRefreshListener(OnRefreshListener {
//            updateUserInfo()
//            userRefreshLayout.finishRefresh()
//        })
//        userRefreshLayout.setOnLoadMoreListener(OnLoadMoreListener {
//        })
//        if(!isInit) {
//            isInit = true
//            updateUserInfo()
//        }

        getGroupChatList()
    }

    override fun initListener() {
        super.initListener()
        userBinding.tvLogin.setOnClickListener {
            LoginActivity.start()
        }

        userBinding.ivUserPic.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                ActivityUtils.startActivity(AccountSettingActivity::class.java)
            }
        }

        userBinding.tvUserTask.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                TaskActivity2.start()
            }
        }

        userBinding.tvUserShare.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                Toast.makeText(activity, "分享", Toast.LENGTH_SHORT).show()
                ActivityUtils.startActivity(ShareActivity::class.java)
            }
        }

        userBinding.tvUserService.setOnClickListener {
            var description: String = ""
            if (App.startBean != null && App.startBean.ads != null && App.startBean.ads.service_qq != null && App.startBean.ads.service_qq.description != null) {
                description = App.startBean.ads.service_qq.description
            }
            //获取QQ
            if (description.contains("uin=")) {
                description = description.split("uin=")[1]
            }
            if (description.contains("&site")) {
                description.split("&site")[0]
            }
            val link = "mqq://im/chat?chat_type=wpa&uin=${description}&version=1&src_type=web"

            Intent(Intent.ACTION_VIEW, Uri.parse(link)).let {
                if (it.resolveActivity(mActivity.packageManager) != null) {
                    ActivityUtils.startActivity(it)
                } else {
                    ToastUtils.showShort("未安装QQ!!")
                }
            }
        }


        userBinding.tvCoinWithdraw.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                ActivityUtils.startActivity(GoldWithdrawActivity::class.java)
            }
        }

        userBinding.tvUserSign.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                sign()
            }

        }
        userBinding.tvUserT1.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                val intent = Intent(activity, PayActivity::class.java)
                intent.putExtra("type", 0)
                ActivityUtils.startActivity(intent)
            }
        }
        userBinding.tvUserT2.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                val intent = Intent(activity, PayActivity::class.java)
                intent.putExtra("type", 0)
                ActivityUtils.startActivity(intent)
            }

        }
        userBinding.tvUserT3.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                val intent = Intent(activity, PayActivity::class.java)
                intent.putExtra("type", 1)
                ActivityUtils.startActivity(intent)
            }
        }

        userBinding.llCollect.setOnClickListener {
            CollectionActivity.start()
        }

        userBinding.llPlayScore.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                //  ActivityUtils.startActivity(PlayScoreActivity::class.java)
                val intent = Intent(activity, PlayScoreActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }

        userBinding.llClear.setOnClickListener {
            LitePal.deleteAll(PlayScoreBean::class.java)
            ToastUtils.showShort("已清除缓存")
            getPlayScore()
        }
        userBinding.llNotice.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                val intent = Intent(activity, MessageCenterActivity::class.java)
                startActivity(intent)
            }
        }
        userBinding.llExpand.setOnClickListener {
            if (!UserUtils.isLogin()) {
                LoginActivity.start()
            } else {
                val intent = Intent(activity, MyExpandActivity::class.java)
                startActivity(intent)
            }
        }
        userBinding.llCache.setOnClickListener {

            if(LoginUtils.checkLogin(activity)){
                activity?.let { it1 -> AllDownloadActivity.start(it1) }
            }

        }

    }

    @Subscribe
    fun onLoginSucces(data: LoginBean? = null) {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.userInfo(), object : BaseObserver<UserInfoBean>() {
            override fun onSuccess(data: UserInfoBean) {
                updateUserInfo(data)
                UserUtils.userInfo = data
//                if (isInit) {
                getPlayScore()
//                }
                EventBus.getDefault().post(data)//通知改变信息
            }

            override fun onError(e: ResponseException) {
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            UserUtils.userInfo = null
            updateUserInfo()
            getPlayScore()

            if (UserUtils.isLogin()) {
                onLoginSucces()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateUserInfo()
        getPlayScore()

        if (UserUtils.isLogin()) {
            onLoginSucces()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //从我的页面点击视频播放界面和视频播放记录页面
        if (requestCode == 1 || requestCode == 2) {
            getPlayScore()
        }

    }


    //    var playVideoReceiver : PlayVideoReceiver by lazy {
//        PlayVideoReceiver()
//
//    }
//    //微信登录通知广播
    public class PlayVideoReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            Log.i(javaClass.name.toString(), "onReceive playscore")
            //   UserFragment.newInstance().getPlayScore();
            // UserFragment.newInstance().mHandler.sendEmptyMessage(1)


        }

    }

    public var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    getPlayScore()
                }
            }
        }
    }


//    internal val playVideoReceiver: BroadcastReceiver by lazy {
//        BroadcastReceiver.apply {
//              fun onReceive(context: Context, intent: Intent) {
//         Log.i(javaClass.name.toString(), "onReceive playscore")
//                  userFragment.getPlayScore()
//
//       }
//
//        }
//    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
//        getPlayScore()
//        if (UserUtils.isLogin()) {
//            onLoginSucces()
//        }
    }

    @Subscribe
    fun onLogout(data: LogoutBean? = null) {
        UserUtils.userInfo = null
        updateUserInfo()
    }

    private fun sign() {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(this, vodService.sign(), object : BaseObserver<GetScoreBean>() {
            override fun onSuccess(data: GetScoreBean) {
                if (data.score == "0") {
                    ToastUtils.showShort(R.string.sign_success)
                } else {
                    ToastUtils.showShort("签到成功，获得${data.score}积分")
                }
                onLoginSucces()
            }

            override fun onNext(p0: GetScoreBean) {
                TODO("Not yet implemented")
            }

            override fun onError(e: ResponseException) {
                ToastUtils.showShort(e.getErrorMessage())
            }
        })
    }

    private fun updateUserInfo(data: UserInfoBean? = null) {
        if (UserUtils.isLogin()) {
            userBinding.tvLogin.visibility = View.GONE
            userBinding.tvUserName.visibility = View.VISIBLE
            userBinding.tvUserTip.visibility = View.VISIBLE
        } else {
            userBinding.tvLogin.visibility = View.VISIBLE
            userBinding.tvUserName.visibility = View.INVISIBLE
            userBinding.tvUserTip.visibility = View.INVISIBLE
            userBinding.tvUserJinbi.text = "剩余金币 0"
            userBinding.tvUserJifen.text = "剩余积分 0"
            userBinding.tvUserVideo.text = "观影次数 0"
        }
        data?.let {
            val  isVIp=it.group.group_name.contains("VIP")
            SPUtils.setBoolean(activity, "isVip", isVIp)
            userBinding.tvUserName.text = "${it.group?.group_name}：${data.user_nick_name}"
            userBinding.tvUserName.text = "${it.group?.group_name}：${data.user_nick_name}"
            userBinding.tvUserJinbi.text = "剩余金币  ${it.user_gold}"
            userBinding.tvUserJifen.text = "剩余积分  ${it.user_points}"
            userBinding.tvUserVideo.text = "观影次数  ${it.leave_times}"
            if (it.user_portrait.isNotEmpty()) {
                Glide.with(mActivity)
                        .load(ApiConfig.BASE_URL + "/" + it.user_portrait)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(userBinding.ivUserPic)
            } else {
                Glide.with(mActivity)
                        .load(R.drawable.ic_default_avator)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(userBinding.ivUserPic)
            }

        }
    }


    private fun getPlayScore() {
//        LitePal.order("id desc")
//                .findAsync(PlayScoreBean::class.java)
//                .listen {
//                    when {
//                        it.size > 10 -> {
//                            rvPlayScore.visibility = View.VISIBLE
//                            playScoreAdapter.setNewData(it.subList(0, 10))
//                        }
//                        it.size == 0 -> rvPlayScore.visibility = View.GONE
//                        else -> {
//                            rvPlayScore.visibility = View.VISIBLE
//                            playScoreAdapter.setNewData(it)
//                        }
//                    }
//                }


        var playScoreBeans = ArrayList<PlayScoreBean>()
        if (UserUtils.isLogin()) {
            val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
            if (AgainstCheatUtil.showWarn(vodService)) {
                return;
            }
            RequestManager.execute(this, vodService.getPlayLogList("1", "12"),
                    object : LoadingObserver<Page<PlayLogBean>>(this@UserFragment.mActivity) {
                        override fun onSuccess(data: Page<PlayLogBean>) {
                            val playLogBeans = data.list
                            playLogBeans.forEach {
                                val playScoreBean: PlayScoreBean = PlayScoreBean()
                                playScoreBean.vodName = it.vod_name
                                playScoreBean.vodImgUrl = it.vod_pic
                                if (it.percent.equals("NaN")) {
                                    playScoreBean.percentage = 0.0f
                                } else {
                                    try {
                                        playScoreBean.percentage = it.percent.toFloat()
                                    } catch (ex: Exception) {
                                    }
                                }
                                playScoreBean.typeId = it.type_id;
                                playScoreBean.vodId = it.vod_id.toInt();
                                playScoreBean.isSelect = false
                                playScoreBean.vodSelectedWorks = it.nid.toString()

                                playScoreBean.urlIndex = it.urlIndex
                                playScoreBean.curProgress = it.curProgress
                                playScoreBean.playSourceIndex = it.playSourceIndex


                                var gson: Gson = Gson()
                                var playScoreBeanStr = gson.toJson(playScoreBean).toString();
                                Log.i("playlog", "playScoreBean${playScoreBeanStr}")
                                playScoreBeans.add(playScoreBean)

                                if (playScoreBeans.size > 10) {
                                    playScoreAdapter.setNewData(playScoreBeans.subList(0, 10))
                                } else {
                                    playScoreAdapter.setNewData(playScoreBeans)
                                }

                            }

                            Log.i("playlog", "getPlayLogList11${data}");
                        }

                        override fun onError(e: ResponseException) {
                            Log.i("playlog", "getPlayLogList222")

                        }
                    })


        }
    }

    private class PlayScoreAdapter : BaseQuickAdapter<PlayScoreBean, BaseViewHolder>(R.layout.item_play_score_horizontal) {
        override fun convert(helper: BaseViewHolder, item: PlayScoreBean?) {
            item?.run {
                val name = if (item.typeId == 3) {
                    "$vodName $vodSelectedWorks"
                } else if (item.typeId == 1) {
                    "$vodName"
                } else {
                    "$vodName ${vodSelectedWorks}"
                }
                helper.setText(R.id.tvName, name)
                helper.setText(R.id.tvPlayProgress, "${(percentage * 100).toInt()}%")
//                Glide.with(helper.itemView.context)
//                        .load(vodImgUrl)
//                        .into(helper.getView<View>(R.id.ivImg) as ImageView)
                val mation: MultiTransformation<Bitmap> = MultiTransformation(CenterCrop(), RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL))
                Glide.with(helper.itemView.context)
                        .load(vodImgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(RequestOptions.bitmapTransform(mation))
                        .into(helper.getView<View>(R.id.ivImg) as ImageView)
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(): UserFragment {
            val args = Bundle()
            val fragment = UserFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onOpenShareEvent(event: OpenShareEvent) {
        if (!UserUtils.isLogin()) {
            LoginActivity.start()
        } else {
            ActivityUtils.startActivity(ShareActivity::class.java)
        }
    }

    fun gotoWeb(url: String) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val uri = Uri.parse(url)
        intent.data = uri
        if (intent.resolveActivity(App.getApplication().packageManager) != null) {
            startActivity(intent)
        } else {
            //要调起的应用不存在时的处理
        }
    }

    //获取群聊列表
    private fun getGroupChatList() {
        val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        RequestManager.execute(this, vodService.groupChat(), object : BaseObserver<GroupChatBean>() {
            override fun onSuccess(data: GroupChatBean) {
                val list = data.list
                for (i in list.indices) {
                    if (i == 0) {
                        userBinding.llPotato.visibility = View.VISIBLE
                        userBinding.linePotato.visibility = View.VISIBLE
                        userBinding.tvPotato.text = list[0].title
                        userBinding.llPotato.setOnClickListener {
                            gotoWeb(list[0].url)
                        }
                    } else if (i == 1) {
                        userBinding.llPlane.visibility = View.VISIBLE
                        userBinding.linePlane.visibility = View.VISIBLE
                        userBinding.tvPlane.text = list[1].title
                        userBinding.llPlane.setOnClickListener {
                            gotoWeb(list[1].url)
                        }
                    }
                }
            }

            override fun onError(e: ResponseException) {

            }
        })
    }


}
