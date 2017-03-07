package com.cayden.collect.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cayden.collect.R;
import com.cayden.collect.WeakHandler;
import com.cayden.collect.utils.ImageHelper;

public class LedActivity extends AppCompatActivity {
    private static final String TAG="LedActivity";
    /**开启UDP组播**/
    private static final int CMD_OPEN = 1001;
    private TextView tv_led;
    private Thread receiveThread=null;

    /**
     * 创建 WatchDogHandler
     */
    private final Handler viewHandler = new ViewHandler(this);


    private ImageView iv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);

        tv_led=(TextView)findViewById(R.id.tv_led);
        iv_test=(ImageView)findViewById(R.id.iv_test);

        viewHandler.sendEmptyMessageDelayed(CMD_OPEN,2000);

        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488874686179&di=568604cb687e8579ac786fedc99aaff4&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F48540923dd54564e1e1ac2d7b7de9c82d0584fe4.jpg";
        new ImageHelper(this).display(iv_test,url);
    }

   public void check(){
       closeReceiveThread();
       receiveThread=new Thread(new Runnable() {
           @Override
           public void run() {

               try {
                   while(!receiveThread.isInterrupted()){
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              changeColor();
                          }
                      });
                       Thread.sleep(1000);
                   }
               }
               catch (Exception e){
                  e.printStackTrace();
               }
           }
       });

       receiveThread.start();
   }

    boolean isChange=false;
    private void changeColor(){
        if(!isChange){
            GradientDrawable grad= (GradientDrawable)tv_led.getBackground();
            grad.setColor(Color.BLACK);
            isChange=true;
            Log.i(TAG,"黑色");
        }else{
            GradientDrawable grad= (GradientDrawable)tv_led.getBackground();
            grad.setColor(getColor(R.color.common_green));
            isChange=false;
            Log.i(TAG,"绿色");
        }

    }


    private static class ViewHandler extends WeakHandler<LedActivity> {


        public ViewHandler(LedActivity owner) {
            super(owner);
        }

        @Override
        public void handleMessage(Message msg) {
            LedActivity activity = getOwner();
            if(activity == null) return;
            switch (msg.what){
                case CMD_OPEN:
                    activity.check();
                    break;
            }

        }
    }

    /**
     * 关闭检测组播是否正常的线程<br>
     * 2014-1-15 上午10:39:35
     *
     */
    public void closeReceiveThread(){
        try{
            if(receiveThread!=null){
                receiveThread.interrupt();
                try {
                    receiveThread.join(3000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "InterruptedException",e);
                }
                receiveThread=null;
            }
        }catch(Exception e){
            Log.e(TAG, "closeReceiveThread fund error ",e);
        }

    }
}
