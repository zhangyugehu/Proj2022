package com.thssh.proj2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.timer.IntervalTimer;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    EditText schemeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        schemeInput = findViewById(R.id.input_scheme);
        testIntervalTimer();
    }

    IntervalTimer timer;

    private void testIntervalTimer() {
        IntervalTimer.with(this, 1_000, () -> L.td("with doWork!! " + System.currentTimeMillis()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        IntervalTimer.pause(timer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntervalTimer.resume(timer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IntervalTimer.destroy(timer);
    }

    public void openSecond(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        for (int i = 0; i < 10000; i++) {
            // 52,858,131
            // 24,124,221
            // 24,041,304
            // 24,107,815
            // 23,949,273
//            SerializableBean bean = new SerializableBean(i);
//            intent.putExtra("serializable" + i, bean);
            // 1,048,855
            // 521,927
            // 219,010
            // 204,167
            // 200,417
            ParcelableBean parcelableBean = new ParcelableBean(20, Arrays.asList(new ParcelableBean.InnerBean("zhang")));
            intent.putExtra("parcelable", parcelableBean);
        }
        startActivity(intent);
    }

    public void openScheme(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String scheme = schemeInput.getText().toString();
        Toast.makeText(this, "to: " + scheme, Toast.LENGTH_LONG).show();
        intent.setData(Uri.parse(scheme));
        startActivity(intent);
    }
}