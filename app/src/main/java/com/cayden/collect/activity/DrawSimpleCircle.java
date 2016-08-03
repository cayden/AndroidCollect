package com.cayden.collect.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.cayden.collect.mode.reflect.Student;

import java.lang.reflect.Field;

/**
 * Created by cuiran on 16/7/6.
 */
public class DrawSimpleCircle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // 屏幕的分辨率
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        setContentView(new MyCicle(this, width, height));

        testReflect();
    }

    private void testReflect(){
        Student student=new Student();
        try{
            Field property1=student.getClass().getDeclaredField("id");
            System.out.println(property1);//private
            Field property3=student.getClass().getField("nickname");
            System.out.println(property3);//public java.lang.String com.cx.test.Student.nickname

            Field property4=student.getClass().getDeclaredField("ee_1");
            System.out.println(property4);//public java.lang.String com.cx.test.Student.ee_1

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class MyCicle extends View{
        private Context context;
        private int width;
        private int height;

        private  int []colors=new int[]{Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,Color.GRAY,Color.MAGENTA,Color.RED,Color.LTGRAY};
        private String[] colorStrs=new String[]{"黑色","蓝色","青绿色","绿色","灰色","洋红色","红色","浅灰色"};

        private float bigR;

        private float litterR;
        //屏幕中间点的x,y坐标
        private float centerX,centerY;

        public MyCicle(Context context,int width,int height){
            super(context);
            this.context=context;
            this.width=width;
            this.height=height;
            setFocusable(true);

            System.out.println("width="+width+"<---->height="+height);
            // 设置两个圆的半径
            bigR = (width - 20)/2;
            litterR = bigR/2;

            centerX = width/2;
            centerY = height/2;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //画背景色
            Paint bg=new Paint();
            bg.setColor(Color.WHITE);
            Rect bgR=new Rect(0,0,width,height);
            canvas.drawRect(bgR,bg);

            float start=0f;
            Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

            for(int i=0;i<4;i++){
                //注意一定要先画大圆，再画小圆，不然看不到效果，小圆在下面会被大圆覆盖
                // 画大圆
                RectF bigOval=new RectF(centerX-bigR,centerY-bigR,centerX+bigR,centerY+bigR);
                paint.setColor(colors[i]);
                canvas.drawArc(bigOval,start,90,true,paint);

                //画小圆
                RectF litterOval=new RectF(centerX-litterR,centerY-litterR,centerX+litterR,centerY+litterR);
                paint.setColor(colors[i+2]);
                canvas.drawArc(litterOval,start,90,true,paint);

                start+=90F;
            }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x=event.getX();
            float y=event.getY();
            whichCircle(x,y);

            return super.onTouchEvent(event);
        }

        private void whichCircle(float x, float y){
            //将屏幕中的点转换成以屏幕中心
            float mx=x-centerX;
            float my=y-centerY;
            float result=mx*mx+my*my;

            StringBuilder tip=new StringBuilder();
            tip.append("您点击了");
            //高中的解析几何
            if(result<=litterR*litterR){ //点击的点在小圆内
                tip.append("小圆的");
                tip.append(colorStrs[whichZone(mx,my)+2]);
                tip.append("区域");
            }else if(result<=bigR*bigR){
                tip.append("大圆的");
                tip.append(colorStrs[whichZone(mx,my)]);
            }else {
                tip.append("作用区域以为的区域");
            }
            Toast.makeText(context,tip,Toast.LENGTH_SHORT).show();;

        }


        private int whichZone(float x,float y){
            //简单的象限点处理处理
            //第一象限在右下角，第二象限在左下角
            if(x>0&&y>0){
                return 0;
            }else if(x>0&&y<0){
                return 3;
            }else if(x<0&&y<0){
                return 2;
            }else if(x<0&&y>0){
                return 1;
            }

            return -1;
        }
    }
}
