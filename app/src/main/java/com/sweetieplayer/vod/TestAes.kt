package com.sweetieplayer.vod

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import jaygoo.library.m3u8downloader.M3U8DownloaderConfig
import jaygoo.library.m3u8downloader.utils.AesUtils
import jaygoo.library.m3u8downloader.utils.MUtils.isByte
import jaygoo.library.m3u8downloader.utils.MUtils.keyBytes
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class TestAes : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
//        val doneInfos: List<M3u8DoneInfo> = DownloadPresenter.getM3u8DoneAll()
//
//
        val filePath: String = M3U8DownloaderConfig.getSaveDir()
        val file = File(filePath)
        val files = file.list()
        val movieFile: String = M3U8DownloaderConfig.getSaveDir().plus(File.separator).plus(files[0]).plus(File.separator)
        val m3u8path: String = movieFile.plus("local.m3u8")


        Log.d(TAG, movieFile)
        val m3u8File = File(m3u8path)
        var ins: List<String> = m3u8File.readLines().filter { it.endsWith(".ts") }



        for ((index, e) in ins.withIndex()) {
            println("下标=$index----元素=$e")
        }


        var outPath = movieFile.plus("out")
        var outFile =File(outPath)

            outFile.mkdir()


        for ((withIndex, s) in ins.withIndex()) {

            val f = File(movieFile.plus(s))

            val inputStream1 = FileInputStream(f)
            val available: Int = inputStream1.available()
            var bytes = inputStream1.readBytes()
            if (bytes.size < available) bytes = ByteArray(available)

            inputStream1.read(bytes)
            val f222 = File(outPath.plus(File.separator).plus("哈${withIndex}.mp4"))
            var outputStream1 = FileOutputStream(f222)
            //开始解密ts片段，这里我们把ts后缀改为了xyz，改不改都一样
            //开始解密ts片段，这里我们把ts后缀改为了xyz，改不改都一样
            val decrypt = decrypt(bytes, available, "e72895d5dbadfd9c", "", "AES-128")

            if (decrypt == null) outputStream1.write(bytes, 0, available) else outputStream1.write(decrypt)

            Log.d(TAG, "第${withIndex}个")
//            return
        }

        //删除测试文件
//        delelteFile(movieFile)

        //读取m3u8 文件

    }

    /**
     * 删除测试文件
     */
    private fun delelteFile(path: String) {
        var files: File = File(path)
        var list = files.listFiles()
        for (file in list) {
            if (file.name.contains("哈")) {
                Log.d(TAG, "我的名字\t${file.name}\t我要被删除了")
                file.delete()
            }
        }
    }

    /**
     * 解密ts
     *
     * @param sSrc   ts文件字节数组
     * @param length
     * @param sKey   密钥
     * @return 解密后的字节数组
     */
    @Throws(java.lang.Exception::class)
    private fun decrypt(sSrc: ByteArray, length: Int, sKey: String, iv: String, method: String): ByteArray? {
        if (AesUtils.isNotEmpty(method) && !method.contains("AES")) throw Exception("未知的算法！")
        // 判断Key是否正确
        if (AesUtils.isEmpty(sKey)) return null
        // 判断Key是否为16位
        if (sKey.length != 16 && !isByte) {
            throw Exception("Key长度不是16位！")
        }
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val keySpec = SecretKeySpec(if (isByte) keyBytes else sKey.toByteArray(StandardCharsets.ISO_8859_1), "AES")
        var ivByte: ByteArray
        ivByte = if (iv.startsWith("0x")) AesUtils.hexStringToByteArray(iv.substring(2)) else iv.toByteArray()
        if (ivByte.size != 16) ivByte = ByteArray(16)
        //如果m3u8有IV标签，那么IvParameterSpec构造函数就把IV标签后的内容转成字节数组传进去
        val paramSpec: AlgorithmParameterSpec = IvParameterSpec(ivByte)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec)
        return cipher.doFinal(sSrc, 0, length)

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