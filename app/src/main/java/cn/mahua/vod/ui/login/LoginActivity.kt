package cn.mahua.vod.ui.login

import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.view.View
import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.LoginBean
import cn.mahua.vod.bean.OpenRegister
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import com.blankj.utilcode.util.*
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class LoginActivity : BaseActivity(), Handler.Callback {
    private var curType = 0 //默认为登录类型
    private var isOpenRegister: Boolean = true
    private val WHAT_COUNT = 1
    private var mHanlder: Handler? = null
    private val MAX_NUM = 60
    private var index = MAX_NUM
    private var timer: Timer? = null
    private var task: TimerTask? = null

    override fun getLayoutResID(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        super.initView()
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        checkIsOpenRegister()
        mHanlder = Handler(this)
    }

    override fun initListener() {
        super.initListener()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position ?: 0) {
                    0 -> {
                        curType = 0
                        btn_login.text = StringUtils.getString(R.string.login)
//                        et_login_e3.visibility = View.GONE
                        tv_login_tip.text = StringUtils.getString(R.string.login_hit)
                        et_verify.visibility = View.GONE
                        tv_send.visibility = View.GONE
                    }
                    1 -> {
                        curType = 1
                        btn_login.text = StringUtils.getString(R.string.register)
//                        et_login_e3.visibility = View.VISIBLE
                        tv_login_tip.text = StringUtils.getString(R.string.register_hit)
                        if (isOpenRegister) {
                            et_verify.visibility = View.VISIBLE
                            tv_send.visibility = View.VISIBLE
//                            et_login_e3.visibility = View.GONE
                        } else {
                            et_verify.visibility = View.GONE
                            tv_send.visibility = View.GONE
//                            et_login_e3.visibility = View.VISIBLE
                        }
                    }
                }
            }

        })
        iv_login_back.setOnClickListener {
            finish()
        }

        btn_login.setOnClickListener {
            if (curType == 0) {
                login()
            } else {
                if (isOpenRegister) {
                    registerByCode()
                } else {
                    register()
                }
            }
        }
        tv_send.setOnClickListener {
            sendVerifyCode()
        }
    }

    private fun login() {
        if (check()) {
            val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
            if (AgainstCheatUtil.showWarn(vodService)) {
                return;
            }
            val loginObservable = vodService
                    .login(et_login_e1.text.trim().toString(), et_login_e2.text.trim().toString())
            RequestManager.execute(this, loginObservable, object : LoadingObserver<String>(this) {
                override fun onSuccess(data: String) {
                    ToastUtils.showShort(R.string.login_success)
                    EventBus.getDefault().post(LoginBean())
                    finish()
                }

                override fun onError(e: ResponseException) {
                }
            })
        }

    }

    private fun registerByCode() {
        if (check()) {
            val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
            if (AgainstCheatUtil.showWarn(vodService)) {
                return;
            }
            val registerObservable = vodService
                    .registerByCode(et_login_e1.text.trim().toString(), et_login_e2.text.trim().toString(),
                            et_verify.text.trim().toString())
            RequestManager.execute(this, registerObservable, object : LoadingObserver<String>(this) {
                override fun onSuccess(data: String) {
                    ToastUtils.showShort(R.string.register_success)
                    tabLayout.getTabAt(0)?.select()
                }

                override fun onError(e: ResponseException) {

                }
            })
        }
    }

    private fun register() {
        if (check()) {
            val vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
            if (AgainstCheatUtil.showWarn(vodService)) {
                return;
            }
            val registerObservable = vodService
                    .register(et_login_e1.text.trim().toString(), et_login_e2.text.trim().toString(),et_login_e2.text.trim().toString())
            RequestManager.execute(this, registerObservable, object : LoadingObserver<String>(this) {
                override fun onSuccess(data: String) {
                    ToastUtils.showShort(R.string.register_success)
                    tabLayout.getTabAt(0)?.select()
                }

                override fun onError(e: ResponseException) {

                }
            })
        }
    }

    private fun checkIsOpenRegister() {
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        val registerObservable = vodService.openRegister()
        RequestManager.execute(this, registerObservable, object : LoadingObserver<OpenRegister>(this) {
            override fun onSuccess(data: OpenRegister) {
                isOpenRegister = data.phone == "1"
                if (isOpenRegister) {
                    if (curType == 1) {
                        et_verify.visibility = View.VISIBLE
                        tv_send.visibility = View.VISIBLE
//                        et_login_e3.visibility = View.GONE
                    }
                } else {
//                    et_login_e3.visibility = View.VISIBLE
                }
            }

            override fun onError(e: ResponseException) {

            }
        })
    }

    private fun sendVerifyCode() {
        val phone = et_login_e1.text.trim().toString()
        if (phone.isEmpty()) {
            ToastUtils.showShort(R.string.phone_isempty)
            return
        }
        var vodService = Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return
        }
        startTimer()
        val registerObservable = vodService
                .sendVerifyCode(phone)
        RequestManager.execute(this, registerObservable, object : LoadingObserver<String>(this) {
            override fun onSuccess(data: String) {
                ToastUtils.showShort(R.string.verify_code_success)
            }

            override fun onError(e: ResponseException) {
                cancelTimer()
            }
        })
    }


    private fun startTimer() {
        tv_send.isEnabled = false
        timer = Timer()
        task = object : TimerTask() {
            override fun run() {
                if (index <= 1) {
                    cancelTimer()
                } else {
                    index--
                    mHanlder?.sendMessage(Message.obtain(mHanlder, WHAT_COUNT, index, 0))
                }
            }
        }
        timer?.schedule(task, 0, 1000)
    }

    private fun cancelTimer() {
        index = MAX_NUM
        mHanlder?.sendMessage(Message.obtain(mHanlder, WHAT_COUNT, index, 0))
        task?.cancel()
        timer?.cancel()
    }

    private fun check(): Boolean {
        val username = et_login_e1.text.trim().toString()
        val password = et_login_e2.text.trim().toString()
//        val repassword = et_login_e3.text.trim().toString()
        val verifyCode = et_verify.text.trim().toString()
        if (StringUtils.isEmpty(username)) {
            ToastUtils.showShort(R.string.phone_isempty)
            return false
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort(R.string.password_isempty)
            return false
        }
        val isPhone = RegexUtils.isMobileSimple(username)
        if (!isPhone) {
            ToastUtils.showShort(R.string.phone_format_incorrect)
            return false
        }
        if (curType == 1) {
            if (isOpenRegister) {
                if (StringUtils.isEmpty(verifyCode)) {
                    ToastUtils.showShort(R.string.verify_code_isempty)
                    return false
                }
//            } else {
//                if (StringUtils.isEmpty(repassword)) {
//                    ToastUtils.showShort(R.string.repassword_isempty)
//                    return false
//                }
//                if (!password.equals(repassword)) {
//                    ToastUtils.showShort(R.string.repassowrd_not_correct)
//                    return false
//                }
            }
        }
        return true
    }

    companion object {
        fun start() {
            ActivityUtils.startActivity(LoginActivity::class.java, R.anim.slide_in_right, R.anim.no_anim)
        }
    }

    override fun handleMessage(msg: Message?): Boolean {
        return when (msg?.what) {
            WHAT_COUNT -> {
                if (msg.arg1 == MAX_NUM) {
                    tv_send.isEnabled = true
                    tv_send.text = "发送验证码"
                } else {
                    tv_send.text = "${index}s"
                }
                true
            }
            else -> {
                false
            }
        }
    }

}
