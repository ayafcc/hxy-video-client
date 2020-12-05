package cn.mahua.vod.ui.task

import android.os.Bundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import cn.mahua.vod.R
import jaygoo.library.m3u8downloader.utils.MUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.concurrent.thread

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
//        var url= "https://vod2.kssznuu.cn/20200920/jEa8mf1G/index.m3u8"
        var url= "https://vod2.kssznuu.cn/20200920/jEa8mf1G/1000kb/hls/index.m3u8"
        thread {
            val m3u8 = MUtils.getTsUrl(url)
        }




//        thread {
//            val data = getWebInfo()
////            runOnUiThread({
////                //这里进行更新UI操作
////                getWebInfo()
////            })
//        }

    }


    private fun getWebInfo() {
        try {
            //找水源，创建URL
            val url = URL("https://vod2.kssznuu.cn/20200920/jEa8mf1G/index.m3u8")
            //开水闸-openConnection
            val httpURLConnection = url.openConnection() as HttpURLConnection
            //建水管-InputStream
            val inputStream: InputStream = httpURLConnection.inputStream
            //建蓄水池蓄水-InputStreamReader
            val reader = InputStreamReader(inputStream, "UTF-8")
            //水桶盛水-BufferedReader
            val bufferedReader = BufferedReader(reader)
            val stringBuffer = StringBuffer()
            var temp: String? = null
            while (bufferedReader.readLine().also { temp = it } != null) {
                stringBuffer.append(temp)
            }
            bufferedReader.close()
            reader.close()
            inputStream.close()

            Log.d("MUtils", "第$stringBuffer")
//            println(stringBuffer.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}