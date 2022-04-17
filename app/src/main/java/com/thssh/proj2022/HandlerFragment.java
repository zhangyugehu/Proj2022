package com.thssh.proj2022;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thssh.commonlib.logger.L;

public class HandlerFragment extends Fragment implements Handler.Callback {

    Handler handler;

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        L.d("Handler.Callback", msg.what, msg.obj);
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper(), this) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                L.d("handleMessage", msg.what, msg.obj);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.d("START");
        L.d("callHandler1");
        callHandler("callHandler1");
        L.d("callHandler2");
        callHandler("callHandler2");
        L.d("END");
    }

    private void callHandler(String obj) {
        handler.obtainMessage(1, obj).sendToTarget();
    }
}
