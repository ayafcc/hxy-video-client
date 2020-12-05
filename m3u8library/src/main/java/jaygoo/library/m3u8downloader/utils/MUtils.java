package jaygoo.library.m3u8downloader.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jaygoo.library.m3u8downloader.M3U8DownloaderConfig;
import jaygoo.library.m3u8downloader.OnTaskMergeListener;
import jaygoo.library.m3u8downloader.bean.M3U8;
import jaygoo.library.m3u8downloader.bean.M3U8Ts;
import jaygoo.library.m3u8downloader.bean.M3U8TsInfo;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/11/18
 * 描    述: 工具类
 * ================================================
 */
public class MUtils {
    private static final String TAG = "MUtils";
    //重试次数
    public static int retryCount = 30;

    //链接连接超时时间（单位：毫秒）
    public static long timeoutMillisecond = 3000L;
    //自定义请求头
    public static Map<String, Object> requestHeaderMap = new HashMap<>();
    //key是否为字节
    public static boolean isByte = false;
    //密钥字节
    public static byte[] keyBytes = new byte[16];
    private static OnTaskMergeListener onmerge ;
    public static void  mergelist(OnTaskMergeListener onmerges){
        onmerge= onmerges;
    }
    /**
     * 将Url转换为M3U8对象
     *
     * @param url
     * @return
     */
    public static M3U8 parseIndex(String url, StringBuilder content) {
        StringBuilder urlContent;
        Log.d("MUtils", "" + url);

        String basePath = HttpUtils.getBasePath(url);
        if (content == null || TextUtils.isEmpty(content.toString())) {
            urlContent = getUrlContent111(url);
            //多加一层判断是为了合并域名与 二次解析后的值 再次请求即可获取连接 地址
            if (urlContent.toString().trim().endsWith(".m3u8")) {
                Log.d("MUtils", "需解析文本:" + urlContent);
                //路径拼接错误
                //将//转回&&
                url = url.substring(0, url.lastIndexOf("/") + 1);
                url = HttpUtils.mergeUrl(url, getM3u8ListIndex(urlContent.toString()));
                Log.d("MUtils", "最終地址:" + url);
                urlContent = getUrlContent111(url);
            }
        } else {
            urlContent = content;
        }

        M3U8 ret = new M3U8();
        ret.setBasePath(basePath);


//         需要二度解析带上前缀  https://vod2.kssznuu.cn/20200920/jEa8mf1G/1000kb/hls/index.m3u8
        Log.d("MUtils", "地址:" + urlContent);
        float seconds = 0;
        String[] split = urlContent.toString().split("\\n");

        String ss = "";
        String relativeUrl = url.substring(0, url.lastIndexOf("/") + 1);
        //将ts片段链接加入set集合
        List<M3U8TsInfo> m3U8TsInfoList = new ArrayList<>();

        List<M3U8Ts> m3U8s = new ArrayList<>();

        M3U8TsInfo m3U8TsInfo = new M3U8TsInfo();
        int index = 0;
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            Log.d(TAG, "ssssssssss" + s);
            //如果有加密文件的话
            if (s.contains("#EXT-X-KEY")) {

                if (index > 0) {
                    m3U8TsInfo.setM3U8TsList(m3U8s);


                    m3U8TsInfoList.add(m3U8TsInfo);

                    ret.setM3U8TsInfoList(m3U8TsInfoList);
                    m3U8s = new ArrayList<>();
                }
                m3U8TsInfo = getKey(basePath, s);

                index++;
            } else if (s.contains("#EXTINF")) {
                ss = split[++i];
                Pattern p = Pattern.compile(":(.*?),");//正则表达式，取=和|之间的字符串，不包括=和|
                Matcher m = p.matcher(s);
                while (m.find()) {
                    seconds = Float.parseFloat(m.group(1));//m.group(1)不包括这两个字符
                }
                M3U8Ts m3U8Ts = new M3U8Ts(HttpUtils.isUrl(ss) ? ss : HttpUtils.mergeUrl(relativeUrl, ss), seconds);
//                ret.addTs(m3U8Ts);

                m3U8s.add(m3U8Ts);
                Log.d("MUtils", "切片:" + m3U8Ts);

            }
            //防止到达未且未出现加密
            if (s.contains("EXT-X-ENDLIST")) {
                if (index == 0) {
                    m3U8TsInfo = new M3U8TsInfo();
                    m3U8TsInfo.setMethod("METHOD");
                }
                m3U8TsInfo.setM3U8TsList(m3U8s);
                m3U8TsInfoList.add(m3U8TsInfo);
                ret.setM3U8TsInfoList(m3U8TsInfoList);
            }

            seconds = 0;


        }

