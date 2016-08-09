package com.cayden.collect.activity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.View;
import com.cayden.collect.R;
import com.cayden.collect.activity.base.BaseActivity;

public class TweenActivity extends BaseActivity {

    ImageView imageView;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_tween;
    }

    @Override
    protected void initView() {
        imageView=customFindViewById(R.id.imageView);
    }

    // 移动效果
    public void translateImpl(View v) {
        // XML文件
        Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.translate_demo);

        animation.setRepeatCount(Animation.INFINITE);//循环显示
        imageView.startAnimation(animation);

        /*
         * 第一种 imageView.setAnimation(animation); animation.start();
         */
        // 第二种

        // Java代码
        /*
         * TranslateAnimation translateAnimation = new TranslateAnimation(0,
         * 200, 0, 0); translateAnimation.setDuration(2000);
         * imageView.startAnimation(translateAnimation);
         */
    }

    /**
     * 旋转动画
     * @param v
     */
    public void rotatteImpl(View v){
        Animation animation=AnimationUtils.loadAnimation(this,
                R.anim.rotate_demo);
        imageView.startAnimation(animation);
    }

    /**
     * 缩放动画
     * @param v
     */
    public void scaleImpl(View v){
        Animation animation=AnimationUtils.loadAnimation(this,
                R.anim.scale_demo);
        imageView.startAnimation(animation);
    }

    /**
     * 透明动画
     * @param v
     */
    public void alphaImpl(View v){
        Animation animation=AnimationUtils.loadAnimation(this,
                R.anim.alpha_demo);
        imageView.startAnimation(animation);
    }

    public void setAll(View v){
        Animation animation=AnimationUtils.loadAnimation(this,
                R.anim.set_demo);
        imageView.startAnimation(animation);
    }
}
