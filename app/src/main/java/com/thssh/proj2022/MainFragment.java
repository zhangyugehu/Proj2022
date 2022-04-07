package com.thssh.proj2022;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thssh.commonlib.activity.TrojanActivity;
import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.timer.IntervalTimer;

public class MainFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button).setOnClickListener(this);
        IntervalTimer.with(this, 3_500, this::inInterval);
    }

    private void inInterval() {
        L.td("MainFragment", "inInterval");
    }

    @Override
    public void onClick(View v) {
//        new AlertDialog.Builder(v.getContext())
//                .setTitle("Title")
//                .setMessage("Message")
//                .show();

        TrojanActivity.withFragment(requireActivity(), HandlerFragment.class, null);
//        TrojanActivity.withTrojan(requireActivity(), new TrojanActivity.ITrojan() {
//            @Override
//            public View onCreateView(TrojanActivity host) {
//                return LayoutInflater.from(host).inflate(R.layout.activity_second, null);
//            }
//        });
    }
}
