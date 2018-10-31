package customer.tcrj.com.zsproject.first;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.rl_updata)
    RelativeLayout rl_updata;
    @BindView(R.id.btnback)
    ImageView btnback;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setView() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 5,null);
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.rl_updata,R.id.btnback})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:
                finish();
                break;

            case R.id.rl_updata:
                init();
                break;

            default:
                break;
        }
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
                        new AlertDialog.Builder( SettingActivity.this)
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
                                                        SettingActivity.this,
                                                        appBean.getDownloadURL());
                                            }
                                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        hideLoadingDialog();

                        Toast.makeText(SettingActivity.this, "当前为最新版本", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
