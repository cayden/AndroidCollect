package com.cayden.collect.custom;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by cuiran on 16/7/8.
 */
public class RadialProgressWidget extends View {

    private static final String TAG=RadialProgressWidget.class.getSimpleName();
    public static final int COLOR_TYPE_RISK=1;
    public static final int COLOR_TYPE_VIRUS=2;
    /**绿色 外圈*/
    private static final int  BORDER_GREEN_COLOR = Color.parseColor("#1e892b");
    /**绿色 浅 中 深*/
    private static final int[] SECTION_GREEN_COLORS = {Color.parseColor("#e9f6eb"),Color.parseColor("#6ac575"),Color.parseColor("#26ab36")};

    /**橙色 外圈*/
    private static final int  BORDER_ORANGE_COLOR =Color.parseColor("#cc7600");
    /**橙色 浅 中 深*/
    private static final int[] SECTION_ORANGE_COLORS = {Color.parseColor("#fff4e5"),Color.parseColor("#ffc77a"),Color.parseColor("#ff9400")};

    /**红色  外圈*/
    private static final int  BORDER_RED_COLOR =Color.parseColor("#be0000");
    /**红色 浅 中 深*/
    private static final int[] SECTION_RED_COLORS = {Color.parseColor("#fccccc"),Color.parseColor("#f45454"),Color.parseColor("#ee0000")};


    private int width;
    private int height;

    private float bigR;//大圆半径
    private float middleR;//中圆半径
    private float litterR;//小圆半径
    //View中间点的x,y坐标
    private float centerX,centerY;

    private float mCenterTextSize = 0.0f;
    private float mSecondaryTextSize = 0.0f;
    private int mDiameter = 200;

    private boolean isScaning=true;

    public RadialProgressWidget(Context context) {
        super(context);
        initView();
    }
    public RadialProgressWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RadialProgressWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }



    private void initView(){

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


        //画大圆
        RectF bigOval=new RectF(centerX-bigR,centerY-bigR,centerX+bigR,centerY+bigR);
        paint.setColor(BORDER_GREEN_COLOR);
        canvas.drawArc(bigOval,start,360,true,paint);

        //画中圆
        RectF middleOval=new RectF(centerX-middleR,centerY-middleR,centerX+middleR,centerY+middleR);
        paint.setColor(SECTION_GREEN_COLORS[2]);
        canvas.drawArc(middleOval,start,360,true,paint);

        //画小圆
        RectF litterOval=new RectF(centerX-litterR,centerY-litterR,centerX+litterR,centerY+litterR);
        paint.setColor(Color.WHITE);
        canvas.drawArc(litterOval,start,360,true,paint);

        //画字或者画图片
        int color=SECTION_GREEN_COLORS[2];
        String str="设备安全";
        paint.setColor(color);
        paint.setTextSize(mCenterTextSize/3);
        paint.setFakeBoldText(true);//加粗
        float textWidth = paint.measureText(str);
        canvas.drawText(str, centerX - (textWidth/2), centerY, paint);
    }

    /**
     * 改变颜色数组内颜色值位置，实现颜色转动
     * @param arr 颜色数组
     */
    public void rotateArray(int[] arr) {
        int tmp = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[arr.length - 1] = tmp;
//        invalidate();   //重绘
    }


    /**
     * 该方法会在onCreate之后，onDraw之前调用
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG,"onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        if(w > h) {
            mDiameter = h;
            bigR = mDiameter/2 - (getPaddingTop() + getPaddingBottom());
        } else {
            mDiameter = w;
            bigR = mDiameter/2 - (getPaddingLeft() + getPaddingRight());
        }
        width=w;
        height=h;
        middleR=bigR-30;
        litterR = bigR/2;
        centerX = width/2;
        centerY = height/2;
        mCenterTextSize=bigR/2;
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
            tip.append("区域");
        }else if(result<=middleR*middleR){
            tip.append("中圆的");
            tip.append("区域");
        }
        else if(result<=bigR*bigR){
            tip.append("大圆的");
            tip.append("区域");
        }else {
            tip.append("作用区域以为的区域");
        }
        Toast.makeText(getContext(),tip,Toast.LENGTH_SHORT).show();;

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
