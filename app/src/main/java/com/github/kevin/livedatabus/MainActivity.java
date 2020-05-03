package com.github.kevin.livedatabus;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_content);
//        init1();
        init2();
    }

    private void init1() {
        LiveDataBus.get().getChannel("event", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //UI线程接收消息
                Toast.makeText(MainActivity.this, "=====>" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //MVVM模式演练
    private void init2() {
        LiveDataTimerViewModel viewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel.class);
        viewModel.getTimer().observe(this, new Observer<String>(){

            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

    }




    public void jump(View view) {
//        LiveDataBus.get().getChannel("event").setValue("我是从UI线程发送过来的");

        new Thread(new Runnable() {
            @Override
            public void run() {
                LiveDataBus.get().getChannel("event").postValue("我是从异步线程发送过来的");
            }
        }).start();
    }

    public void nextPage(View view) {
        startActivity(new Intent(this, CustomerActivity.class));
    }

}
