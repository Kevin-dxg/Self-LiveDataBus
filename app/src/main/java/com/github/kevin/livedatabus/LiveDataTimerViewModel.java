package com.github.kevin.livedatabus;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class LiveDataTimerViewModel extends ViewModel{
    //消息通道
    private MutableLiveData<String> mTime = new MutableLiveData<>();
    private String data = "数据来了：";
    private int index = 0;

    public LiveDataTimerViewModel(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mTime.postValue(data + index++);

            }
        },100,1000);
    }

    public MutableLiveData<String> getTimer() {
        return mTime;
    }

}
