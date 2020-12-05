package jaygoo.library.m3u8downloader.bean;

import java.util.List;

public class M3U8TsInfo {

 private   String keyValue;
 private   String method;
 private   String keyUri;
 private   String IV;
 private   List<M3U8Ts> m3U8TsList;

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKeyUri() {
        return keyUri;
    }

    public void setKeyUri(String keyUri) {
        this.keyUri = keyUri;
    }

    public List<M3U8Ts> getM3U8TsList() {
        return m3U8TsList;
    }

    public void setM3U8TsList(List<M3U8Ts> m3U8TsList) {
        this.m3U8TsList = m3U8TsList;
    }

    public String getIV() {
        return IV;
    }

    public void setIV(String IV) {
        this.IV = IV;
    }

    @Override
    public String toString() {
        return "M3U8TsInfo{" +
                "keyValue='" + keyValue + '\'' +
                ", method='" + method + '\'' +
                ", keyUri='" + keyUri + '\'' +
                ", IV='" + IV + '\'' +
                ", m3U8TsList=" + m3U8TsList +
                '}';
    }
}
