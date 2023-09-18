package com.sweetieplayer.vod.utils;

import android.content.Context;
import android.content.Intent;

import com.sweetieplayer.vod.BuildConfig;
import org.jetbrains.annotations.NotNull;

import com.sweetieplayer.vod.download.SPUtils;
import com.sweetieplayer.vod.ui.pay.PayActivity;
import com.sweetieplayer.vod.ui.widget.HitDialog;

public class LoginUtils {


    private boolean checkDebug(Context context) {
        return (BuildConfig.DEBUG);
    }


    public static  boolean checkLogin(Context context) {
//        if(checkDebug(context)){
//            return true;
//              //客服QQ“36711293  有偿解决一切问题
//        }
//        if (UserUtils.isLogin()) {

            return true;
        /*if (UserUtils.isLogin()) {
        } else {
//客服QQ“36711293  有偿解决一切问题
            ActivityUtils.startActivity(LoginActivity.class);
            return false;
        }
*/

    }

    /**
     * 检查是不是vip
     *
     * @param context
     * @return
     */
    public static boolean checkVIP(Context context,String msg) {
        if (SPUtils.getBoolean(context, "isVip")) {

            return true;
        } else {
            new HitDialog(context).setTitle("提示").setMessage(msg).setOnHitDialogClickListener(new HitDialog.OnHitDialogClickListener() {
                @Override
                public void onCancelClick(@NotNull HitDialog dialog) {
                    super.onCancelClick(dialog);
                }

                @Override
                public void onOkClick(@NotNull HitDialog dialog) {
                    super.onOkClick(dialog);
                    context.startActivity(new Intent(context, PayActivity.class));
                }
            }).show();
            return false;
        }
    }
}
