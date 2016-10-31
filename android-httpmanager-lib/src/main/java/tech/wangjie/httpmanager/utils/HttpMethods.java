package tech.wangjie.httpmanager.utils;

/**
 * Created by wangjie on 2016/10/31 0031
 */

public enum  HttpMethods {

    GET("GET"),

    HEAD("HEAD"),

    DELETE("DELETE"),

    POST("POST"),

    PATCH("PATCH");

    private String methodName;
    HttpMethods(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
