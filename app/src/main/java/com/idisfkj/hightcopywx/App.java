package com.idisfkj.hightcopywx;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by idisfkj on 16/4/21.
 * Email : idisfkj@qq.com.
 */
public class App extends Application{
    private static Context mContext;
    public static final String APP_ID = "2882303761517464903";
    public static final String APP_KEY = "5631746467903";
    public static final String APP_SECRET_KEY = "HxMA7STSUQMLEiDX+zo+5A==";
    public static final String TAG = "com.idisfkj.hightcopywx";
    public static final String DEVELOPER_ID = "/f6ukNwIPpdSmkrgsmklcMbW6WefG01XkxdILDNEUVw=";
    public static final String DEVELOPER_NUMBER = "1";
    public static final String DEVELOPER_NAME = "我的消息";
    public static final String DEVELOPER_MESSAGE = "欢迎注册微修APP，你可以使用添加朋友自行互动！";
    public static final String HELLO_MESSAGE = "你已添加了%s，现在可以开始聊天了";
    public static final String UNREADNUM = "unReadNum";
    public static String mNumber = "-1";
    public static String mRegId = "-1";
    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        sp = getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }

        //小米推送调试日记
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
        initUmeng();
        initImageLoader();
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    public void initUmeng(){
        UMConfigure.init(this, "5b4ffce98f4a9d337e000115", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "033328127187c1c55c729fdc57fc44c7");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("my_token=","my_token="+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
            }
        });
    }

    public static Context getAppContext(){
        return mContext;
    }
    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }
}
