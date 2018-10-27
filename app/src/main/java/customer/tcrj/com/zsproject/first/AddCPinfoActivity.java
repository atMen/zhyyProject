package customer.tcrj.com.zsproject.first;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.adapter.GridImageAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.LoginInfo;
import customer.tcrj.com.zsproject.bean.bean;
import customer.tcrj.com.zsproject.bean.dwInfo;
import customer.tcrj.com.zsproject.bean.proListInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.DialogLeaveApply;
import customer.tcrj.com.zsproject.widget.DialogSpotClass;
import customer.tcrj.com.zsproject.widget.FullyGridLayoutManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AddCPinfoActivity extends BaseActivity {

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.layout_cptype)
    LinearLayout layout_cptype;
    @BindView(R.id.ll_dwfl)
    LinearLayout ll_dwfl;

    @BindView(R.id.tv_work_naturejob)
    TextView tv_work_naturejob;
    @BindView(R.id.tv_dwfl)
    TextView tv_dwfl;
    @BindView(R.id.edt_work_overtime)
    EditText edt_work_overtime;
    @BindView(R.id.edt_cpbcggdw)
    EditText edt_cpbcggdw;
    @BindView(R.id.edt_cpbzq)
    EditText edt_cpbzq;
    @BindView(R.id.edt_today_plan)
    EditText edt_today_plan;

    @BindView(R.id.tv_state)
    TextView tv_state;

    @BindView(R.id.btn_submit_daily)
    Button btn_submit_daily;

    dwInfo dwinfo;

    //点位分类
    private int SpotClass = -1;
    //状态
    private int SpotState = -1;

    private String SpotClassName;
    private MyOkHttp mMyOkhttp;

    private LoginInfo.ResultBean data;
    @Override
    protected int setLayout() {
        return R.layout.activity_add_cpinfo;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        data = (LoginInfo.ResultBean) ACache.get(this).getAsObject("userinfo");

        txtTitle.setText("点位信息编辑");
        dwinfo = (dwInfo) getIntent().getSerializableExtra("dwinfo");
        SpotClassName = getIntent().getStringExtra("SpotClassName");

        dwInfo.ResultBean result = dwinfo.getResult();
        dwInfo.ResultBean.ProjectInfoBean projectInfo = result.getProjectInfo();
        tv_work_naturejob.setText(projectInfo.getPName());
        tv_dwfl.setText(SpotClassName);
        edt_work_overtime.setText(result.getSpotName());
        edt_cpbcggdw.setText(result.getSpotCode());
        edt_cpbzq.setText(result.getSpotSchedule()+"");
        edt_today_plan.setText(result.getRemark());


        int state = result.getState();
        if(state == 1){
            tv_state.setText("未开始");
        }else if(state == 2){
            tv_state.setText("施工中");
        }else {
            tv_state.setText("已完成");
        }

    }


    @Override
    protected void setData() {

    }




    @OnClick({R.id.btnback,R.id.layout_cptype,R.id.btn_submit_daily,R.id.ll_dwfl})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnback:

                finish();

                break;

            case R.id.layout_cptype:
                final DialogLeaveApply spinnerzsfs = DialogLeaveApply.getInstance(this,"106");
                spinnerzsfs.setTitleName("请选择状态");
                spinnerzsfs.setOnItemClickListener(new DialogLeaveApply.ILeaveApplyCallBack() {
                    @Override
                    public void setOnItemListener(String typeName,int typeCode) {
                        tv_state.setText(typeName);
                        SpotState = typeCode;
                        Log.e("TAG","typeName:"+typeName+"----typeCode:"+typeCode);
                    }
                });
                spinnerzsfs.show();
                break;

            case R.id.ll_dwfl:
                final DialogSpotClass spinner = DialogSpotClass.getInstance(this,ApiConstants.XM_DW_TYPE_API);
                spinner.setTitleName("请选择点位分类");
                spinner.setOnItemClickListener(new DialogSpotClass.ILeaveApplyCallBack() {
                    @Override
                    public void setOnItemListener(String typeName,int typeCode) {
                        tv_dwfl.setText(typeName);
                        SpotClass = typeCode;
                        Log.e("TAG","typeName:"+typeName+"----typeCode:"+typeCode);
                    }
                });
                spinner.show();
                break;


            case R.id.btn_submit_daily:
                UpLoadData();

                break;
//
//            case R.id.ll_zsmsq:
//                toClass(mContext,MainActivity.class);//追溯码申请
//                break;
//
//            case R.id.ll_spjg:
//                toClass(mContext,MainActivity.class);//审批结果查询
//                break;

            default:
                break;
        }

    }


    //获取网络数据
    private void UpLoadData() {

        JSONObject jsonObject = new JSONObject();

        try {
            dwInfo.ResultBean result = dwinfo.getResult();

            jsonObject.put("ID", result.getID());
            jsonObject.put("SpotCode", edt_cpbcggdw.getText().toString());
            jsonObject.put("SpotName", edt_work_overtime.getText().toString());
            jsonObject.put("SpotClass", SpotClass ==  -1? result.getSpotClass() : SpotClass);
            jsonObject.put("SpotSchedule", Integer.parseInt(edt_cpbzq.getText().toString()));//edt_cpbzq.getText().toString()
            jsonObject.put("State", SpotState == -1? result.getState() : SpotState);
            jsonObject.put("Remark", edt_today_plan.getText().toString());
            jsonObject.put("UpdateStaffID", data.getID());
            jsonObject.put("UpdateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.XM_DW_INFO_EDIT_API)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<bean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Toast.makeText(AddCPinfoActivity.this, error_msg, Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, bean response) {

                        Toast.makeText(AddCPinfoActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        if (response.getStat() == 1) {
                            Intent i = new Intent();
                            setResult(RESULT_OK, i);
                            finish();
                        }
                    }
                });

    }
}