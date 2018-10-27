package customer.tcrj.com.zsproject;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.jaeger.library.StatusBarUtil;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import customer.tcrj.com.zsproject.Utils.AppManager;
import customer.tcrj.com.zsproject.Utils.DialogHelper;
import customer.tcrj.com.zsproject.first.FirstFragment;
import customer.tcrj.com.zsproject.mine.MineFragment;
import customer.tcrj.com.zsproject.resources.ResourcesFragment;
import customer.tcrj.com.zsproject.search.SearchFragment;
import customer.tcrj.com.zsproject.widget.BottomNavigationViewHelper;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TAG";
    private FragmentManager fragmentManager;
    private MineFragment mineFragment;
    private FirstFragment newsFragment;
    private SearchFragment settingFragment;
    private ResourcesFragment djFragment;

    private TextView poiat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 5,null);
        AppManager.getAppManager().addActivity(this);
        initview();
    }



    private void init() {
        showLoadingDialog("正在检测更新..");
        PgyUpdateManager.register(this,
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {

                        hideLoadingDialog();
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder( MainActivity.this)
                                .setTitle("更新")
                                .setMessage(appBean.getReleaseNote())
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(



                                                    DialogInterface dialog,
                                                    int which) {
                                                startDownloadTask(
                                                        MainActivity.this,
                                                        appBean.getDownloadURL());
                                            }
                                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        hideLoadingDialog();

//                        Toast.makeText(MainActivity.this, "当前为最新版本", Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void initview() {
        fragmentManager = getSupportFragmentManager();
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bnve);
        BottomNavigationViewHelper.disableShiftMode(navigationView);

        navigationView.setOnNavigationItemSelectedListener(this);

        setTabSelection(0);
    }

    private int num = -1;
    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (newsFragment == null) {
                    newsFragment = new FirstFragment();
                    transaction.add(R.id.contentContainer, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }

                break;
            case 2:
                if (djFragment == null) {
                    djFragment = new ResourcesFragment();
                    transaction.add(R.id.contentContainer, djFragment);
                } else {
                    transaction.show(djFragment);
                }
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.contentContainer, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;

            case 1:
                if (settingFragment == null) {
                    settingFragment = new SearchFragment();
                    transaction.add(R.id.contentContainer, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
        if (djFragment != null) {
            transaction.hide(djFragment);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_music:

                if(num != 0){
                    setTabSelection(0);
                }

                num = 0;
                return true;

            case R.id.menu_backup:

//              poiat.setVisibility(View.GONE);
                if(num != 1) {
                    setTabSelection(1);
                }
                num = 1;

                return true;

            case R.id.menu_friends:
                if(num != 2) {
                    setTabSelection(2);
                }
                num = 2;

                return true;

            case R.id.menu_set:
                if(num != 3) {
                    setTabSelection(3);
                }
                num = 3;

                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        AppManager.getAppManager().finishActivity(this); //从栈中移除
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息

            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
//          System.exit(0);
        }
    }

    //定义一个变量，
    //来标识是否退出
    private boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    protected Dialog mLoadingDialog = null;
    protected void showLoadingDialog(String str) {
        if (mLoadingDialog != null) {
            TextView tv = (TextView) mLoadingDialog.findViewById(R.id.tv_load_dialog);
            tv.setText(str);
            mLoadingDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}

