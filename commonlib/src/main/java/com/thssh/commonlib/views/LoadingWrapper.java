package com.thssh.commonlib.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.thssh.commonlib.R;
import com.thssh.commonlib.utils.UI;

/**
 * @author hutianhang
 */
public class LoadingWrapper extends RelativeLayout {


    public static LoadingWrapper with(View view) {
        if (view.getParent() instanceof LoadingWrapper) {
            return (LoadingWrapper) view.getParent();
        } else {
            return new LoadingWrapper(view);
        }
    }

    public static void release(View view) {
        if (view.getParent() instanceof LoadingWrapper) {
            ((LoadingWrapper) view.getParent()).releaseLoading(view);
        }
    }

    public static void dismiss(LoadingWrapper... loadings) {
        for (LoadingWrapper loading : loadings) {
            if (loading != null) {
                loading.dismiss();
            }
        }
    }

    private View mContentView;
    private ProgressBar mLoadingView;
    private Handler handler;

    ViewGroup.LayoutParams originLayoutParams;

    private LoadingWrapper(Context context) {
        this(context, null);
    }

    private LoadingWrapper(View view) {
        this(view.getContext());
        handler = new Handler(Looper.getMainLooper());
        setContentView(view);
    }

    private LoadingWrapper setContentView(View view) {
        if (view.getParent() instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view.getParent();
            int viewIndex = -1;
            for (int i = parent.getChildCount() - 1; i >= 0; i--) {
                if (view == parent.getChildAt(i)) {
                    viewIndex = i;
                    break;
                }
            }
            if (viewIndex != -1 && (originLayoutParams = view.getLayoutParams()) != null) {
                parent.removeViewAt(viewIndex);
                LayoutParams newLayoutParams = new LayoutParams(originLayoutParams);
                if (newLayoutParams.width == 0) {
                    newLayoutParams.width = view.getWidth();
                }
                if (newLayoutParams.height == 0) {
                    newLayoutParams.height = view.getHeight();
                }
                addView(view, newLayoutParams);
                mContentView = view;
                mLoadingView = new ProgressBar(view.getContext());
                mLoadingView.setIndeterminateDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.rotate_loading));
                mLoadingView.setVisibility(GONE);
                LayoutParams loadingLayoutParams = new LayoutParams(UI.dpi(14), UI.dpi(14));
                loadingLayoutParams.addRule(CENTER_IN_PARENT, TRUE);
                addView(mLoadingView, loadingLayoutParams);
                setId(view.getId());
                // replace
                parent.addView(this, viewIndex, originLayoutParams);
            }
        }
        return this;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 同步View的状态
        setVisibility(mContentView.getVisibility());
    }

    public LoadingWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoadingWrapper show() {
        showLoading(true);
        return this;
    }

    public void dismiss() {
        showLoading(false);
    }

    public void dismiss(long delay) {
        showLoading(false, delay);
    }

    boolean nextStatus = false;
    final Runnable showLoadingDelay = this::showLoadingReal;

    public void showLoading(boolean flag, long delay) {
        handler.removeCallbacks(showLoadingDelay);
        nextStatus = flag;
        handler.postDelayed(showLoadingDelay, delay);
    }

    public void showLoading(boolean flag) {
        handler.removeCallbacks(showLoadingDelay);
        nextStatus = flag;
        showLoadingReal();
    }

    private void showLoadingReal() {
        if (mContentView != null) {
            boolean flag = nextStatus;
            boolean enable = !flag;
            mContentView.setEnabled(enable);
            mContentView.setAlpha(flag ? 0.5f : 1.0f);
            mLoadingView.setVisibility(flag ? VISIBLE : GONE);
        }
    }

    public void releaseLoading(View view) {
        dismiss();
        if (view == mContentView && getParent() instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) getParent();
            int childAt = -1;
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (this == parent.getChildAt(i)) {
                    childAt = i;
                    break;
                }
            }
            if (childAt != -1) {
                removeAllViews();
                parent.removeViewAt(childAt);
                view.setId(getId());
                parent.addView(mContentView, childAt, originLayoutParams);
            }
        }
    }

}
