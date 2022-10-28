package com.tsh.imagecompressor;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tsh.imagecompressor.lib.CompressException;
import com.tsh.imagecompressor.lib.Config;
import com.tsh.imagecompressor.lib.ImageCompressor;
import com.tsh.imagecompressor.lib.processor.AbsProcessor;
import com.tsh.imagecompressor.lib.processor.ConcatProcessor;
import com.tsh.imagecompressor.lib.processor.DimensionProcessor;
import com.tsh.imagecompressor.lib.processor.SizeProcessor;
import com.tsh.imagecompressor.lib.StreamProvider;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * @author hutianhang
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTag";
    ActivityResultLauncher<String> launcher;

    ImageView vImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vImage = findViewById(R.id.image_view);

        launcher = registerForActivityResult(new ActivityResultContract<String, Uri>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, String input) {
                Log.d(TAG, "createIntent() called with: context = [" + context + "], input = [" + input + "]");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                return intent;
            }

            @Override
            public Uri parseResult(int resultCode, @Nullable Intent data) {
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "User Canceled", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_OK) {
                    Log.d(TAG, "parseResult() called with: resultCode = [" + resultCode + "], data = [" + data + "]");
                    if (data == null) {
                        return null;
                    }
                    return data.getData();
                } else {
                    Toast.makeText(MainActivity.this, "Unhandled", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        }, this::onImageChooseResult);
    }

    private Bitmap srcBitmap, dstBitmap;

    private void onImageChooseResult(Uri uri) {
        if (uri == null) {
            return;
        }
        compress(() -> getContentResolver().openInputStream(uri));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                display(dstBitmap);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                display(srcBitmap);
                break;
            default: break;
        }
        return super.onTouchEvent(event);
    }

    public void onChooseImageClick(View view) {
        launcher.launch("");

//        compress(() -> getAssets().open("p1.jpg"));
//        compress(() -> getResources().openRawResource(R.raw.p1));
    }

    private void compress(StreamProvider streamer) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("处理中...")
                .setCustomTitle(new ProgressBar(this)).show();
        Executors.newSingleThreadExecutor().submit(() -> {
            long cost = Global.cost(() -> {
                try {
                    display(srcBitmap = decodeDisplayBitmap(streamer));
                    ImageCompressor.CompressResult compressResult = ImageCompressor
                            .withConfig(Config.newBuilder().setFormat(Bitmap.CompressFormat.JPEG).setStreamProvider(streamer).build())
                            .byProcessor(
//                            new DimensionProcessor().toSmallDimension())
//                            new SizeProcessor().toMediumSize())
                            new ConcatProcessor(new DimensionProcessor().toSmallDimension(), new SizeProcessor().toMediumSize()))
                            .compress();
                    dstBitmap = compressResult.toBitmap();
                    File dstFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "compress.jpg");
                    compressResult.toFile(dstFile);
                    Log.i(TAG, "result size: " + Formatter.formatFileSize(this, dstFile.length()));
                } catch (CompressException | IOException e) {
                    e.printStackTrace();
                } finally {
                    vImage.post(dialog::dismiss);
                }
            });
            Log.i(TAG, "cost: " + cost + "ms");
        });
    }

    private Bitmap decodeDisplayBitmap(StreamProvider stream) throws IOException, CompressException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream.openStream(), null, options);
        Log.i(TAG, "decodeDisplayBitmap: " + options.outWidth + "x" + options.outHeight);
        int dstHeight = vImage.getHeight();
        options.inJustDecodeBounds = false;
        options.inSampleSize = options.outWidth / dstHeight;
        return BitmapFactory.decodeStream(stream.openStream(), null, options);
    }

    private void display(Bitmap bitmap) {
        int dstHeight = vImage.getHeight();
        if (bitmap == null) {
            Log.i(TAG, "display: bitmap null.");
            return;
        }
        Log.i(TAG, "display: " + bitmap.getHeight() + "x" + bitmap.getWidth());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, dstHeight * bitmap.getWidth() / bitmap.getHeight(), dstHeight, false);
        vImage.post(() -> vImage.setImageBitmap(scaledBitmap));
    }
}