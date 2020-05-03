package com.github.kevin.library.livedata;


import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kevin.library.lifecycle.LifecycleListener;


//消息通道  LiveData 架构
public class LiveData<T> {
    private HashMap<Integer, Observer<T>> map = new HashMap<>();
    //延迟消息
    private HashMap<Integer, List<T>> mPendingDelayList = new HashMap<>();

    private Handler handler = new Handler(Looper.getMainLooper());


    private LifecycleListener lifecycleListener = new LifecycleListener() {
        @Override
        public void onCreate(int activityCode) {
            map.get(activityCode).setState(Observer.STATE_INIT);
        }

        @Override
        public void onStart(int activityCode) {
            map.get(activityCode).setState(Observer.STATE_ACTIVE);
            if (mPendingDelayList.get(activityCode) == null || mPendingDelayList.get(activityCode).size() == 0) {
                return;
            }
            for (T t : mPendingDelayList.get(activityCode)){
                map.get(activityCode).onChanged(t);
            }
            mPendingDelayList.get(activityCode).clear();
        }

        @Override
        public void onPause(int activityCode) {
            map.get(activityCode).setState(Observer.STATE_ONPAUSE);
        }

        @Override
        public void onDetach(int activityCode) {
            map.remove(activityCode);
        }

    };

    public void observe(AppCompatActivity activity, Observer<T> observer) {
        FragmentManager fm = activity.getSupportFragmentManager();
        HolderFragment current = (HolderFragment) fm.findFragmentByTag("com.github.kevin");
        if (current == null) {
            current = new HolderFragment();
            fm.beginTransaction().add(current, "com.github.kevin").commitAllowingStateLoss();
        }
        current.setLifecycleListener(lifecycleListener);
        map.put(activity.hashCode(), observer);
    }

    public void setValue(T value) {
        List<Observer> destoryList = new ArrayList<>();
        for (Map.Entry<Integer, Observer<T>> entry : map.entrySet()) {
            Observer<T> observer = entry.getValue();
            Integer code = entry.getKey();
            switch (observer.getState()) {
                case Observer.STATE_ACTIVE:
                    observer.onChanged(value);
                    break;
                case Observer.STATE_ONPAUSE:
                    if (mPendingDelayList.get(code) == null) {
                        mPendingDelayList.put(code, new ArrayList<T>());
                    }
                    if (!mPendingDelayList.get(code).contains(value)) {
                        mPendingDelayList.get(code).add(value);
                    }
                    break;
                case Observer.STATE_DESTROY:
                    destoryList.add(observer);
                    break;
            }
        }
    }

    public void postValue(final T value) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                setValue(value);
            }
        });
    }


}
