package cn.mahua.vod

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import jaygoo.library.m3u8downloader.M3U8DownloaderConfig
import jaygoo.library.m3u8downloader.utils.MUtils

import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class TestActivity2 : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
//        val doneInfos: List<M3u8DoneInfo> = DownloadPresenter.getM3u8DoneAll()
//
//
        val filePath: String = M3U8DownloaderConfig.getSaveDir()
//        Log.d(TAG, "____${filePath}")
        val file = File(filePath)
        val files = file.list()

        val movieFile: String = M3U8DownloaderConfig.getSaveDir().plus(File.separator).plus(files[0]).plus(File.separator)
        val m3u8path: String = movieFile.plus("local.m3u8")


        Log.d(TAG, movieFile)
        val m3u8File = File(m3u8path)
        val ins: List<String> = m3u8File.readLines().filter { it.endsWith(".ts") }
        val fos = FileOutputStream(movieFile.plus("哈哈哈哈.mp4"));
        for (s in ins) {
            val f = File(movieFile.plus(s))
            Log.d(TAG,  f.name)
            if (f.isFile && f.exists()) {
                Log.d(TAG,"HHHHHH")
              IOUtils.copyLarge(FileInputStream(f), fos);
            }
        }

        fos.close();

        val f222=File(movieFile.plus("哈哈哈哈.mp4"))
        MUtils.clearDir(File(movieFile),f222.name)
        Log.d(TAG,"HHHHHH"+     f222.exists() )
        Log.d(TAG,"HHHHHH"+     f222.absolutePath)



//        var inputStream=openText(movieFile)
//        Log.d(TAG,inputStream)
//
//        val f2List=File(movieFile)
//
//        for (s in f2List.list()) {
//          Log.d(TAG, "file_name:$s")
//
//        }

        //读取m3u8 文件


    }

    companion object {
        const val TAG = "vodBean"

        //读取文本文件
        fun openText(path: String?): String? {
            var readStr: String? = ""
            try {
                val fis = FileInputStream(path)
                val b = ByteArray(fis.available())
                fis.read(b)
                readStr = String(b)
                fis.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return readStr
        }
    }
}