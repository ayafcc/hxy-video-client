package cn.mahua.vod.utils

import cn.mahua.vod.App
import cn.mahua.vod.R
import com.blankj.utilcode.util.StringUtils

object AppColorUtils {

    private val styleList: MutableList<Pair<String, Int>> = mutableListOf()

    init {
        styleList.add(Pair("1080P", R.drawable.shape_tip_bg_blue))
        styleList.add(Pair("热播", R.drawable.shape_tip_bg_red))
        styleList.add(Pair("抢先", R.drawable.shape_tip_bg_purple))
        styleList.add(Pair("VIP", R.drawable.shape_tip_bg_orange))
        styleList.add(Pair("经典", R.drawable.shape_tip_bg_green))
    }

    @JvmStatic
    fun getTagBgResId(pos: Int, text: String?): Pair<String, Int> {
        if (App.tagConfig !=null && App.tagConfig.img == "1" ) {
            if (text == null || StringUtils.isEmpty(text)) {
                when (pos % 5) {
                    0 -> {
                        return styleList[0]
                    }
                    1 -> {
                        return styleList[1]
                    }
                    2 -> {
                        return styleList[2]
                    }
                    3 -> {
                        return styleList[3]
                    }
                    4 -> {
                        return styleList[4]
                    }
                    else -> {
                        return styleList[4]
                    }
                }
            } else {
                when (text) {
                    "1080P" -> {
                        return Pair("1080P", R.drawable.shape_tip_bg_blue)
                    }
                    "热播" -> {
                        return Pair("热播", R.drawable.shape_tip_bg_red)
                    }
                    "抢先" -> {
                        return Pair("抢先", R.drawable.shape_tip_bg_purple)
                    }
                    "VIP" -> {
                        return Pair("VIP", R.drawable.shape_tip_bg_orange)
                    }
                    "经典" -> {
                        return Pair("经典", R.drawable.shape_tip_bg_green)
                    }
                    else -> {
                        return Pair(text, R.drawable.shape_tip_bg_orange)
                    }
                }
            }

        } else {
            if (text != null && !StringUtils.isEmpty(text)) {
                when (text) {
                    "1080P" -> {
                        return Pair("1080P", R.drawable.shape_tip_bg_blue)
                    }
                    "热播" -> {
                        return Pair("热播", R.drawable.shape_tip_bg_red)
                    }
                    "抢先" -> {
                        return Pair("抢先", R.drawable.shape_tip_bg_purple)
                    }
                    "VIP" -> {
                        return Pair("VIP", R.drawable.shape_tip_bg_orange)
                    }
                    "经典" -> {
                        return Pair("经典", R.drawable.shape_tip_bg_green)
                    }
                    else -> {
                        return Pair(text, R.drawable.shape_tip_bg_orange)
                    }
                }
            } else {
                return Pair("", R.drawable.shape_tip_bg_orange)
            }
        }
    }
}