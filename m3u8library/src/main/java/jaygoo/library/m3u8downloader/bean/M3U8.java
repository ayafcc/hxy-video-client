package jaygoo.library.m3u8downloader.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jaygoo.library.m3u8downloader.utils.MUtils;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/11/20
 * 描    述: m3u8实体类
 * ================================================
 */
public class M3U8 {


    private String fileName;
    private boolean merage = false;
    private String basePath;//去除后缀文件名的url
    private String m3u8FilePath;//m3u8索引文件路径
    private String dirFilePath;//切片文件目录
    private long fileSize;//切片文件总大小
    private long totalTime;//总时间，单位毫秒
    private List<M3U8TsInfo> m3U8TsInfoList = new ArrayList<>();
//    private List<M3U8Ts> tsList = new ArrayList<M3U8Ts>();//视频切片

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
    public boolean getmerage() {
        return merage;
    }
    public void setmerage(boolean basePath) {
        this.merage = basePath;
    }
    public String getM3u8FilePath() {
        return m3u8FilePath;
    }

    public void setM3u8FilePath(String m3u8FilePath) {
        this.m3u8FilePath = m3u8FilePath;
    }

    public String getDirFilePath() {
        return dirFilePath;
    }

    public void setDirFilePath(String dirFilePath) {
        this.dirFilePath = dirFilePath;
    }

    public long getFileSize() {
        fileSize = 0;
        for (M3U8TsInfo m3U8TsInfo : m3U8TsInfoList) {
            for (M3U8Ts m3U8Ts : m3U8TsInfo.getM3U8TsList()) {
                fileSize = fileSize + m3U8Ts.getFileSize();
            }
        }

//        for (M3U8Ts m3U8Ts : tsList){
//        //            fileSize = fileSize + stringListEntry.getFileSize();
//        }
        return fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFormatFileSize() {
        fileSize = getFileSize();
        if (fileSize == 0) return "";
        return MUtils.formatFileSize(fileSize);
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

//    public List<M3U8Ts> getTsList() {
//        return tsList;
//    }
//
//    public void setTsList(List<M3U8Ts> tsList) {
//        this.tsList = tsList;
//    }
//
//    public void addTs(M3U8Ts ts) {
//        this.tsList.add(ts);
//    }

    public long getTotalTime() {
        totalTime = 0;
        for (M3U8TsInfo m3U8TsInfo : m3U8TsInfoList) {
            for (M3U8Ts m3U8Ts : m3U8TsInfo.getM3U8TsList()) {
                fileSize = fileSize + m3U8Ts.getFileSize();
            }
        }

//        for (M3U8Ts m3U8Ts : tsList){
//            fileSize = fileSize + m3U8Ts.getFileSize();
//        }
        return totalTime;
    }

    @Override
    public String toString() {
        return "M3U8{" +
                "fileName='" + fileName + '\'' +
                ", basePath='" + basePath + '\'' +
                ", m3u8FilePath='" + m3u8FilePath + '\'' +
                ", dirFilePath='" + dirFilePath + '\'' +
                ", fileSize=" + fileSize +
                ", totalTime=" + totalTime +
                ", m3U8TsInfoList=" + m3U8TsInfoList +
                ", merage=" + merage +
                '}';
    }

    public List<M3U8TsInfo> getM3U8TsInfoList() {
        return m3U8TsInfoList;
    }

    public void setM3U8TsInfoList(List<M3U8TsInfo> m3U8TsInfoList) {
        this.m3U8TsInfoList = m3U8TsInfoList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof M3U8) {
            M3U8 m3U8 = (M3U8) obj;
            if (basePath != null && basePath.equals(m3U8.basePath)) return true;
        }
        return false;
    }
}
