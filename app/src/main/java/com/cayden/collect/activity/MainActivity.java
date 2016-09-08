package com.cayden.collect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.cayden.collect.R;
import com.cayden.collect.activity.base.BaseActivity;
import com.cayden.collect.activity.mvp.MvpActivity;
import com.cayden.collect.fragment.BlogFragment;
import com.cayden.collect.fragment.CsdnFragment;
import com.cayden.collect.fragment.FrozenuiFragment;
import com.cayden.collect.fragment.HomeFragment;
import com.cayden.collect.fragment.MessengerFragment;
import com.cayden.collect.fragment.OkHttpFragment;
import com.cayden.collect.fragment.base.WebViewFragment;
import com.cayden.collect.memo.tasks.TasksActivity;
import com.cayden.collect.utils.ViewUtils;
import com.jiongbull.jlog.JLog;

/**
 *Created by cuiran on 16/5/10.
 */
public class MainActivity extends BaseActivity {
    private static String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;//侧边菜单视图
    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private Toolbar mToolbar;
    private NavigationView mNavigationView;//侧边菜单项

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    private int mCurrentSelectMenuIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the presenter

        if (savedInstanceState == null)
            Log.i(TAG, "NULL mCurrentSelectMenuIndex:" + mCurrentSelectMenuIndex);
        else {
            mCurrentSelectMenuIndex = savedInstanceState.getInt("currentSelectMenuIndex", 0);
            Log.i(TAG, "NOT NULL mCurrentSelectMenuIndex:" + mCurrentSelectMenuIndex);

        }
       JLog.d("测试日志框架");

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
    }


    @Override
    protected void initView() {
        mToolbar = customFindViewById(R.id.toolbar);
        mDrawerLayout = customFindViewById(R.id.drawer_layout);
        mNavigationView = customFindViewById(R.id.navigation_view);
        mToolbar.setTitle("首页");
        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolbar);
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果。
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setNavigationViewItemClickListener();
        initDefaultFragment();

        JLog.d("initView");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentSelectMenuIndex", mCurrentSelectMenuIndex);
        JLog.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        JLog.i(TAG,"onRestoreInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JLog.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        JLog.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.i(TAG, "onDestroy");
    }

    //init the default checked fragment
    private void initDefaultFragment() {
        JLog.i(TAG, "initDefaultFragment");
        mCurrentFragment = ViewUtils.createFragment(HomeFragment.class);
        mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
        mNavigationView.getMenu().getItem(mCurrentSelectMenuIndex).setChecked(true);
    }

    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        mToolbar.setTitle("首页");
                        switchFragment(HomeFragment.class);
                        break;
                    case R.id.navigation_item_blog:
                        mToolbar.setTitle("我的简书");
                        switchFragment(BlogFragment.class);
                        break;
                    case R.id.navigation_item_csdn:
                        mToolbar.setTitle("我的CSDN");
                        switchFragment(CsdnFragment.class);
                        break;
                    case R.id.navigation_item_okhttp:
                        mToolbar.setTitle(R.string.drawer_title_okhttp);
                        switchFragment(OkHttpFragment.class);
                        break;
                    case R.id.navigation_item_mvp:
                        Intent intent0 =
                                new Intent(MainActivity.this, MvpActivity.class);
//                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent0);

                        break;
                    case R.id.navigation_item_memo:
                        Intent intent =
                                        new Intent(MainActivity.this, TasksActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                        break;
                    case R.id.navigation_item_messenger:
                        mToolbar.setTitle(R.string.drawer_title_messenger);
                        switchFragment(MessengerFragment.class);
                        break;
                    case R.id.navigation_item_frozenui:
                        mToolbar.setTitle(R.string.drawer_title_frozenui);
                        switchFragment(FrozenuiFragment.class);
                        break;
                    case R.id.navigation_item_record:
                        Intent intent1 =
                                new Intent(MainActivity.this, MediaProjectionActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);

                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    //切换Fragment
    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            JLog.i(TAG, "Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            JLog.i(TAG, "Not Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }

    private long lastBackKeyDownTick = 0;
    public static final long MAX_DOUBLE_BACK_DURATION = 1500;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {//当前抽屉是打开的，则关闭
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }

        if (mCurrentFragment instanceof WebViewFragment) {//如果当前的Fragment是WebViewFragment 则监听返回事件
            WebViewFragment webViewFragment = (WebViewFragment) mCurrentFragment;
            if (webViewFragment.canGoBack()) {
                webViewFragment.goBack();
                return;
            }
        }

        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            Snackbar.make(mDrawerLayout, "再按一次退出", Snackbar.LENGTH_SHORT).show();
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
            System.exit(0);
        }
    }


}
