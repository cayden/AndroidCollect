package com.cayden.collect.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cayden.collect.R;
import com.cayden.collect.activity.base.BaseActivity;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by cuiran on 16/8/12.
 */
public class MediaProjectionActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_CODE = 1;
    private int mScreenDensity;
    private MediaProjectionManager mProjectionManager;
    private static final int DISPLAY_WIDTH = 960;
    private static final int DISPLAY_HEIGHT = 1280;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionCallback mMediaProjectionCallback;
    private ToggleButton mToggleButton;
    private MediaRecorder mMediaRecorder;

    private ImageView imgView;

    private ImageReader mImageReader = null;

    @SuppressLint("SdCardPath")
    private final static String captureFileName = "/sdcard/capture.mp4";

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_record;
    }

    @Override
    protected void initView() {

        imgView = (ImageView)findViewById(R.id.imgview);
        customFindViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCapture();
            }
        });

        //设置当前Activity不被录制
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //获取屏幕的分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        mToggleButton = (ToggleButton) findViewById(R.id.toggle);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleScreenShare(v);
            }
        });

        mMediaProjectionCallback = new MediaProjectionCallback();

        //初始化视频编码器
        mMediaRecorder = new MediaRecorder();
        initRecorder(captureFileName);
        prepareRecorder();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != PERMISSION_CODE) {
            Log.e(TAG, "Unknown request code: " + requestCode);
            return;
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Screen Cast Permission Denied",
                    Toast.LENGTH_SHORT).show();
            mToggleButton.setChecked(false);
            return;
        }

        //这里的图片格式是RGBA_8888,这里的图片大小和下面的createVirtualPlayer的尺寸要保持一致就可以了
        mImageReader = ImageReader.newInstance(
                DISPLAY_WIDTH, DISPLAY_HEIGHT,
                PixelFormat.RGBA_8888, 2);

        mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
        mMediaProjection.registerCallback(mMediaProjectionCallback, null);
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startCapture(){
        Image image = mImageReader.acquireLatestImage();
        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width+rowPadding/pixelStride, height,
                Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0,width, height);
        image.close();
        imgView.setImageBitmap(bitmap);
    }

    public void onToggleScreenShare(View view) {
        if (((ToggleButton) view).isChecked()) {
            // start
            shareScreen();
        } else {
            // stop
            stopRecorder();
            Log.v(TAG, "Recording Stopped");
            stopScreenSharing();
            initRecorder(captureFileName);
            prepareRecorder();
        }
    }

    private void stopRecorder() {
        mMediaRecorder.stop();
        mMediaRecorder.reset();
    }

    private void shareScreen() {
        if (mMediaProjection == null) {
            startActivityForResult(
                    mProjectionManager.createScreenCaptureIntent(),
                    PERMISSION_CODE);
            return;
        }
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    private void stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
    }

    private VirtualDisplay createVirtualDisplay() {
        /**
         * 创建虚拟画面
         * 第一个参数：虚拟画面名称
         * 第二个参数：虚拟画面的宽度
         * 第三个参数：虚拟画面的高度
         * 第四个参数：虚拟画面的标志
         * 第五个参数：虚拟画面输出的Surface
         * 第六个参数：虚拟画面回调接口
         */
        return mMediaProjection
                .createVirtualDisplay("MainActivity", DISPLAY_WIDTH,
                        DISPLAY_HEIGHT, mScreenDensity,
                        DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                        mMediaRecorder.getSurface()/*mImageReader.getSurface()*/,
                        null /* Callbacks */, null /* Handler */);
    }



    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (mToggleButton.isChecked()) {
                mToggleButton.setChecked(false);
                stopRecorder();
                Log.v(TAG, "Recording Stopped");
                initRecorder(captureFileName);
                prepareRecorder();
            }
            mMediaProjection = null;
            stopScreenSharing();
            Log.i(TAG, "MediaProjection Stopped");
        }
    }

    private void prepareRecorder() {
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    private void initRecorder(String path) {
        /**
         *  视频编码格式：default，H263，H264，MPEG_4_SP
         获得视频资源：default，CAMERA
         音频编码格式：default，AAC，AMR_NB，AMR_WB
         获得音频资源：defalut，camcorder，mic，voice_call，voice_communication,
         voice_downlink,voice_recognition, voice_uplink
         输出方式：amr_nb，amr_wb,default,mpeg_4,raw_amr,three_gpp
         */
        //设置音频源
//        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置视频源：Surface和Camera 两种
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        //设置视频输出格式
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //设置视频编码格式
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //设置音频编码格式
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //设置视频编码的码率
        mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
        //设置视频编码的帧率
        mMediaRecorder.setVideoFrameRate(30);
        //设置视频尺寸大小
        mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        //设置视频输出路径
        mMediaRecorder.setOutputFile(path);

    }
}
