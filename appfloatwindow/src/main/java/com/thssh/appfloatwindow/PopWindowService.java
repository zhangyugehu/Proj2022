package com.thssh.appfloatwindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thssh.commonlib.logger.L;

public class PopWindowService extends Service implements FloatRelativeLayout.OnMoveListener {

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

    FloatRelativeLayout root;
    MutableContextWrapper contextWrapper;
    TextView textView;

    public View getRootView(Context context) {
        L.d("getRootView");
        if (root == null) {
            L.d("createView");
            contextWrapper = new MutableContextWrapper(context);
            root = new FloatRelativeLayout(context);
            root.setOnClickListener((v) -> Toast.makeText(context, "FloatRelativeLayout", Toast.LENGTH_LONG).show());
            root.setListener(this);
            textView = new TextView(context);
            textView.setText("Hahahahah");
//            textView.setOnClickListener((v) -> Toast.makeText(context, textView.getText(), Toast.LENGTH_LONG).show());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            textView.setTextColor(Color.parseColor("#ff2fa9"));
            root.addView(textView);
        } else {
            L.d("setBaseContext");
            contextWrapper.setBaseContext(context);
        }
        return root;
    }

    @Override
    public void onMoved(Point point) {
        if (windowManager != null && mParam != null && root != null) {
            mParam.x = point.x;
            mParam.y = point.y;
            windowManager.updateViewLayout(root, mParam);
        }
    }

    WindowManager windowManager;
    WindowManager.LayoutParams mParam;

    protected void showWindow(Context context) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION);
        if (mParam != null) {
            params.x = mParam.x;
            params.y = mParam.y;
        }
        mParam = params;
        params.flags =
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//                    | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        ;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.START;
        params.format = PixelFormat.TRANSLUCENT;
//        params.windowAnimations = R.style.topTipsAnim;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        View rootView = getRootView(context);

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