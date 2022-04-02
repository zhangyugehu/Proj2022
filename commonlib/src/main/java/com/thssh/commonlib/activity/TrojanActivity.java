package com.thssh.commonlib.activity;

import static android.content.Intent.EXTRA_TITLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.thssh.commonlib.BuildConfig;
import com.thssh.commonlib.base.SimpleActivityDelegate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrojanActivity extends BaseActivity {

    public static final String EXTRA_LAYOUT = "extra_trojan_layout";
    public static final String EXTRA_TROJAN_KEY = "extra_trojan_trojan_key";
    public static final String EXTRA_TITLE = "extra_trojan_title";
    public static final String EXTRA_FRAGMENT_CLASS = "EXTRA_FRAGMENT_CLASS";
    public static final String EXTRA_FRAGMENT_ARGS = "EXTRA_FRAGMENT_ARGS";

    /**
     * 需要Activity主动调用onActivityResult的Fragment需要实现
     * 为了区分Activity#startActivityForResult和Fragment#startActivityForResult
     * 前者需要Activity主动通知Fragment，后者Framework已处理
     */
    public interface NeedCallOnActivityResult {}

    private static final String TAG_FRAGMENT = "TAG_FRAGMENT_TROJAN_ACTIVITY";
    private static final String EXTRA_SAVED_CONTAINER_ID = "extra_saved_container_id";

    private static final Map<String, ITrojan> sTrojan = new HashMap<>();
    public interface ITrojan {
        /**
         * 创建contentView
         * @param host
         * @return
         */
        View onCreateView(TrojanActivity host);
    }
    public interface ITrojanLifecycle extends ITrojan {
        void onCreate(Bundle savedInstanceState);
        void onPause();
        void onResume();
        void onDestroy();
    }

    private static String getTrojanKey() {
        String key = UUID.randomUUID().toString();
        if (sTrojan.containsKey(key)) {
            return getTrojanKey();
        }
        return key;
    }

    public static void withTrojan(Activity host, ITrojanLifecycle lifecycle) {
        Intent intent = new Intent(host, TrojanActivity.class);
        String trojanKey = getTrojanKey();
        intent.putExtra(EXTRA_TROJAN_KEY, trojanKey);
        sTrojan.put(trojanKey, lifecycle);
        host.startActivity(intent);
    }
    public static void withTrojan(Activity host, ITrojan trojan) {
        Intent intent = new Intent(host, TrojanActivity.class);
        String trojanKey = getTrojanKey();
        intent.putExtra(EXTRA_TROJAN_KEY, trojanKey);
        sTrojan.put(trojanKey, trojan);
        host.startActivity(intent);
    }
    public static void withLayout(Activity host, int layoutId, String title) {
        Intent intent = new Intent(host, TrojanActivity.class);
        intent.putExtra(EXTRA_LAYOUT, layoutId);
        intent.putExtra(EXTRA_TITLE, title);
        host.startActivity(intent);
    }
    public static void withFragment(Activity host, Class<?> fragmentClass, Bundle bundle) {
        withFragment(host, fragmentClass, bundle, -1);
    }
    public static void withFragment(Activity activity, Class<?> fragmentClass, Bundle bundle, int requestCode) {
        withFragment(activity, fragmentClass, bundle, -1, requestCode);
    }
    public static void withFragment(Context context, Class<?> fragmentClass, Bundle bundle, int flags) {
        Intent intent = new Intent(context, TrojanActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_CLASS, fragmentClass);
        intent.putExtra(EXTRA_FRAGMENT_ARGS, bundle);
        if (flags != -1) {
            intent.setFlags(flags);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void withFragment(Activity activity, Class<?> fragmentClass, Bundle bundle, int flags, int requestCode) {
        Intent intent = new Intent(activity, TrojanActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_CLASS, fragmentClass);
        intent.putExtra(EXTRA_FRAGMENT_ARGS, bundle);
        if (flags != -1) {
            intent.setFlags(flags);
        }
        activity.startActivityForResult(intent, requestCode);
    }
    public static void withFragment(Fragment fragment, Class<?> fragmentClass, Bundle bundle, int requestCode) {
        withFragment(fragment, fragmentClass, bundle, -1, requestCode);
    }
    public static void withFragment(Fragment fragment, Class<?> fragmentClass, Bundle bundle, int flags, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), TrojanActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_CLASS, fragmentClass);
        intent.putExtra(EXTRA_FRAGMENT_ARGS, bundle);
        if (flags != -1) {
            intent.setFlags(flags);
        }
        fragment.startActivityForResult(intent, requestCode);
    }
    public static void withFragment(Activity host, String fragmentClassname, Bundle bundle) {
        Intent intent = new Intent(host, TrojanActivity.class);
        try {
            Class<?> fragmentClass = Class.forName(fragmentClassname);
            intent.putExtra(EXTRA_FRAGMENT_CLASS, fragmentClass);
            intent.putExtra(EXTRA_FRAGMENT_ARGS, bundle);
            host.startActivity(intent);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private String mTrojanKey;
    private ITrojan mTrojan;
    private ITrojanLifecycle mLifecycle;
    private int mFragmentContainerId = View.NO_ID;
    private boolean shouldRecycleTrojan = true;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(mTrojanKey)) {
            shouldRecycleTrojan = false;
        }
        if (mFragmentContainerId != View.NO_ID) {
            outState.putInt(EXTRA_SAVED_CONTAINER_ID, mFragmentContainerId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(EXTRA_LAYOUT)) {
            int resId = getIntent().getIntExtra(EXTRA_LAYOUT, View.NO_ID);
            String title = getIntent().getStringExtra(EXTRA_TITLE);
            setContentView(resId);
            if (getSupportActionBar() != null && !TextUtils.isEmpty(title)) {
                getSupportActionBar().setTitle(title);
            }
        } else if(getIntent().hasExtra(EXTRA_TROJAN_KEY)) {
            mTrojan = sTrojan.get(mTrojanKey = getIntent().getStringExtra(EXTRA_TROJAN_KEY));
            if (mTrojan instanceof ITrojanLifecycle) {
                mLifecycle = (ITrojanLifecycle) mTrojan;
            }
            if (mTrojan == null) {
                finish();
            } else {
                setContentView(mTrojan.onCreateView(this));
            }
        } else if (getIntent().hasExtra(EXTRA_FRAGMENT_CLASS)) {
            FrameLayout mFragmentHost = new FrameLayout(this);
            if (savedInstanceState != null) {
                mFragmentContainerId = savedInstanceState.getInt(EXTRA_SAVED_CONTAINER_ID, ViewCompat.generateViewId());
            } else {
                mFragmentContainerId = ViewCompat.generateViewId();
            }
            mFragmentHost.setId(mFragmentContainerId);
            setContentView(mFragmentHost);
            initFragment();
        }
        if (mLifecycle != null) {
            mLifecycle.onCreate(savedInstanceState);
        }
    }

    private Fragment createFragment() throws InstantiationException, IllegalAccessException {
        Class<?> clazz = (Class<?>) getIntent().getSerializableExtra(EXTRA_FRAGMENT_CLASS);
        if (clazz != null) {
            Object clazzInstance = clazz.newInstance();
            if (clazzInstance instanceof Fragment) {
                Fragment fragment = (Fragment) clazzInstance;
                Bundle args;
                if ((args = getIntent().getBundleExtra(EXTRA_FRAGMENT_ARGS)) != null) {
                    fragment.setArguments(args);
                }
                return fragment;
            }
        }
        return null;
    }

    private void initFragment() {
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
            if (fragment == null) {
                fragment = createFragment();
            }
            if (fragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragment.isAdded()) {
                    transaction.show(fragment);
                } else {
                    transaction.add(mFragmentContainerId, fragment, TAG_FRAGMENT);
                }
                transaction.commit();
            } else if (BuildConfig.DEBUG) {
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment instanceof NeedCallOnActivityResult) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLifecycle != null) {
            mLifecycle.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLifecycle != null) {
            mLifecycle.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLifecycle != null) {
            mLifecycle.onDestroy();
        }
        if (shouldRecycleTrojan) {
            sTrojan.remove(mTrojanKey);
        }
        mTrojan = null;
    }
}
