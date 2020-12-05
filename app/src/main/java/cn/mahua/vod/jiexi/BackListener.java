package cn.mahua.vod.jiexi;

import java.util.Map;

public interface BackListener {

    void onSuccess(String url, int curParseIndex, Map<String, String> headers);

    void onError();

    void onProgressUpdate(String msg);
}
