package jaygoo.library.m3u8downloader;
import jaygoo.library.m3u8downloader.bean.M3U8;
public interface OnTaskMergeListener extends BaseListener{
    /**
     * 合并成功
     */
    void onSuccess(M3U8 m3U8);
    /**
     * 开始的时候回调
     */
    void onProgress(M3U8 m3U8);
}
