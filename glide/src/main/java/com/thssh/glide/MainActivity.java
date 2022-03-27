package com.thssh.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://static.wikia.nocookie.net" +
            "/spongebobsquarepants/images/a/ad/Spongebob-squarepants-1-.png" +
            "/revision/latest?cb=20200215024852&path-prefix=zh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image_view);

        Target<GlideDrawable> target = Glide.with(this)
                .load(URL)
                .into(imageView);
    }
}