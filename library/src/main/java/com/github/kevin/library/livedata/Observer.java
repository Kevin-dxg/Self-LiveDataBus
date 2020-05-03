package com.github.kevin.library.livedata;

import android.support.annotation.Nullable;

public abstract class Observer<T> {
    //活跃状态
    static final int STATE_ACTIVE = 1;
    //暂停状态
    static final int STATE_ONPAUSE = 2;
    //销毁状态
    static final int STATE_DESTROY = 3;
    //初始化状态
    static final int STATE_INIT = 4;

    private int state = STATE_INIT;

    public abstract void onChanged(@Nullable T t);

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
