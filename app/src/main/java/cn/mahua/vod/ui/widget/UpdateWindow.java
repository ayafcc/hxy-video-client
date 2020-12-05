package cn.mahua.vod.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import cn.mahua.vod.App;
import cn.mahua.vod.R;
import cn.mahua.vod.entity.UpdateEntity;
import cn.mahua.vod.utils.SimpleUtils;

/**
 * Created by YangXun
 * on 2019/10/14
 * on class describe
 * 更新
 */
public class UpdateWindow {
    private Dialog authorityDialog;
    private View view;
    ProgressBar download_pb;
    TextView tv_content;
    TextView tv_download;
    public Dialog createAuthorityDialog(final Activity activity,  final UpdateEntity updateInfo ) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        view = inflater.inflate(R.layout.update_window, null);

        tv_content =  view.findViewById(R.id.tv_content);
        download_pb =  view.findViewById(R.id.download_pb);
        tv_download = view.findViewById(R.id.tv_download);
        tv_content.setText(updateInfo.getAppRemark());
        TextView tv_cancel =  view.findViewById(R.id.tv_cancel);
        if (updateInfo.getIsUpdate() == 2) {
            tv_cancel.setVisibility(View.VISIBLE);
        }

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorityDialogDialog();
            }
        });

        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tv_content.setVisibility(View.GONE);
                    download_pb.setVisibility(View.VISIBLE);
                            RequestParams requestParams = new RequestParams(updateInfo.getAppUrl());
                    // 为RequestParams设置文件下载后的保存路径
                    requestParams.setSaveFilePath(new SimpleUtils().findImgFile().getAbsolutePath()+ File.separator +"ioc.apk");
                    // 下载完成后自动为文件命名
                    requestParams.setAutoRename(false);
                    x.http().get(requestParams, new Callback.ProgressCallback<File>() {

                        @Override
                        public void onSuccess(File result) {
                            activity.startActivityForResult( SimpleUtils.getInstallAppIntent(result,false), 1000);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            authorityDialogDialog();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            authorityDialogDialog();
                        }

                        @Override
                        public void onFinished() {
                            authorityDialogDialog();
                        }

                        @Override
                        public void onWaiting() {
                            tv_download.setText("下载中");
                            tv_download.setOnClickListener(null);
                            // 网络请求开始的时候调用
                        }

                        @Override
                        public void onStarted() {
                            // 下载的时候不断回调的方法
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isDownloading) {
                            // 当前的下载进度和文件总大小
                            download_pb.setMax((int)total);
                            download_pb.setProgress((int)current);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        authorityDialog = new Dialog(activity, R.style.MyDialogStyle);
        authorityDialog.setCancelable(true);
        authorityDialog.setCanceledOnTouchOutside(false);
        authorityDialog.setContentView(view, new LinearLayout.LayoutParams(
                App.getSrceenWidth() - 100,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = authorityDialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = App.getSrceenWidth() - 100;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);

        return authorityDialog;
    }


    public void authorityDialogShow() {
        authorityDialog.show();
    }

    public void authorityNoBack() {
        authorityDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
            }
        });
    }

    private void authorityDialogDialog() {
        if (authorityDialog != null) {
            authorityDialog.dismiss();
            authorityDialog = null;
        }
    }
}
