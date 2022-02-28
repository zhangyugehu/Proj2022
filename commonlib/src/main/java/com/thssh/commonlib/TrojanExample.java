package com.thssh.commonlib;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.thssh.commonlib.activity.TrojanActivity;

public class TrojanExample {

    public void withLayout(Activity host) {
        TrojanActivity.withLayout(
                host,
                R.layout.support_simple_spinner_dropdown_item,
                "support_simple_spinner_dropdown_item"
        );
    }

    public void withTrojan1(Activity host) {
        TrojanActivity.withTrojan(host, new TrojanActivity.ITrojan() {
            @Override
            public View onCreateView(TrojanActivity host) {
                return null;
            }
        });
    }

    public void withTrojan2(Activity host) {
        TrojanActivity.withTrojan(host, new InnerTrojanPage());
    }

    static class InnerTrojanPage implements TrojanActivity.ITrojanLifecycle {

        @Override
        public View onCreateView(TrojanActivity host) {
            return null;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onResume() {

        }

        @Override
        public void onDestroy() {

        }
    }
}
