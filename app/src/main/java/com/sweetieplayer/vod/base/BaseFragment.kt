package com.github.StormWyrm.wanandroid.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : SupportFragment() {
    lateinit var mContext: Context
        //private set
    public lateinit var mActivity: AppCompatActivity
        private set
    open var isUseEventBus: Boolean = false

//    protected val vodService : VodService by lazy {
//        Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
//    }

    private val mDisposablePool: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private var isViewCreated: Boolean = false//见面是否创建成功
    private var isVisibleToUser: Boolean = false//是否对用户可见
    private var isDataLoaded: Boolean = false//数据是否已经请求
    private var isFragmentHidden: Boolean = true // 记录当前fragment的是否隐藏
    protected open var isNeedReload: Boolean = false //fragment再次可见的时候是否请求数据，默认位false 仅请求一次

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context.applicationContext
        mActivity = (context as? AppCompatActivity)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isUseEventBus)
            EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(getLayoutId(), container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        initView()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tryLoadData()
    }

    override fun onDestroy() {
        isViewCreated = false
        isVisibleToUser = false
        isDataLoaded = false
        isFragmentHidden = true
        if (isUseEventBus)
            EventBus.getDefault().unregister(this)

        detach()

        super.onDestroy()
    }

    /**
     * 使用ViewPager嵌套fragment时，切换ViewPager回调该方法
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        tryLoadData()
    }

    /**
     * 当调用fragment的hide(),show()等方法时会回调
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isFragmentHidden = hidden

        if (!hidden) {
            tryLoadData1()
        }
    }

    /**
     * show()、hide()场景下，尝试请求数据
     */
    protected fun tryLoadData1() {
        if (!isParentHidden() && (isNeedReload || !isDataLoaded)) {
            initLoad()
            isDataLoaded = true
            dispatchParentHiddenState()
        }
    }

    /**
     * show()、hide()场景下，当前fragment没隐藏，如果其子fragment也没隐藏，则尝试让子fragment请求数据
     */
    private fun dispatchParentHiddenState() {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (child in fragments) {
            if (child is BaseFragment && !child.isHidden) {
                child.tryLoadData1()
            }
        }
    }

    /**
     * show()、hide()场景下，父fragment是否隐藏
     */
    private fun isParentHidden(): Boolean {
        val fragment: Fragment? = parentFragment
        if (fragment == null) {
            return false
        } else if (fragment is BaseFragment && !fragment.isHidden) {
            return false
        }
        return true
    }

    /**
     * ViewPager场景下，尝试请求数据
     */
    protected fun tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && (isNeedReload || !isDataLoaded)) {
            initLoad()
            isDataLoaded = true
            dispatchParentVisibleState()
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private fun isParentVisible(): Boolean {
        val fragment: Fragment? = parentFragment
        return fragment == null || (fragment is BaseFragment && fragment.isVisibleToUser)
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private fun dispatchParentVisibleState() {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (child in fragments) {
            if (child is BaseFragment && child.isVisibleToUser) {
                child.tryLoadData()
            }
        }
    }


    @LayoutRes
    abstract fun getLayoutId(): Int

    protected open fun initLoad() {}

    protected open fun initView() {}

    protected open fun initListener() {}

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

}