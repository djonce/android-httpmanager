package tech.wangjie.httpmanager;

import android.content.Context;

import java.util.Random;

/**
 * Created by wangjie on 2016/10/31 0031
 */

public class HttpConfig {

    private Context appContext;

    public HttpConfig() {
    }

    public HttpConfig(Context appContext) {
        this.appContext = appContext;
    }

    public String getFileFolder() {
        return appContext.getCacheDir().getPath();
    }

    public String getRandName() {

        return "test---" + new Random(100000).toString();
    }
}
