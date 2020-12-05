package cn.mahua.vod;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.dpuntu.downloader.Md5Utils;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.seamless.util.ByteArray;
import org.seamless.util.io.Base64Coder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

import jaygoo.library.m3u8downloader.M3U8EncryptHelper;
import jaygoo.library.m3u8downloader.bean.M3U8;
import jaygoo.library.m3u8downloader.bean.M3U8Ts;
import jaygoo.library.m3u8downloader.bean.M3U8TsInfo;
import jaygoo.library.m3u8downloader.db.table.M3u8DoneInfo;
import jaygoo.library.m3u8downloader.utils.AES128Utils;
import jaygoo.library.m3u8downloader.utils.MD5Utils;
import jaygoo.library.m3u8downloader.utils.MUtils;
import kotlin.text.Charsets;

public class MergeTestActivity2 extends AppCompatActivity {

    private static final String TAG = "MergeTestActivity2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_merge_test2);
//
//        String path = "storage/emulated/0/M3u8Downloader/aHR0cHM6Ly92aWRlby5rc3N6bnV1LmNuLzIwMjAwOTE5L1oyOEJaeVpzL2luZGV4Lm0zdTg=/json.txt";
//
//        FileOutputStream fos;
//        File file = new File(path);
//        Log.d(TAG, "file.exists():" + file.exists());
//
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//
//            new FileOutputStream("storage/emulated/0/M3u8Downloader/aHR0cHM6Ly92aWRlby5rc3N6bnV1LmNuLzIwMjAwOTE5L1oyOEJaeVpzL2luZGV4Lm0zdTg=" + File.separator + "play.mp4");
//            InputStreamReader read = new InputStreamReader(
//                    new FileInputStream(file), Charset.defaultCharset());//考虑到编码格式
//            BufferedReader bufferedReader = new BufferedReader(read);
//            String lineTxt;
//            while ((lineTxt = bufferedReader.readLine()) != null) {
//                stringBuilder.append(lineTxt);
//            }
//            read.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String read = stringBuilder.toString();
//        Log.d(TAG, "读到的数据;" + read);
//        try {
////            String deCodeString = MD5Utils.decode(read);
//            M3U8 m3U8 = new Gson().fromJson(deCodeString, M3U8.class);
//            for (M3U8TsInfo m3U8TsInfo : m3U8.getM3U8TsInfoList()) {
//                Log.d(TAG, m3U8TsInfo.getMethod());
//                if (!m3U8TsInfo.getMethod().contains("AES")) {
//
//                } else {
//
//                    for (M3U8Ts m3U8Ts : m3U8TsInfo.getM3U8TsList()) {
//
//
//                        String fileName = M3U8EncryptHelper.encryptFileName(null, m3U8Ts.obtainEncodeTsFileName());
//                        //
////
//
//                        File f = new File("storage/emulated/0/M3u8Downloader/aHR0cHM6Ly92aWRlby5rc3N6bnV1LmNuLzIwMjAwOTE5L1oyOEJaeVpzL2luZGV4Lm0zdTg=" + File.separator + fileName);
//
//                        InputStream inputStream1 = new FileInputStream(f);
//                        int available = inputStream1.available();
//
//                        Log.d(TAG, f.getAbsolutePath());
//                        byte[] bytes = MUtils.readFile( f.getAbsolutePath());
////
////                    if (bytes.length < available) {
////                        bytes = new ByteArray(available);
////                    }
//
//                        inputStream1.read(bytes);
//                        File f222 = new File(f.getAbsolutePath());
//                        Log.d(TAG, f.getAbsolutePath());
//                        OutputStream outputStream1 = new FileOutputStream(f222);
//                        //开始解密ts片段，这里我们把ts后缀改为了xyz，改不改都一样
//                        //开始解密ts片段，这里我们把ts后缀改为了xyz，改不改都一样
//                        byte[] decrypt = AES128Utils.decrypt(bytes, available, m3U8TsInfo.getKeyValue(), "", "AES-128");
//                        if (decrypt == null) {
//                            outputStream1.write(bytes, 0, available);
//                        } else {
//                            outputStream1.write(decrypt);
//                        }
////                    if (f.isFile() && f.exists()) {
////                        IOUtils.copyLarge(new FileInputStream(f), fos);
////                    }
//                        outputStream1.close();
//                        inputStream1.close();
////                        Log.d(TAG, "具体:" + fileName);
//                    }
//                }
//
//            }
//
////            fos.close();
////        return MUtils.clearDir(new File(rootPath), outFileName);
//            Log.d(TAG, deCodeString);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Log.d(TAG, "异常：" + e.getMessage());
//        }
//

    }
}