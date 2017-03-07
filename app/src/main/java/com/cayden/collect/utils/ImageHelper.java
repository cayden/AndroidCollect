package com.cayden.collect.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by cuiran
 * Time  17/3/7 11:07
 * Email cuiran2001@163.com
 * Description 缓存加载图片类
 */

public class ImageHelper {

    // LRUCahce 池子
    private static LruCache<String,Bitmap> mCache;
    private static Handler mHandler;
    private static ExecutorService mThreadPool;
    private static Map<ImageView,Future<?>> mTaskTags=new LinkedHashMap<>();

    private Context mContext;

    public ImageHelper(Context context){
        this.mContext=context;
        if(mCache==null){
            //最大使用的内存空间
            int maxSize=(int)(Runtime.getRuntime().freeMemory()/4);
            mCache=new LruCache<String,Bitmap>(maxSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {

                    return value.getRowBytes()*value.getHeight();
                }
            };
        }

        if(mHandler==null){
            mHandler=new Handler();
        }
        if(mThreadPool==null){
            mThreadPool= Executors.newFixedThreadPool(3);
        }
    }

    /**
     * 显示图片
     * @param iv
     * @param url
     */
    public void display(ImageView iv,String url){
        //1、先从内存中取出来
        Bitmap bitmap=mCache.get(url);
        if(null!=bitmap){
            iv.setImageBitmap(bitmap);
            return;
        }
        //2、从硬盘上取
       bitmap=loadBitmapFromLocal(url);
        if(bitmap!=null){
            //直接显示
            iv.setImageBitmap(bitmap);
            return;
        }

        //3、从网络获取图片
        loadBitmapFromNet(iv,url);

    }

    private void loadBitmapFromNet(ImageView iv,String url){
        //开线程去网络获取 使用线程池管理
        //判断是否有线程为iv加载数据
        Future<?> future=mTaskTags.get(iv);
        if(future!=null&&!future.isCancelled()&&!future.isDone()){
            //线程正在执行
            future.cancel(true);
            future=null;
        }

        future=mThreadPool.submit(new ImageLoadTask(iv,url));

        mTaskTags.put(iv,future);
    }

    class ImageLoadTask implements  Runnable{
        private String mUrl;
        private ImageView iv;

        public ImageLoadTask(ImageView iv,String url){
            this.mUrl=url;
            this.iv=iv;
        }

        @Override
        public void run() {
            try{
                HttpURLConnection conn=(HttpURLConnection) new URL(mUrl).openConnection();
                conn.setConnectTimeout(30 * 1000);// 设置连接服务器超时时间
                conn.setReadTimeout(30 * 1000);// 设置读取响应超时时间
                // 连接网络
                conn.connect();
                // 获取响应码
                int code = conn.getResponseCode();
                if(code==200){
                    InputStream is=conn.getInputStream();
                    //将流转换为bitmap
                    Bitmap bitmap=BitmapFactory.decodeStream(is);
                   //存储到本地
                    write2Local(mUrl,bitmap);
                    //存储到内存
                    mCache.put(mUrl,bitmap);
                    //图片显示:
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            display(iv,mUrl);
                        }
                    });
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 从本地加载Bitmap
     * @param url
     * @return
     */
    private Bitmap loadBitmapFromLocal(String url){
        String name;
        try{
            name=Md5Encoder.encode(url);
            File file=new File(getCacheDir(),name);
            if(file.exists()){
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());

                mCache.put(url,bitmap);

                return bitmap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将图片写在本地
     * @param url
     * @param bitmap
     */
    private void write2Local(String url,Bitmap bitmap){
        String name;
        FileOutputStream fos=null;
        try {
            name=Md5Encoder.encode(url);
            File file=new File(getCacheDir(),name);
            fos=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                    fos=null;
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    private String getCacheDir() {
        String state = Environment.getExternalStorageState();
        File dir = null;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 有sd卡
            dir = new File(Environment.getExternalStorageDirectory(), "/Android/data/" + mContext.getPackageName()
                    + "/icon");
        } else {
            // 没有sd卡
            dir = new File(mContext.getCacheDir(), "/icon");
        }

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir.getAbsolutePath();
    }

}
