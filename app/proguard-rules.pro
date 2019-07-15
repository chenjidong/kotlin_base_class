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

# 指定代码的压缩级别 max 7
-optimizationpasses 7

# 混淆时不会产生形形色色的类名
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

# 混淆时是否做预校验
-dontpreverify

# 混淆时是否记录日志
-verbose

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#过滤泛型
-keepattributes Signature

#不优化输入的类文件
-dontoptimize

#指定不去忽略包可见的库类的成员
-dontskipnonpubliclibraryclassmembers
#不输出通知
-dontnote
#-ignorewarnings # 忽略警告，避免打包时某些警告出现

#-libraryjars libs/**.jar


# 官方 4大主件 及jar包
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class com.android.vending.licensing.ILicensingService

#------------------  下方是android平台自带的排除项         ----------------

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class * implements java.io.Serializable {
  *;
}

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}

##--------------------------第三方 library 不混淆 start -----------------------------------------
#gson解析不被混淆
-dontwarn com.google.**
-keep class com.google.**{*;}
-keep class com.google.protobuf.** {*;}

#阿帕奇 第三方库
-dontwarn org.apache.**
-keep class org.apache.**{*;}

-dontwarn internal.org.apache.**
-keep class internal.org.apache.**{*;}

#J2SE 禁止发出警告
-dontwarn javax.swing.**
-dontwarn java.awt.**

#base 开源库
-dontwarn com.cjd.base.opensource.**
-keep class com.cjd.base.opensource.** {*;}

#okhttp3
-dontwarn okio.**
-keep class okio.** { *; }

-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

#retrofit 请求框架

-keep class com.jingya.videoplayer.api.**{*;}
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

#fresco
-keep class com.facebook.**{*;}
-dontwarn com.facebook.**

#glide
-keep class com.bumptech.**{*;}
-dontwarn com.bumptech.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Rx RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}


# BRVAH
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}


# EventBus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# GreenDao Proguard
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# Rx:
-dontwarn rx.**


#com.squareup
-keep class com.squareup.**{*;}
-dontwarn com.squareup.**

-keep class !android.support.v7.internal.view.menu.**,android.support.** {*;}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#umeng
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class jingya.com.controlcenter.R$*{
public static final int *;
}

#阿里 sdk
-keep class com.alipay.** { *; }
-dontwarn com.alipay.**

#腾讯sdk
-keep class com.tencent.** { *; }
-dontwarn com.tencent.**

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#阿里
-keep class com.alibaba.sdk.android.oss.** { *; }

# Tencent GDT ad
-keep class com.androidquery.**{*;}
-keep class com.qq.e.**{*;}
# Baidu ad
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.baidu.mobads.*.** { *; }

# 推啊
-dontwarn com.check.ox.sdk.**
-keep class com.check.ox.sdk.** { *; }
#TONGDUN
-dontwarn android.os.**
-dontwarn com.android.internal.**
-keep class cn.tongdun.android.**{*;}

#MPAndroidChart
-dontwarn com.github.mikephil.charting.**
-keep class com.github.mikephil.charting.**{*;}

##--------------------------第三方 library 不混淆 end -----------------------------------------

##--------------------------自定义业务 部混淆start------------------------------------------
-keep class com.cjd.kotlin.ui.**.bean.**{*;}
-keep class com.cjd.kotlin.ui.**.adapter.**{*;}
-keep class com.cjd.kotlin.ui.**.event.**{*;}
##--------------------------自定义业务 部混淆end--------------------------------------------