        return ret;
    }


    public static int count = 1;

    /**
     * 获取所有的ts片段下载链接
     *
     * @return 链接是否被加密，null为非加密
     */

    public static M3U8 getTsUrl(String url) {
        M3U8 m3U8 = null;
        StringBuilder content = getUrlContent111(url);

        //判断是否是m3u8链接
        if (!content.toString().contains("#EXTM3U")) {
            if (count < 4) {
                url = url.replace("http", "https");
                count++;
                content = getUrlContent111(url);
            } else if (count > 4) {
                count = 1;
                return null;
            }
        }
        Log.d("MUtils", "Content为\t" + count);
        Log.d("MUtils", "Content为\t" + content);
        Log.d("MUtils", "url为\t" + url);
        String[] split = content.toString().split("\\n");
        String keyUrl = "";
        for (String s : split) {
            //如果含有此字段，则说明只有一层m3u8链接
            if (s.contains("#EXT-X-KEY") || s.contains("#EXTINF")) {
                keyUrl = url;
                break;
            }
            //如果含有此字段，则说明ts片段链接需要从第二个m3u8链接获取
            if (s.contains(".m3u8")) {
                if (HttpUtils.isUrl(s)) {
                    m3U8 = parseIndex(s, null);
                    return m3U8;

                }
                String relativeUrl = url.substring(0, url.lastIndexOf("/") + 1);
                if (s.startsWith("/"))
                    s = s.replaceFirst("/", "");
                keyUrl = HttpUtils.mergeUrl(relativeUrl, s);
                break;
            }
        }
        if (TextUtils.isEmpty(keyUrl)) {
            Log.d("MUtils", "未发现key链接！");
        }
        m3U8 = parseIndex(keyUrl, null);
        return m3U8;
    }


    /**
     * 模拟http请求获取内容
     *
     * @param urls http链接
     * @return 内容
     */
    private static StringBuilder getUrlContent(String urls) {
        int count = 1;
        HttpURLConnection httpURLConnection = null;
        StringBuilder content = new StringBuilder();
        while (count <= retryCount) {
            try {
                URL url = new URL(urls);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout((int) timeoutMillisecond);
                httpURLConnection.setReadTimeout((int) timeoutMillisecond);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoInput(true);
                for (Map.Entry<String, Object> entry : requestHeaderMap.entrySet())
                    httpURLConnection.addRequestProperty(entry.getKey(), entry.getValue().toString());
                String line;
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null)
                    content.append(line).append("\n");
                bufferedReader.close();
                inputStream.close();
                break;
            } catch (Exception e) {
                Log.d("MUtils", "第" + count + "获取链接重试！\t" + urls + "\t" + e.getMessage());
                count++;
//                    e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }
//        if (count > retryCount)
//            throw new M3u8Exception("连接超时！");
        return content;
    }


    private static StringBuilder getUrlContent111(String urls) {

        StringBuilder stringBuffer = new StringBuilder();
        try {
            //找水源，创建URL
            URL url = new URL(urls);
            //开水闸-openConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //建水管-InputStream
            InputStream inputStream = httpURLConnection.getInputStream();
            //建蓄水池蓄水-InputStreamReader
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            //水桶盛水-BufferedReader
            BufferedReader bufferedReader = new BufferedReader(reader);


            String temp;

            while ((temp = bufferedReader.readLine()) != null) {
                stringBuffer.append(temp).append("\n");
            }
            bufferedReader.close();
            reader.close();
            inputStream.close();

            Log.e("MAIN", stringBuffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer;
    }

    /**
     * 清空文件夹
     *
     * @return
     */
    public static boolean clearDir(File dir, String excludeFile) {
        if (dir.exists()) {// 判断文件是否存在
            if (dir.isFile()) {// 判断是否是文件
                return dir.delete();// 删除文件
            } else if (dir.isDirectory()) {// 否则如果它是一个目录
                File[] files = dir.listFiles();// 声明目录下所有的文件 files[];
                for (File file : files) {// 遍历目录下所有的文件
                    if (!TextUtils.isEmpty(excludeFile) && excludeFile.equals(file.getName())) {
                        continue;
                    } else if (file.getName().contains("json") || file.getName().contains("local")) {
                        continue;
                    }
                    clearDir(file, null);// 把每个文件用这个方法进行迭代
                }
                return true;
            }
        }
        return true;
    }

    /**
     * 清空文件夹
     */
    public static boolean clearDir(File dir) {
        if (dir.exists()) {// 判断文件是否存在
            if (dir.isFile()) {// 判断是否是文件
                return dir.delete();// 删除文件
            } else if (dir.isDirectory()) {// 否则如果它是一个目录
                File[] files = dir.listFiles();// 声明目录下所有的文件 files[];
                for (File file : files) {// 遍历目录下所有的文件

                    clearDir(file, null);// 把每个文件用这个方法进行迭代
                }
                return dir.delete();// 删除文件夹
            }
        }
        return true;
    }

    private static float KB = 1024;
    private static float MB = 1024 * KB;
    private static float GB = 1024 * MB;

    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long size) {
        if (size >= GB) {
            return String.format("%.1f GB", size / GB);
        } else if (size >= MB) {
            float value = size / MB;
            return String.format(value > 100 ? "%.0f MB" : "%.1f MB", value);
        } else if (size >= KB) {
            float value = size / KB;
            return String.format(value > 100 ? "%.0f KB" : "%.1f KB", value);
        } else {
            return String.format("%d B", size);
        }
    }

    /**
     * 生成本地m3u8索引文件，ts切片和m3u8文件放在相同目录下即可
     *
     * @param m3u8Dir
     * @param m3U8
     */
    public static File createLocalM3U8(File m3u8Dir, String fileName, M3U8 m3U8) throws IOException {
        return createLocalM3U8(m3u8Dir, fileName, m3U8, null);
    }

    /**
     * 生成AES-128加密本地m3u8索引文件，ts切片和m3u8文件放在相同目录下即可
     *
     * @param m3u8Dir
     * @param m3U8
     */
    public static File createLocalM3U8(File m3u8Dir, String fileName, M3U8 m3U8, String keyPath) throws IOException {
        File m3u8File = new File(m3u8Dir, fileName);
        BufferedWriter bfw = new BufferedWriter(new FileWriter(m3u8File, false));
        bfw.write("#EXTM3U\n");
        bfw.write("#EXT-X-VERSION:3\n");
        bfw.write("#EXT-X-MEDIA-SEQUENCE:0\n");
        bfw.write("#EXT-X-TARGETDURATION:13\n");
        for (M3U8TsInfo m3U8TsInfo : m3U8.getM3U8TsInfoList()) {
            if (TextUtils.isEmpty(m3U8TsInfo.getMethod())) {
                bfw.write("#EXT-X-KEY:METHOD=METHOD,URI=\"" + m3U8TsInfo.getKeyUri() + ",VI=" + m3U8TsInfo.getKeyValue() + "\"\n");

            } else {
                bfw.write("#EXT-X-KEY:METHOD=AES-128,URI=\"" + m3U8TsInfo.getKeyUri() + ",VI=" + m3U8TsInfo.getKeyValue() + "\"\n");
            }
            for (M3U8Ts m3U8Ts : m3U8TsInfo.getM3U8TsList()) {
                bfw.write("#EXTINF:" + m3U8Ts.getSeconds() + ",\n");
                bfw.write(m3U8Ts.obtainEncodeTsFileName());
                bfw.newLine();
            }
        }
        bfw.write("#EXT-X-ENDLIST");
        bfw.flush();
        bfw.close();
        return m3u8File;
    }


    public static byte[] readFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        int length = fis.available();
        byte[] buffer = new byte[length];
        fis.read(buffer);
        fis.close();
        return buffer;
    }

    public static void saveFile(byte[] bytes, String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }


    public static String getSaveFileDir(String url) {
        return M3U8DownloaderConfig.getSaveDir() + File.separator + MD5Utils.encode(url);
    }


    /**
     * 根据local.m3u8 解析
     *
     * @param rootPath
     * @param outFileName
     * @return
     * @throws IOException
     */
    public static boolean mergeTs(String rootPath, String outFileName) throws IOException {

        String m3u8path = rootPath + File.separator + "local.m3u8";
        //合并后的文件名
        outFileName = outFileName + ".ts";

        Log.d(TAG, m3u8path);
        List<String> stringList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(m3u8path));

        String line;

        while ((line = br.readLine()) != null) {//不知道文件中有多少行数据，用while循环
            if (line.endsWith(".ts")) {
                stringList.add(line);
            }
        }
        br.close();

        //循环文件夹下的 ts 的文件 且合并
        FileOutputStream fos = new FileOutputStream(rootPath + File.separator + outFileName);

        for (String s : stringList) {
            File f = new File(rootPath + File.separator + s);

            if (f.isFile() && f.exists()) {
                IOUtils.copyLarge(new FileInputStream(f), fos);
            }
        }
        fos.close();
        return MUtils.clearDir(new File(rootPath), outFileName);
    }

    /**
     * 根据json 解析
     *
     * @param rootPath
     * @param outFileName
     * @return
     * @throws IOException
     */

    public static boolean mergeTs2Json(String rootPath, String outFileName) throws IOException {
        long startTime = System.currentTimeMillis();
        long endTime;
        String json = rootPath + File.separator + "json.txt";
        //合并后的文件名
        outFileName = outFileName + ".ts";
        FileOutputStream fos = new FileOutputStream(rootPath + File.separator + outFileName);
        File file = new File(json);
        Log.d(TAG, "file.exists():" + file.exists());
        String read = FileUtils.readFile2Text(file);
        Log.d(TAG, "读到的数据;" + read);
        try {
            M3U8 m3U8 = new Gson().fromJson(read, M3U8.class);
            for (M3U8TsInfo m3U8TsInfo : m3U8.getM3U8TsInfoList()) {
                Log.d(TAG, m3U8TsInfo.getMethod());
                if (m3U8TsInfo.getM3U8TsList().size() > 2) {
                        for (M3U8Ts m3U8Ts : m3U8TsInfo.getM3U8TsList()) {
                            String fileName = m3U8Ts.getUrl();
                            File f = new File(rootPath + File.separator + fileName);
                            if (m3U8TsInfo.getMethod().contains("AES")) {
                                deCodeTsFile(f, m3U8TsInfo.getKeyValue());
                            }
                            //合并文件
                            if (f.isFile() && f.exists()) {
                                IOUtils.copyLarge(new FileInputStream(f), fos);
                            }
                    }
                }
            }
            fos.close();
            endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            m3U8.setmerage(true);
            onmerge.onSuccess(m3U8);
            saveFile(new Gson().toJson(m3U8).getBytes(), json);
            Log.d(TAG, "合并完成,耗时:" + elapsedTime);
            return MUtils.clearDir(new File(rootPath), outFileName);

        } catch (Exception e) {

            e.printStackTrace();
            Log.d(TAG, "异常：" + e.getMessage());
        }
        return false;
    }

    /**
     * 还原加密视屏
     *
     * @param rootPath
     * @param outFileName
     * @return
     * @throws IOException
     */
    public static boolean decodeFile(String rootPath, String outFileName) throws IOException {

        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        String json = rootPath + File.separator + "json.txt";
        //合并后的文件名
        outFileName = outFileName + ".ts";


        File file = new File(json);
        Log.d(TAG, "file.exists():" + file.exists());

        String read = FileUtils.readFile2Text(file);
        Log.d(TAG, "读到的数据;" + read);
        try {
            M3U8 m3U8 = new Gson().fromJson(read, M3U8.class);
            for (M3U8TsInfo m3U8TsInfo : m3U8.getM3U8TsInfoList()) {
                Log.d(TAG, m3U8TsInfo.getMethod());
                if (m3U8TsInfo.getMethod().contains("AES")) {
                    for (M3U8Ts m3U8Ts : m3U8TsInfo.getM3U8TsList()) {
                        String fileName = m3U8Ts.getUrl();
                        File f = new File(rootPath + File.separator + fileName);
                        deCodeTsFile(f, m3U8TsInfo.getKeyValue());
                    }
                }
            }
            endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            Log.d(TAG, "解析,耗时:" + elapsedTime);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常：" + e.getMessage());
        }
        return false;
    }

    /**
     * 解密文件
     *
     * @param f
     * @param value
     */
    private static void deCodeTsFile(File f, String value) {
        InputStream inputStream1 = null;
        try {
            inputStream1 = new FileInputStream(f);
            int available = inputStream1.available();
            byte[] bytes = MUtils.readFile(f.getAbsolutePath());
            inputStream1.read(bytes);
            File f222 = new File(f.getAbsolutePath());

            OutputStream outputStream1 = new FileOutputStream(f222);
            //开始解密ts片段，这里我们把ts后缀改为了xyz，改不改都一样
            byte[] decrypt = AES128Utils.decrypt(bytes, available, value, "", "AES-128");
            if (decrypt == null) {
                outputStream1.write(bytes, 0, available);
            } else {
                outputStream1.write(decrypt);
            }
            outputStream1.close();
            inputStream1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取二次解析后的东西
     *
     * @param s
     * @return
     */
    private static String getM3u8ListIndex(String s) {
        String[] split = s.split("\n");

        for (String s1 : split) {
            if (s1.endsWith(".m3u8")) {
                return s1;
            }
        }
        return "";
    }
    /**
     * 获取ts解密的密钥，并把ts片段加入set集合
     * 版本 v1
     *
     * @param url     密钥链接，如果无密钥的m3u8，则此字段可为空
     * @param content 内容，如果有密钥，则此字段可以为空
     * @return ts是否需要解密，null为不解密
     */
    private static M3U8TsInfo getKey(String url, String content) {
        M3U8TsInfo m3U8TsInfo = new M3U8TsInfo();
        String key = "";
        String method = "";
        String iv = "";
        String[] split1 = content.split(",");
        for (String s1 : split1) {
            if (s1.contains("METHOD")) {
                method = s1.split("=", 2)[1];
                m3U8TsInfo.setMethod(method);
                continue;
            }
            if (s1.contains("URI")) {
                key = s1.split("=", 2)[1];
                m3U8TsInfo.setKeyUri(key);
                continue;
            }
            if (s1.contains("IV")) {
                iv = s1.split("=", 2)[1];
                m3U8TsInfo.setIV(iv);
            }
        }
        //将ts片段链接加入set集合
        if (!TextUtils.isEmpty(key)) {
            key = key.replace("\"", "");

            Log.d(TAG, "替换的key:" + key);
            (HttpUtils.isUrl(key) ? key : HttpUtils.mergeUrl(url, key)).replaceAll("\\s+", "");
            iv = getUrlContent(key).toString();
            m3U8TsInfo.setIV(iv.trim());
            m3U8TsInfo.setKeyValue(iv.trim());
        }
        return m3U8TsInfo;
    }
}