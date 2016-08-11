package com.cayden.collect.activity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.View;
import com.cayden.collect.R;
import com.cayden.collect.activity.base.BaseActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import rx.Subscriber;

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

    private void testLogin() {
        final BmobUser bu2 = new BmobUser();
        bu2.setUsername("smile");
        bu2.setPassword("123456");
        //login回调
//		bu2.login(new SaveListener<BmobUser>() {
//
//			@Override
//			public void done(BmobUser bmobUser, BmobException e) {
//				if(e==null){
//					toast(bu2.getUsername() + "登陆成功");
//					testGetCurrentUser();
//				}else{
//					loge(e);
//				}
//			}
//		});
        //新增加的Observable
        bu2.loginObservable(BmobUser.class).subscribe(new Subscriber<BmobUser>() {
            @Override
            public void onCompleted() {
                log("----onCompleted----");
            }

            @Override
            public void onError(Throwable e) {
                loge(new BmobException(e));
            }

            @Override
            public void onNext(BmobUser bmobUser) {
                toast(bmobUser.getUsername() + "登陆成功");

            }
        });
    }

    // 移动效果
    public void translateImpl(View v) {
        testLogin();
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
