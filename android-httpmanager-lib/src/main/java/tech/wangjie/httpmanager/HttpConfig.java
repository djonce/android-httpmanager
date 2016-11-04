package tech.wangjie.httpmanager;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static tech.wangjie.httpmanager.HttpManager.TAG;

/**
 * Created by wangjie on 2016/10/31 0031
 */

public class HttpConfig {

    private static long DEFAULT_CONNECT_TIME_OUT = 15000;

    private boolean debugged;

    private long connectTimeout;
    private long readTimeOut;

    private String cacheDir;

    public HttpConfig(Context context) {
        setDefaultCacheDir(getDefaultCacheDir(context));
    }

    private HttpConfig setDefaultCacheDir(String defaultCacheDir) {
        this.cacheDir = defaultCacheDir;
        // 校验目录
        File cacheDir = new File(defaultCacheDir);
        if (!cacheDir.exists()) {
            boolean mkdirs = cacheDir.mkdirs();
            Log.d(TAG, cacheDir.getAbsolutePath() + "  mkdirs:" + mkdirs);
        }
        Log.d(TAG, "HttpConfig http cache file dir :" + defaultCacheDir);
        return this;
    }

    private String getDefaultCacheDir(Context context) {
        if (context != null) {
            context = context.getApplicationContext();
            return context.getFilesDir() + "/http-cache";
        }else {
            return Environment.getExternalStorageDirectory() + "/http-cache";
        }
    }

    public String getCacheDir() {
        if (cacheDir == null)
            cacheDir = getDefaultCacheDir(null);
        return cacheDir;
    }

    public boolean isDebugged() {
        return debugged;
    }

    public HttpConfig setDebugged(boolean debugged) {
        this.debugged = debugged;
        return this;
    }

    public HttpConfig setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public long getConnectTimeOut() {
        if (connectTimeout == 0) {
            connectTimeout = DEFAULT_CONNECT_TIME_OUT;
        }
        return connectTimeout;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }
}
