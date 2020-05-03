package com.github.kevin.livedatabus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.github.kevin.library.LiveDataBus;
import com.github.kevin.library.livedata.Observer;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        init();
    }

    private void init() {
        LiveDataBus.get().getChannel("event", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //UI线程接收消息
                Toast.makeText(CustomerActivity.this, "自定义=====>" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void send(View view) {
//        LiveDataBus.get().getChannel("event").setValue("我是从UI线程发送过来的");

        new Thread(new Runnable() {
            @Override
            public void run() {
                LiveDataBus.get().getChannel("event").postValue("我是从异步线程发送过来的");
            }
        }).start();
    }

}
