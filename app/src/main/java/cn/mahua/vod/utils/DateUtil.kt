package cn.mahua.vod.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {

        var yyyyMMddHHmmChinaFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA)
        var yyyyMMddHHmmssLinePointFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

        var yyyyMMddHHmmssSSSFormat = SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS", Locale.CHINA)
        var yyyyMMddHHmmsslineFormat = SimpleDateFormat("yyyy-MM-dd-HH.mm.ss", Locale.CHINA)
        var yyyyMMddHHmmFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
        var yyyyMMddLineFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

        var yyMMddHHmmFormat = SimpleDateFormat("yy-MM-dd  HH:mm", Locale.CHINA)
        var yyMMddFormat = SimpleDateFormat("yy-MM-dd", Locale.CHINA)

        var yyyyMMddHHmmssFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
        var yyyyMMddFormat = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
        var yyyyMMddPointFormat = SimpleDateFormat("yyyy.MM.dd", Locale.CHINA)

        var MMddyyHHmmFormat = SimpleDateFormat("MM/dd/yy HH:mm", Locale.CHINA)
        var MMddHHmmFormat = SimpleDateFormat("MM-dd  HH:mm", Locale.CHINA)
        var MMddFormat = SimpleDateFormat("MM-dd", Locale.CHINA)

        var HHmmssSSSFormat = SimpleDateFormat("HH:mm:ss:SSS", Locale.CHINA)
        var HHmmssFormat = SimpleDateFormat("HH:mm:ss", Locale.CHINA)
        var HHmmFormat = SimpleDateFormat("HH:mm", Locale.CHINA)


        @JvmStatic
        fun getyyyyMMddHHmmChina(date: Long): String {
            return yyyyMMddHHmmChinaFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyyyMMddHHmmssLinePoint(date: Long): String {
            return yyyyMMddHHmmssLinePointFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyyyMMddHHmmssSSS(date: Long): String {
            return yyyyMMddHHmmssSSSFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyyyMMddHHmmssline(date: Long): String {
            return yyyyMMddHHmmsslineFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyyyMMddHHmm(date: Long): String {
            return yyyyMMddHHmmFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyyyMMddLine(date: Long): String {
            return yyyyMMddLineFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyMMddHHmm(date: Long): String {
            return yyMMddHHmmFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyMMdd(date: Long): String {
            return yyMMddFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyyyMMddHHmmss(date: Long): String {
            return yyyyMMddHHmmssFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyyyMMdd(date: Long): String {
            return yyyyMMddFormat.format(Date(date))
        }

        @JvmStatic
        fun getyyyyMMddPoint(date: Long): String {
            return yyyyMMddPointFormat.format(Date(date))
        }

        @JvmStatic
        fun getMMddyyHHmm(date: Long): String {
            return MMddyyHHmmFormat.format(Date(date))
        }

        @JvmStatic
        fun getMMddHHmm(date: Long): String {
            return MMddHHmmFormat.format(Date(date))
        }

        @JvmStatic
        fun getMMdd(date: Long): String {
            return MMddFormat.format(Date(date))
        }

        @JvmStatic
        fun getHHmmssSSS(date: Long): String {
            return HHmmssSSSFormat.format(Date(date))
        }

        @JvmStatic
        fun getHHmmss(date: Long): String {
            return HHmmssFormat.format(Date(date))
        }

        @JvmStatic
        fun getHHmmHm(date: Long): String {
            return HHmmFormat.format(Date(date))
        }
    }
}