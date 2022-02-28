package com.thssh.activitygc.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thssh.activitygc.MemAllocActivity;
import com.thssh.activitygc.R;
import com.thssh.activitygc.SingleInstanceActivity;
import com.thssh.commonlib.activity.BaseFragment;
import com.thssh.commonlib.activity.TrojanActivity;
import com.thssh.commonlib.logger.L;

public class TransitionFragment extends BaseFragment {
    private static final String ARGS_MSG = "args_message";

    public static Bundle newArgs(String message) {
        Bundle args = new Bundle();
        args.putString(ARGS_MSG, message);
        return args;
    }


    private TextView msgLabel;
    private String message;

    public TransitionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(ARGS_MSG);
            L.d(getTag(), "message: ", message);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transition, container, false);
    }

//    @Override
//    public void setArguments(@Nullable Bundle args) {
//        super.setArguments(args);
//        if (args != null) {
//            message = args.getString(ARGS_MSG);
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        msgLabel = view.findViewById(R.id.label_msg);
        msgLabel.setText(message);
        view.findViewById(R.id.btn_single_instance).setOnClickListener((v) -> {
            if (getActivity() != null) getActivity().startActivity(new Intent(getActivity(), SingleInstanceActivity.class));
        });
        view.findViewById(R.id.btn_mem_alloc).setOnClickListener((v) -> {
            if (getActivity() != null) getActivity().startActivity(new Intent(getActivity(), MemAllocActivity.class));
        });
        view.findViewById(R.id.btn_self).setOnClickListener((v) -> {
            if (getActivity() != null) TrojanActivity.withFragment(getActivity(), TransitionFragment.class, newArgs(message + "[self]"));
        });
    }
}