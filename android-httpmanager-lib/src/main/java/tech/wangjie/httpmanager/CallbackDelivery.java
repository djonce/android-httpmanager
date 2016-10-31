package tech.wangjie.httpmanager;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 消息分发器， 主要是将消息转发到主线程
 * <p>
 * Created by wangjie on 2016/10/28
 */

public class CallbackDelivery {

    private static final CallbackDelivery deliver = createDelivery();

    public static CallbackDelivery get() {
        return deliver;
    }

    private static synchronized CallbackDelivery createDelivery() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                if (deliver != null) {
                    return deliver;
                }
                return new Android();
            }
        } catch (ClassNotFoundException ignored) {

        }

        return new CallbackDelivery();
    }

    public void execute(Runnable runnable) {
        getDefaultCallbackExecutor().execute(runnable);
    }

    public Executor getDefaultCallbackExecutor() {
        return Executors.newCachedThreadPool();
    }

    static class Android extends CallbackDelivery {
        static class MainThreadExecutor implements Executor {

            private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable cmd) {
                mainThreadHandler.post(cmd);
            }
        }

        @Override
        public Executor getDefaultCallbackExecutor() {
            return new MainThreadExecutor();
        }
    }

}
