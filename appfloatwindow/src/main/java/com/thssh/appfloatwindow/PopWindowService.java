package com.thssh.appfloatwindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.thssh.commonlib.logger.L;

public class PopWindowService extends Service implements WindowManagerMoveDelegate.OnMoveListener {

    @Override
    public void onMove(float dx, float dy) {
        if (windowManager != null && mParam != null && rootView != null) {
            mParam.x = (int) dx;
            mParam.y = (int) dy;
            windowManager.updateViewLayout(rootView, mParam);
        }
    }

    class InnerBinder extends Binder {
        public PopWindowService getService() {
            return PopWindowService.this;
        }
    }

    private final InnerBinder mBinder = new InnerBinder();

    public PopWindowService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    FloatRelativeLayout rootView;
    MutableContextWrapper contextWrapper;
    View contentView;

    public <T extends View> T getContentView() {
        return (T)contentView;
    }

    public void initRootView(Context context) {
//        Context context = getBaseContext();
        L.d("getRootView");
        if (rootView == null) {
            L.d("createView");
            contextWrapper = new MutableContextWrapper(context);
            rootView = new FloatRelativeLayout(context);
            rootView.setOnMoveListener(this);
            addOnlyOneChildToRoot();
        } else {
            L.d("setBaseContext");
            contextWrapper.setBaseContext(context);
        }
    }

    public void setContentView(View contentView) {
        // TODO: 2022/6/7 remove old
        if (contentView != null) {
            this.contentView = contentView;
            addOnlyOneChildToRoot();
        }
    }

    private void addOnlyOneChildToRoot() {
        if (rootView != null && contentView != null) {
            rootView.removeAllViews();
            rootView.addView(contentView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    WindowManager windowManager;
    WindowManager.LayoutParams mParam;

    public void hideWidow(Context context) {
        windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        if (rootView.getParent() == null) {
            windowManager.removeView(rootView);
        }
    }

    protected void showWindow(Context context) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION);
        if (mParam != null) {
            params.x = mParam.x;
            params.y = mParam.y;
        }
        mParam = params;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.gravity = Gravity.END|Gravity.TOP;
        params.format = PixelFormat.TRANSLUCENT;
//        params.windowAnimations = R.style.topTipsAnim;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        initRootView(context);
        if (windowManager != null) {
            try {
                L.d("removeView");
                windowManager.removeView(rootView);
            } catch (Exception e) {
                L.d("removeView failed.", e);
            }
        }

        windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

        if (rootView.getParent() == null) {
            L.d("addView");
            windowManager.addView(rootView, params);
        } else {
            L.d("rootView has parent.");
        }
    }
}