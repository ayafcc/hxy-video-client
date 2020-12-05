package cn.mahua.vod.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import cn.mahua.vod.App;
import cn.mahua.vod.R;


public class SimpleUtils {
    public static File saveBitmapToSdCard(Context context, Bitmap mybitmap) {
        //创建位图保存目录
        String path = context.getExternalCacheDir().toString();
        String name = TimeUtils.getNowString();
        File file = new File(path +"/"+name + ".png");
        FileOutputStream fileOutputStream = null;
        if (!file.exists()) {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                fileOutputStream = new FileOutputStream(file);
                mybitmap.compress(Bitmap.CompressFormat.PNG, 50, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                //update gallery
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                context.sendBroadcast(intent);

                return file;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 手动测量摆放View
     * 对于手动 inflate 或者其他方式代码生成加载的View进行测量，避免该View无尺寸
     *
     * @param v
     * @param width
     * @param height
     */
    public static void layoutView(View v, int width, int height) {
        // validate view.width and view.height
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        // validate view.measurewidth and view.measureheight
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取一个 View 的缓存视图
     * (前提是这个View已经渲染完成显示在页面上)
     *
     * @param view
     * @return
     */
    public static Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 对ScrollView进行截图
     *
     * @param scrollView
     * @return
     */
    public static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 对ListView进行截图
     * http://stackoverflow.com/questions/12742343/android-get-screenshot-of-all-listview-items
     */
    public static Bitmap shotListView(ListView listview) {
        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<Bitmap>();
        for (int i = 0; i < itemscount; i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(
                    View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();
            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
        }
        Bitmap bigbitmap =
                Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);
        Paint paint = new Paint();
        int iHeight = 0;
        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();
            bmp.recycle();
            bmp = null;
        }
        return bigbitmap;
    }

    /**
     * 对RecyclerView进行截图
     * https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
     */
    public static Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }
            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }
            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }

    private File mkdirsReturn(File file) {
        if (!file.exists()) {
            file.mkdirs();
            return file;
        } else {
            return file;
        }
    }

    public File findImgFile() {
        File imgFile = new File(findIOCFile().getAbsolutePath() + File.separator + "download");
        return mkdirsReturn(imgFile);
    }

    public File findIOCFile() {
        File iekFile = new File(findDataFile().getAbsolutePath() + File.separator + "com.ioc.swapc");
        return mkdirsReturn(iekFile);
    }

    private File findDataFile() {
        File dataFile = new File(findAndroidFile().getAbsolutePath() + File.separator + "data");
        return mkdirsReturn(dataFile);
    }

    public File findAndroidFile() {
        File memoryPath = listAvailableStorage();
        File androidFile;
        if (memoryPath != null) {
            androidFile = new File(memoryPath.getAbsolutePath() + File.separator + "Android");
        } else {
            androidFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "Android");
        }
        return mkdirsReturn(androidFile);
    }

    /**
     * 获取file路径
     *
     * @return
     */
    private File listAvailableStorage() {
        StorageManager storageManager = (StorageManager) App.getApplication().getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod(
                    "getVolumeList", paramClasses);
            getVolumeList.setAccessible(true);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager,
                    params);
            if (invokes != null) {
                for (Object obj : invokes) {
                    Method getPath = obj.getClass().getMethod("getPath"
                    );
                    String path = (String) getPath.invoke(obj);
                    StorageInfo info = new StorageInfo(path);
                    File file = new File(path);
                    if ((file.exists()) && (file.isDirectory())
                            && (file.canWrite())) {
                        Method isRemovable = obj.getClass().getMethod(
                                "isRemovable");
                        String state;
                        try {
                            Method getVolumeState = StorageManager.class
                                    .getMethod("getVolumeState", String.class);
                            state = (String) getVolumeState.invoke(
                                    storageManager, path);
                            info.state = state;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (info.isMounted()) {
                            info.isRemovable = (Boolean) isRemovable.invoke(
                                    obj);
                            if (!info.isRemovable) {
                                return new File(path);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Intent getInstallAppIntent(final File file, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            String authority = App.getApplication().getPackageName() + ".provider";
            data = FileProvider.getUriForFile(App.getApplication(), authority, file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        App.getApplication().grantUriPermission(App.getApplication().getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(data, type);
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    private class StorageInfo {
        public String path;
        public String state;
        public boolean isRemovable;

        public StorageInfo(String path) {
            this.path = path;
        }

        public boolean isMounted() {
            return "mounted".equals(state);
        }
    }

    /**
     * 获取图片的ImageOptions 信息
     * @return
     */
    public static void setImageLoading(ImageView imageView, String url){
        ImageOptions.Builder impBuilder =  new ImageOptions.Builder();
        impBuilder.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        impBuilder.setUseMemCache(true);//设置使用缓存
        impBuilder.setIgnoreGif(false);//设置支持gif
        impBuilder.setCircular(false);//设置显示圆形图片
        impBuilder.setSquare(false);
        impBuilder.setFailureDrawableId(R.mipmap.ic_launcher);
        impBuilder.setLoadingDrawableId(R.mipmap.ic_launcher);
        ImageOptions build = impBuilder.build();
        x.image().bind(imageView, url, build);

//                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
//                .setRadius(DensityUtil.dip2px(5))
        // 如果ImageView的大小不是定义为wrap_content, 不要crop.
//                .setCrop(true)
        // 加载中或错误图片的ScaleType
        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
//                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
        //设置加载过程中的图片
//                .setLoadingDrawableId(R.drawable.)
        //设置加载失败后的图片
//                .setFailureDrawableId(R.drawable.ic_launcher)
        //设置使用缓存
//                .setUseMemCache(true)
        //设置支持gif
//                .setIgnoreGif(false)
        //设置显示圆形图片
//                .setCircular(false)
//                .setSquare(true)
//                .build();

    }

    /**
     * 获取图片的ImageOptions 信息
     * @return
     */
    public static void setImageLoading(Context context,ImageView imageView, String url, int loadDrawable){
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .load(url)
                .placeholder(loadDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    //判断是否使用了代理
    public static boolean isWifiProxy(Context context) {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

}
