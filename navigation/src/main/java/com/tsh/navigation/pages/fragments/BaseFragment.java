package com.tsh.navigation.pages.fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.navigation.fragment.NavigationFragment;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.simple.SimpleTextWatcher;

public class BaseFragment extends com.thssh.commonlib.activity.BaseFragment implements NavigationFragment {

    @Override
    public void willAppear() {
        L.d(getLogTag(), "willAppear");
    }

    @Override
    public void willDisappear() {
        L.d(getLogTag(), "willDisappear");
    }

    public <T extends TextView> T setLabelText(@IdRes int id, CharSequence text) {
        T tv;
        if ((tv = findViewById(id)) != null && text != null) {
            tv.setText(text);
        }
        return tv;
    }

    public <T extends EditText> T addTextWatch(@IdRes int id, SimpleTextWatcher watcher) {
        T et;
        if ((et = findViewById(id)) != null) {
            et.addTextChangedListener(watcher);
        }
        return et;
    }

    public <T extends View> T bindClick(@IdRes int id, View.OnClickListener listener) {
        T view;
        if ((view = findViewById(id)) != null) {
            view.setOnClickListener(listener);
        }
        return view;
    }
}
