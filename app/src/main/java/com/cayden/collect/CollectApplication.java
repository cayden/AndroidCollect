package com.cayden.collect;

import android.app.Application;

import com.jiongbull.jlog.JLog;
import com.jiongbull.jlog.qiniu.JLogQiniu;
import com.jiongbull.jlog.qiniu.QiniuInterface;

import java.io.IOException;

import cn.bmob.v3.Bmob;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cuiran on 16/5/10.
 */
public class CollectApplication extends Application {
    /**
     * SDK初始化也可以放到Application中
     * 46c730e7e33eabeb3ec790b3fb0a02d7
     * 3124f50157a5df138aba77a85e1d8909
     */
    public static String APPID ="77f2199061fe7d7d935a2f9c3159af01";
    @Override
    public void onCreate() {
        super.onCreate();
        //第二：默认初始化
        Bmob.initialize(this,APPID);

        JLog.init(this)
                .writeToFile(true)
                .setLogDir(getString(R.string.app_name));
        JLogQiniu.init(JLog.getSettings(), new QiniuInterface() {
            @Override
            public String getToken() {
                /* 在这里向你的服务器请求生成token，或者使用固定长效的token(七牛不推荐) */
                return "";
            }
        }, false, false);

//        initQiNiu();

    }
    private  OkHttpClient mOkHttpClient=null;
    private void initQiNiu(){
        mOkHttpClient=new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url("http://10.1.2.2/php-sdk/examples/gettoken.php");
        Request request = requestBuilder.build();
        Call mcall= mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              final String token=response.body().string();
                JLog.init(CollectApplication.this)
                        .writeToFile(true)
                        .setLogDir(getString(R.string.app_name));

                JLogQiniu.init(JLog.getSettings(), new QiniuInterface() {
                    @Override
                    public String getToken() {
                        return token;
                    }
                },false,false);

            }
        });
    }
}
