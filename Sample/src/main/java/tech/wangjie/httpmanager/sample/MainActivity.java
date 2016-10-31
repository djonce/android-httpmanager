package tech.wangjie.httpmanager.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import tech.wangjie.httpmanager.HttpManager;
import tech.wangjie.httpmanager.HttpRequest;
import tech.wangjie.httpmanager.JsonHttpListener;
import tech.wangjie.httpmanager.JsonRequest;
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
            public void onStart(HttpRequest request) {
                super.onStart(request);
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
        getSampleData();
    }
}
