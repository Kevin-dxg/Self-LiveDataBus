package com.github.kevin.library.livedata;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.kevin.library.lifecycle.LifecycleListener;

public class HolderFragment extends Fragment {
    private int activityCode;
    private LifecycleListener lifecycleListener;

    public void setLifecycleListener(LifecycleListener lifecycleListener) {
        this.lifecycleListener = lifecycleListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCode = ((Activity) context).hashCode();
        if (lifecycleListener != null) {
            lifecycleListener.onCreate(activityCode);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (lifecycleListener != null) {
            lifecycleListener.onStart(activityCode);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (lifecycleListener != null) {
            lifecycleListener.onPause(activityCode);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (lifecycleListener != null) {
            lifecycleListener.onDetach(activityCode);
        }
    }

}
