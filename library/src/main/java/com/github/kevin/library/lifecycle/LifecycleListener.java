package com.github.kevin.library.lifecycle;

public interface LifecycleListener {


    void onCreate(int activityCode);

    void onStart(int activityCode);

    void onPause(int activityCode);

    void onDetach(int activityCode);
}
