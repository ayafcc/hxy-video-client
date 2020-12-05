package cn.mahua.vod.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.Unbinder
import cn.mahua.vod.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.umeng.analytics.MobclickAgent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity() : SupportActivity() {

    private var unbinder: Unbinder? = null
    lateinit var mActivity: BaseActivity

    private val mDisposablePool: CompositeDisposable by lazy {
        CompositeDisposable()
    }
//    protected val vodService : VodService by lazy {
//        Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        setContentView(getLayoutResID())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.parseColor("#E8E8E8")
        }
        mActivity = this
        BaseApplication.setContextRef(this)
        unbinder = ButterKnife.bind(this)
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java) || isUseEventBus()) {
            EventBus.getDefault().register(this)
        }

        initView()
        initListener()
        initData()
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    protected abstract fun getLayoutResID(): Int

    protected open fun initView() {

    }

    protected open fun initListener() {

    }

    protected open fun initData() {

    }

    protected open fun isUseEventBus() : Boolean {
        return false
    }


    fun addDisposable(disposable: Disposable) {
        if (mDisposablePool.isDisposed)
            mDisposablePool.add(disposable)
    }

    fun removeDisposable(disposable: Disposable) {
        if (mDisposablePool.isDisposed)
            mDisposablePool.remove(disposable)
    }

    fun detach() {
        if (!mDisposablePool.isDisposed)
            mDisposablePool.clear()
    }


    override fun onDestroy() {
        LogUtils.e("onDestroy:--$this")
        super.onDestroy()
        detach()
        if (unbinder != null) unbinder!!.unbind()
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java) || isUseEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(0, R.anim.slide_out_right)
    }



}
