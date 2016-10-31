package tech.wangjie.httpmanager;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 *
 * Created by wangj on 2016/10/29.
 */
public abstract class JsonHttpListener<T> extends HttpListener<T> {

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String data = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return new Gson().fromJson(data, entityClass);
    }
}
