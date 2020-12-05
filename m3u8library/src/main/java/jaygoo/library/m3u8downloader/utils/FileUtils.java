package jaygoo.library.m3u8downloader.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileUtils {

    /**
     * 从文件读取文本
     *
     * @param file
     * @return
     */
    public static String readFile2Text(File file) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), Charset.defaultCharset());//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                stringBuilder.append(lineTxt);
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
