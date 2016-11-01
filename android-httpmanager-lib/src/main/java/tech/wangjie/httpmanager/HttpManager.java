package tech.wangjie.httpmanager;


import okhttp3.Call;

/**
 * 网络管理器
 *
 * Created by wangjie on 2016/10/27
 */
public abstract class HttpManager {

    public static final String TAG = HttpManager.class.getSimpleName();

    private static HttpManager httpManager;

    public static HttpManager getInstance() {
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null) {
                    httpManager = new OkHttpManagerImpl();
                }
            }
        }
        return httpManager;
    }

    public abstract void enqueue(HttpRequest request, HttpListener callback);

    public abstract Call newRealCall(HttpRequest request);

    public abstract HttpConfig getDefaultConfig();
}
