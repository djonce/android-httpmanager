package tech.wangjie.httpmanager.utils;

import android.util.Log;

/**
 * Created by wangjie on 2016/10/31 0031
 */

public class ClassUtils {

    /**
     * 获取参数上的 uri路径
     *
     * @param object 目标参数类
     * @return
     */
    public static String getHttpUri(Object object) {
        Class cla = object.getClass();

        if (cla.isAnnotationPresent(HttpUri.class)) {
            HttpUri httpUri = (HttpUri) cla.getAnnotation(HttpUri.class);
            String value = httpUri.value();
            Log.d(ClassUtils.class.getSimpleName(), "HttpUri :" + value);
            return value;
        }
        return null;
    }

    /**
     * 获取参数上的 请求方法
     *
     * @param object 目标参数类
     * @return
     */
    public static HttpMethods getHttpMethod(Object object) {
        Class cla = object.getClass();

        if (cla.isAnnotationPresent(HttpMethod.class)) {
            HttpMethod httpMethod = (HttpMethod) cla.getAnnotation(HttpMethod.class);
            Log.d(ClassUtils.class.getSimpleName(), "HttpMethod :" + httpMethod.value());
            return httpMethod.value();
        }
        return null;
    }

    /**
     *  获取参数上的 Host
     *
     * @param object 目标参数类
     * @return
     */
    public static String getHttpBaseUrl(Object object) {
        Class cla = object.getClass();

        if (cla.isAnnotationPresent(HttpBaseURL.class)) {
            HttpBaseURL httpBaseURL = (HttpBaseURL) cla.getAnnotation(HttpBaseURL.class);
            String value = httpBaseURL.value();
            Log.d(ClassUtils.class.getSimpleName(), "HttpBaseURL :" + value);
            return value;
        }
        return null;
    }
}
