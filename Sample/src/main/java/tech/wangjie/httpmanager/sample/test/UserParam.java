package tech.wangjie.httpmanager.sample.test;

import tech.wangjie.httpmanager.utils.HttpBaseURL;
import tech.wangjie.httpmanager.utils.HttpMethod;
import tech.wangjie.httpmanager.utils.HttpMethods;
import tech.wangjie.httpmanager.utils.HttpUri;

/**
 * Created by wangjie on 2016/10/31 0031
 */

@HttpUri("login")
@HttpMethod(HttpMethods.POST)
@HttpBaseURL("http://localhost:8080/")
public class UserParam extends ApiParam {

    private String name;
    private String password;

    public UserParam(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
