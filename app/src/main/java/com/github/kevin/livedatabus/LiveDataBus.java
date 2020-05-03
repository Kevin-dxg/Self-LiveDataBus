package com.github.kevin.livedatabus;


import java.util.HashMap;
import java.util.Map;

import android.arch.lifecycle.MutableLiveData;

/**
 * 事件总线框架：界面感知，不需要注册和注销
 */
public class LiveDataBus {
    //MutableLiveData 消息通道
    private final Map<String, MutableLiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DATA_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DATA_BUS;
    }

    public <T> MutableLiveData<T> getChannel(String target, Class<T> type) {
        if (!bus.containsKey(target)) {
            bus.put(target, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(target);
    }

    public MutableLiveData<Object> getChannel(String target) {
        return getChannel(target, Object.class);
    }


}
