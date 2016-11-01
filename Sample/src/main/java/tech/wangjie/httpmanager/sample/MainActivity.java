package tech.wangjie.httpmanager.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

import tech.wangjie.httpmanager.FileHttpListener;
import tech.wangjie.httpmanager.HttpManager;
import tech.wangjie.httpmanager.HttpRequest;
import tech.wangjie.httpmanager.JsonHttpListener;
import tech.wangjie.httpmanager.JsonRequest;
import tech.wangjie.httpmanager.StringRequest;
import tech.wangjie.httpmanager.sample.test.User;
import tech.wangjie.httpmanager.sample.test.UserModel;
import tech.wangjie.httpmanager.sample.test.UserParam;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void getSampleData() {

        UserParam user = new UserParam("Jonce", "xxxxxxx");
        final HttpRequest request = new JsonRequest(user);
        HttpManager.getInstance().enqueue(request, new JsonHttpListener<UserModel>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(UserModel response) {
                Log.e(TAG, response.toString());
                if (response.hasResult()) {
                    User u = response.data;

                    Log.e(TAG, u.toString());
                }
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
                Log.e(TAG, error);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });


    }

    public void doClick(View view) {
//        getSampleData();
        getSampleFile();
    }

    private void getSampleFile() {

        String api = "http://issuecdn.baidupcs.com/issue/netdisk/yunguanjia/BaiduYunGuanjia_5.4.10.exe";
        HttpManager.getInstance().enqueue(new StringRequest(api), new FileHttpListener("test.exe", getCacheDir().getAbsolutePath()) {
            @Override
            public void onStart() {
                super.onStart();
                Log.e(TAG, "onStart");
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
                Log.e(TAG, "onFail:" + error);
            }

            @Override
            public void onSuccess(File response) {
                Log.e(TAG, "onSuccess:" + response.getAbsolutePath());
            }

            @Override
            public void inProgress(float progress, long total) {
                super.inProgress(progress, total);

                Log.e(TAG, "Progress :" + progress + "  total:" + total);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.e(TAG, "onFinish");
            }
        });
    }
}
