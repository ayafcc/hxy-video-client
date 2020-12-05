package cn.mahua.vod.utils

import android.util.Log
import cn.mahua.vod.ApiConfig
import cn.mahua.vod.bean.UserInfoBean
import com.blankj.utilcode.util.SPUtils
import okhttp3.Cookie
import java.util.*

object UserUtils {
    @JvmStatic
    var userInfo: UserInfoBean? = null

    @JvmStatic
    val needLogin: List<String> by lazy {
        val list = ArrayList<String>()
        list.add(ApiConfig.getVideoProgress)
        list.add(ApiConfig.MY_EXPAND)
        list.add(ApiConfig.USER_INFO)
        list.add(ApiConfig.SEND_DANMU)
        list.add(ApiConfig.watchTimeLong)
        list.add(ApiConfig.TASK_LIST)
        list.add(ApiConfig.SIGN)
        list.add(ApiConfig.POINT_PURCHASE)
        list.add(ApiConfig.CARD_BUY)
        list.add(ApiConfig.UPGRADE_GROUP)
        list.add(ApiConfig.CHANGE_NICKNAME)
        list.add(ApiConfig.CHANGE_AVATOR)
        list.add(ApiConfig.GOLD_WITHDRAW)
        list.add(ApiConfig.CHANGE_AGENTS)
        list.add(ApiConfig.AGENTS_SCORE)
        list.add(ApiConfig.COMMENT)
        list.add(ApiConfig.FEEDBACK)
        list.add(ApiConfig.COLLECTION)
        list.add(ApiConfig.COLLECTION_LIST)
        list.add(ApiConfig.SHARE_SCORE)
        list.add(ApiConfig.SCORE)
        list.add(ApiConfig.BUY_VIDEO)
        list.add(ApiConfig.CHECK_VOD_TRYSEE)
        list.add(ApiConfig.PAY)
        list.add(ApiConfig.GOLD_TIP)
        list.add(ApiConfig.getVod)
        list.add(ApiConfig.SHARE_INFO)
        list.add(ApiConfig.addPlayLog)
        list.add(ApiConfig.getPlayLogList)
        list
    }

    @JvmStatic
    fun isLogin(): Boolean {
        return SPUtils.getInstance("user_cookie").getString("cookie_user_id").isNotEmpty()
    }

    @JvmStatic
    fun logout() {
        SPUtils.getInstance("user_cookie").clear(true)
    }

    @JvmStatic
    fun saveCookies(cookies: List<Cookie>) {
        for (cookie in cookies) {
            when {
                cookie.name.startsWith("user_id") -> {
                    Log.d("====cookie","====cookie"+cookie.value)
                    SPUtils.getInstance("user_cookie").put("cookie_user_id", cookie.value, true)
                    SPUtils.getInstance("user_cookie").put("cookie_user_id_domain", cookie.domain, true)
                }
                cookie.name.startsWith("user_name") -> {
                    SPUtils.getInstance("user_cookie").put("cookie_user_name", cookie.value, true)
                    SPUtils.getInstance("user_cookie").put("cookie_user_name_domain", cookie.domain, true)
                }
                cookie.name.startsWith("user_nick_name") -> {
                    SPUtils.getInstance("user_cookie").put("cookie_user_nick_name", cookie.value, true)
                    SPUtils.getInstance("user_cookie").put("cookie_user_nick_name_domain", cookie.domain, true)
                }
                cookie.name.startsWith("group_id") -> {
                    SPUtils.getInstance("user_cookie").put("cookie_group_id", cookie.value, true)
                    SPUtils.getInstance("user_cookie").put("cookie_group_id_domain", cookie.domain, true)
                }
                cookie.name.startsWith("group_name") -> {
                    SPUtils.getInstance("user_cookie").put("cookie_group_name", cookie.value, true)
                    SPUtils.getInstance("user_cookie").put("cookie_group_name_domain", cookie.domain, true)
                }
                cookie.name.startsWith("user_check") -> {
                    SPUtils.getInstance("user_cookie").put("cookie_user_check", cookie.value, true)
                    SPUtils.getInstance("user_cookie").put("cookie_user_check_domain", cookie.domain, true)
                }
                cookie.name.startsWith("user_portrait") -> {
                    SPUtils.getInstance("user_cookie").put("cookie_user_portrait", cookie.value, true)
                    SPUtils.getInstance("user_cookie").put("cookie_user_portrait_domain", cookie.domain, true)
                }
            }
        }
    }

    @JvmStatic
    fun getLocalCookie(): List<Cookie> {
        val cookies = ArrayList<Cookie>()
        if(isLogin()){
            cookies.add(Cookie.Builder().name("user_id").value(SPUtils.getInstance("user_cookie").getString("cookie_user_id", "")).domain(SPUtils.getInstance("user_cookie").getString("cookie_user_id_domain", "")).build())
            cookies.add(Cookie.Builder().name("user_name").value(SPUtils.getInstance("user_cookie").getString("cookie_user_name", "")).domain(SPUtils.getInstance("user_cookie").getString("cookie_user_name_domain", "")).build())
            cookies.add(Cookie.Builder().name("user_nick_name").value(SPUtils.getInstance("user_cookie").getString("cookie_user_nick_name", "")).domain(SPUtils.getInstance("user_cookie").getString("cookie_user_nick_name_domain", "")).build())
            cookies.add(Cookie.Builder().name("group_id").value(SPUtils.getInstance("user_cookie").getString("cookie_group_id", "")).domain(SPUtils.getInstance("user_cookie").getString("cookie_group_id_domain", "")).build())
            cookies.add(Cookie.Builder().name("group_name").value(SPUtils.getInstance("user_cookie").getString("cookie_group_name", "")).domain(SPUtils.getInstance("user_cookie").getString("cookie_group_name_domain", "")).build())
            cookies.add(Cookie.Builder().name("user_check").value(SPUtils.getInstance("user_cookie").getString("cookie_user_check", "")).domain(SPUtils.getInstance("user_cookie").getString("cookie_user_check_domain", "")).build())
            cookies.add(Cookie.Builder().name("user_portrait").value(SPUtils.getInstance("user_cookie").getString("cookie_user_portrait", "")).domain(SPUtils.getInstance("user_cookie").getString("cookie_user_portrait_domain", "")).build())
        }

        return cookies
    }
}
