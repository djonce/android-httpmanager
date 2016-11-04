package tech.wangjie.httpmanager.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.File;

import tech.wangjie.httpmanager.FileHttpListener;
import tech.wangjie.httpmanager.HttpConfig;
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

    ProgressBar progressBar;
    Button button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDownload();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpConfig httpConfig = new HttpConfig(MainActivity.this)
                        .setDebugged(true)
                        .setConnectTimeout(15000);

                HttpManager.initialize(httpConfig);
            }
        });


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
//        getSampleFile();

        getSampleData();
    }


    private void cancelDownload() {

        HttpManager.getInstance().cancel(download);
    }

    String api = "http://test.19ba.cn/brave.mp3";
    HttpRequest download = new StringRequest(api);

    private void getSampleFile() {

        HttpManager.getInstance().enqueue(download, new FileHttpListener() {
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

                progressBar.setProgress((int) (progress * 100));
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
