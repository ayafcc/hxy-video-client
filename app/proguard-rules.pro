# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#1.基本指令区
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

#2.默认保留区
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}


###########################################################################
-keep class com.whiner.vod.bean.** { *; }
-keep class com.whiner.vod.entity.** { *; }
-keep class com.whiner.vod.ad.** { *; }

##############################   第三方   ##################################

-keep class com.tencent.** {*;}

## v4
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }

## v7
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

## design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

## recyclerview
-keep class androidx.recyclerview.** {*;}
#open_ad_sdk
	-keep class com.bytedance.sdk.openadsdk.** { *; }
	-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
	-keep class com.ss.**{*;}
	-dontwarn com.ss.**
	# miitmdid
	-keep class com.bun.miitmdid.core.** {*;}
	-dontwarn com.bun.miitmdid.core.**

	#广点通 start
	-keep class com.qq.e.** {*;}
	-dontwarn com.qq.e.**
	#广点通 end

	#快手
	-keep class org.chromium.** {*;}
	-keep class org.chromium.** { *; }
	-keep class aegon.chrome.** { *; }
	-keep class com.kwai.**{ *; }
	-dontwarn com.kwai.**
	-dontwarn com.kwad.**
	-dontwarn com.ksad.**
	-dontwarn aegon.chrome.**
	#快手

	# WindAd
	-keep class com.sigmob.**{*;}
	-dontwarn com.sigmob.**
	# WindAd


#baidu start
-ignorewarnings
-dontwarn com.baidu.mobads.sdk.api.**
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.baidu.mobads.** { *; }
-keep class com.style.widget.** {*;}
-keep class com.component.** {*;}
-keep class com.baidu.ad.magic.flute.** {*;}
-keep class com.baidu.mobstat.forbes.** {*;}
#baidu end

#oaid start
-keep class com.asus.msa.**{*;}
-keep class com.bun.**{*;}
-keep class com.huawei.hms.ads.identifier.**{*;}
-keep class com.netease.nis.sdkwrapper.**{*;}
-keep class com.samsung.android.deviceidservice.**{*;}
-keep class com.zui.**{*;}
-keep class XI.**{*;}
#oaid end
#openset start
-keep class com.kc.openset.**{*;}
-dontwarn com.kc.openset.**
#openset end

## Butterknife
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

## fragmentationx
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends androidx.appcompat.app.AppCompatActivity
-keep class me.yokeyword.** {*;}
-keep class androidx.fragment.app.** {*;}
-keep class me.yokeyword.fragmentation.** {*;}
-keep class me.yokeyword.eventbusactivityscope.** {*;}

## EventBUs
-keep class org.greenrobot.eventbus.** {*;}

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

## retrofit
-keep class retrofit2.** {*;}
#-keepattributes Signature, InnerClasses, EnclosingMethod
#-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
#-keepclassmembers,allowshrinking,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}


-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

## okhttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

## gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.sweetieplayer.vod.bean.** { <fields>; }
-keep class com.sweetieplayer.vod.entity.** { <fields>; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

## Glide
#-keep public class * implements com.bumptech.glide.module.AppGlideModule
#-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
#-keep class com.bumptech.glide.** { *; }
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#    **[] $VALUES;
#    public *;
#}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

## banner
-keep class com.youth.banner.** {
    *;
 }

#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

## litepal
-keep class org.litepal.** {
    *;
}

-keep class * extends org.litepal.crud.LitePalSupport {
    *;
}

## xUtils
-keepattributes Signature,*Annotation*
-keep public class org.xutils.** {
    public protected *;
}
-keep public interface org.xutils.** {
    public protected *;
}
-keepclassmembers class * extends org.xutils.** {
    public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {*;}
-keepclassmembers @org.xutils.http.annotation.* class * {*;}
-keepclassmembers class * {
    @org.xutils.view.annotation.Event <methods>;
}

## smartRefresh
-keep class com.github.mmin18.** {*;}

-dontwarn android.support.v8.renderscript.*

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

## xxpermissions
-keep class com.hjq.permissions.** {*;}
## toast
-keep class com.hjq.toast.** {*;}

## danmaku.ijk
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**

##com.dueeeke.ijk
-keep class com.dueeeke.videoplayer.** { *; }
-dontwarn com.dueeeke.videoplayer.**

## reactivestreams
-keep class org.reactivestreams.** {*;}

##hamcrest
-keep class org.hamcrest.** {*;}

##me.drakeet.multitype
-keep class me.drakeet.multitype.** {*;}

##gdut.bsx.share2
-keep class gdut.bsx.share2.** {*;}

##com.shehuan.niv
-keep class com.shehuan.niv.** {*;}

##com.hedgehog.ratingbar
-keep class com.hedgehog.ratingbar.** {*;}

##net.lucode.hackware.magicindicator
-keep class net.lucode.hackware.magicindicator.** {*;}

## aria
-keep class com.arialyy.aria.** {*;}
-keep class com.arialyy.annotations.** {*;}
-keep class com.arialyy.aria.http.** {*;}
-keep class com.arialyy.aria.** {*;}

## facebook
-keep class com.facebook.** {*;}

## utilcode
-keep class com.blankj.utilcode.** {*;}

## BaseRecyclerAdapter
-keep class com.chad.library.** {*;}

##master.flame.danmaku
-keep class master.flame.danmaku.** {*;}

## MarqueeTextView
-keep class com.xiaweizi.marquee.** {*;}

## com.orhanobut:hawk
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep,allowobfuscation interface com.facebook.crypto.proguard.annotations.DoNotStrip
-keep,allowobfuscation interface com.facebook.crypto.proguard.annotations.KeepGettersAndSetters
-keep @com.facebook.crypto.proguard.annotations.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.crypto.proguard.annotations.DoNotStrip *;
}
-keepclassmembers @com.facebook.crypto.proguard.annotations.KeepGettersAndSetters class * {
  void set*(***);
  *** get*();
}


## fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

###plist
-keep class com.dd.plist.** { *; }
-dontwarn com.dd.plist.**

###kxml
-keep class org.kxml2.** { *; }
-keep class org.xmlpull.** { *; }
-dontwarn org.kxml2.**
-dontwarn org.xmlpull.**


###########################以下是AndroidStudio自带的混淆配置协议###############################

#忽略警告
-ignorewarnings
##保证是独立的jar,没有任何项目引用,如果不写就会认为我们所有的代码是无用的,从而把所有的代码压缩掉,导出一个空的jar
-dontshrink
###泛型
-keepattributes Signature

#log
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# 表示混淆时不使用大小写混合类名
-dontusemixedcaseclassnames

# 表示不跳过library中的非public的类
-dontskipnonpubliclibraryclasses

# 打印混淆的详细信息
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 表示不进行校验,这个校验作用 在java平台上的
-dontpreverify

#使用注解需要添加
-keepattributes *Annotation*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
#指定不混淆所有的JNI方法
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
#所有View的子类及其子类的get、set方法都不进行混淆
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
# 不混淆Activity中参数类型为View的所有方法
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
# 不混淆Enum类型的指定方法
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 不混淆Parcelable和它的子类，还有Creator成员变量
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

# 不混淆R类里及其所有内部static类中的所有static变量字段
-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
# 不提示兼容库的错误警告
-dontwarn android.support.**

-dontwarn com.markupartist.**

-keepclasseswithmembers class *{
      <init>(android.content.Context);
}

#####################################  乐播   ###########################################
## 海信
-keep class com.hisense.**{*;}
-dontwarn com.hisense.**
###Picasso
-keep class com.squareup.picasso.** { *; }
-dontwarn com.squareup.picasso.**
###danmu
-keep class master.flame.** { *; }
-dontwarn master.flame.**
###ijk
-keep class lebotv.** { *; }
-dontwarn lebotv.**
#######  乐播
-dontwarn com.hpplay.**
-keep class com.hpplay.**{*;}
-keep class com.hpplay.**$*{*;}
###### bang
-keep class com.bangcle.**{*;}


-keep class master.flame.danmaku.**{*;}
-keep class tv.cjump.jni.**{*;}
-keep class com.dpuntu.downloader.**{*;}
-keep class com.sweetieplayer.av.**{*;}
-keep class com.sweetieplayer.vod.ui.widget.**{*;}

# sdk加固
-keep @com.qihoo.SdkProtected.OSETSDK.Keep class **{*;}
-keep,allowobfuscation @interface com.qihoo.SdkProtected.OSETSDK.Keep