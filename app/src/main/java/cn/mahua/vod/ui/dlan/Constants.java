package cn.mahua.vod.ui.dlan;

/**
 */
public class Constants
{
    private static final String CAST_URL_LOCAL_TEST =
            "http://172.16.0.107:8506/clear/teststage/t594_hd_apptv.m3u8";
    private static final String CAST_URL_CNTV_TEST =
            "http://stdev.nlv2.com/live/cntv/2013/cctv5_ipad.m3u8?auth_key=3af822d1cb3011bcd2f3b6d9a11396df-1532406130-32-*.m3u8;*.ts";
    private static final String CAST_URL_IPHONE_SAMPLE =
            "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
    private static final String CAST_URL_CNTV_SAMPLE =
            "http://stitchcast.nlv2.com/live/cntv/2019/game22810_sd_cast.m3u8?auth_key=e470ece244ae24a4ec07087e18e5be32-1552621004-32-*";
    private static final String CAST_URL_CNTV_SAMPLE_VOD =
            "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private static final int _30_MIN = 30 * 60 * 1000;

    public static final String CAST_URL = CAST_URL_CNTV_SAMPLE_VOD;
    public static final String CAST_ID = "101";
    public static final String CAST_NAME = "castDemo";
    public static final int CAST_VIDEO_DURATION = _30_MIN; //TODO: check get cast video duration.
}
