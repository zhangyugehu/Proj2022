package com.thssh.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;

/**
 * RequestManager#into=>
 * GenericRequestBuilder#into
 * !DrawableRequestBuilder#fitCenter
 * #transform
 * #super.transform
 * GenericRequestBuilder#transform
 * *transformation(GifBitmapWrapperTransformation)#transform
 *
 * !#buildImageViewTarget
 * ImageViewTargetFactory#buildTarget
 * =>new GlideDrawableImageTarget
 *
 * !#into(T target)
 * prev(Request)[clear] = *target(GlideDrawableImageViewTarget)#getRequest()
 * request(Request)=>
 * #buildRequest()
 * #buildRequestRecursive
 * #obtainRequest
 * GenericRequest#obtain
 *
 * !requestTracker(RequestTracker)#runRequest(request)
 * RequestTracker#begin
 *
 * RequestManager(LifecycleListener)#onStart
 * #resumentRequests=>
 * RequestTracker#resumeRequests=>
 * GenericRequest#begin
 * #getSize
 * #onSizeReady
 * *dataFetcher =
 * LoadProvider#getModelLoader
 * ModelLoader(ImageVideoModelLoader)#getResourceFetcher
 * ModelLoader(StreamStringLoader)#getResourceFetcher
 * ModelLoader(UriLoader)#getResourceFetcher
 * ModelLoader(HttpUrlGlideUrlLoader)#getResourceFetcher
 * new DataFetcher(HttpUrlFetcher)
 *
 * *transcoder=>
 * LoadProvider(ChildLoadProvider)#getTranscoder
 * LoadProvider(FixedLoadProvider)#getTranscoder
 * transcoder(GifBitmapWrapperDrawableTranscoder)
 *
 * *loadStatus(LoadStatus) =>
 * Engine#load
 * *cached(EngineResource) =>
 * #loadFromCahce
 * #getEngineResourceFromCache
 * LruResourceCache#remove
 *
 * *chche != null?
 * ResourceCallback#onResourceReady
 *
 * *chche == null?
 * *active(EngineResource) =>
 * #loadFromActiveResources
 *
 * *active != null?
 * ResourceCallback#onResourceReady
 *
 * *active == null?
 * *current(EngineJob) =>
 * jobs(HashMap).get
 *
 * !=null
 * new LoadStatus(cb, current)
 *
 * == null
 * Engine$EngineFactory#build
 * new EngineJob
 * new DecoderJob#start
 *
 * new Load Status()
 *
 * GlideDrawableImageViewTarget#onLoadStarted(Drawable)
 */
public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://static.wikia.nocookie.net" +
            "/spongebobsquarepants/images/a/ad/Spongebob-squarepants-1-.png" +
            "/revision/latest?cb=20200215024852&path-prefix=zh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image_view);

        Target<GlideDrawable> target = Glide.with(this).load(Uri.fromFile())
                .load(URL)
                .into(imageView);

    }
}