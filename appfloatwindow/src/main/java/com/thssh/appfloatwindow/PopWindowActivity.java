package com.thssh.appfloatwindow;

import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.thssh.commonlib.logger.L;

public class PopWindowActivity extends AppCompatActivity {
    WindowManager.LayoutParams params;
    WindowManager windowManager;
    View contentView;

    protected void showWindow(View contentView) {
        this.contentView = contentView;
        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION);
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                ;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT;
        params.format = PixelFormat.TRANSLUCENT;
//        params.windowAnimations = R.style.topTipsAnim;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (contentView.getParent() != null) {
            windowManager.removeView(contentView);
            L.d("removeView");
        }
        L.d("addView");
        windowManager.addView(contentView, params);
    }

    protected void updateWindow() {
    }
}
