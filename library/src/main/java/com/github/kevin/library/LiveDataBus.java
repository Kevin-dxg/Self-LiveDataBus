package com.github.kevin.library;


import com.github.kevin.library.livedata.LiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件总线框架：界面感知，不需要注册和注销
 */
public class LiveDataBus {
    //LiveData 消息通道
    private final Map<String, LiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DATA_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DATA_BUS;
    }

    public <T> LiveData<T> getChannel(String target, Class<T> type) {
        if (!bus.containsKey(target)) {
            bus.put(target, new LiveData<>());
        }
        return (LiveData<T>) bus.get(target);
    }

    public LiveData<Object> getChannel(String target) {
        return getChannel(target, Object.class);
    }


}
