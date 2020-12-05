package cn.mahua.vod.ui.account

import cn.mahua.vod.R
import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.LoginBean
import cn.mahua.vod.netservice.VodService
import cn.mahua.vod.utils.AgainstCheatUtil
import cn.mahua.vod.utils.Retrofit2Utils
import com.blankj.utilcode.util.ToastUtils
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import kotlinx.android.synthetic.main.activity_change_nickname.*
import org.greenrobot.eventbus.EventBus

class ChangeNicknameActivity : BaseActivity() {

    override fun getLayoutResID(): Int {
        return R.layout.activity_change_nickname
    }

    override fun initListener() {
        super.initListener()
        rlBack.setOnClickListener {
            finish()
        }
        tvFinish.setOnClickListener {
            changeNickname()
        }
    }

    private fun changeNickname() {
        val newNickName = etNickname.text.trim().toString()
        if(newNickName.isEmpty()){
            ToastUtils.showShort(R.string.new_nickname_empty)
            return
        }
        var vodService= Retrofit2Utils.INSTANCE.createByGson(VodService::class.java)
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(this, vodService.changeNickname(newNickName), object : LoadingObserver<String>(mActivity) {
            override fun onSuccess(data: String) {
                ToastUtils.showShort(R.string.change_nickname_success)
                EventBus.getDefault().post(LoginBean())
                finish()
            }

            override fun onError(e: ResponseException) {
            }

        })
    }

}
