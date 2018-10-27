package customer.tcrj.com.zsproject.first;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.LoginInfo;
import customer.tcrj.com.zsproject.bean.SpotBasisInfo;
import customer.tcrj.com.zsproject.bean.bean;
import customer.tcrj.com.zsproject.bean.dwInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.DialogLeaveApply;
import customer.tcrj.com.zsproject.widget.DialogSpotClass;

public class DwjcAddCPinfoActivity extends BaseActivity {

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

    @BindView(R.id.edt_sjcc)
    EditText edt_sjcc;
    @BindView(R.id.edt_sjfl)
    EditText edt_sjfl;
    @BindView(R.id.edt_shijichicun)
    EditText edt_shijichicun;
    @BindView(R.id.edt_shijifangl)
    EditText edt_shijifangl;


    @BindView(R.id.tv_state)
    TextView tv_state;

    @BindView(R.id.btn_submit_daily)
    Button btn_submit_daily;

    SpotBasisInfo dwinfo;

    //点位分类
    private int SpotClass = -1;
//    //状态
//    private int SpotState = -1;

    private String SpotClassName;
    private MyOkHttp mMyOkhttp;

    private LoginInfo.ResultBean data;
    @Override
    protected int setLayout() {
        return R.layout.activity_add_dwjcinfo;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        data = (LoginInfo.ResultBean) ACache.get(this).getAsObject("userinfo");

        txtTitle.setText("基础信息编辑");
        dwinfo = (SpotBasisInfo) getIntent().getSerializableExtra("dwinfo");
        SpotClassName = getIntent().getStringExtra("SpotClassName");

        SpotBasisInfo.ResultBean result = dwinfo.getResult();

        SpotBasisInfo.ResultBean.SpottingBean SpottingInfo = result.getSpotting();
        SpotBasisInfo.ResultBean.SpottingBean.ProjectInfoBean projectInfo = SpottingInfo.getProjectInfo();

        tv_work_naturejob.setText(projectInfo.getPName());
        tv_dwfl.setText(SpottingInfo.getSpotName());//点位名称

        edt_work_overtime.setText(result.getBasisCode());
        edt_cpbcggdw.setText(result.getBasisLocation());
        edt_cpbzq.setText(result.getSoilClass());
        edt_today_plan.setText(result.getRemark());


        edt_sjcc.setText(result.getDesignSize());
        edt_sjfl.setText(result.getDesignCubage());
        edt_shijichicun.setText(result.getActualSize());
        edt_shijifangl.setText(result.getActualCubage());

        tv_state.setText(result.getBasisClass()+"");//基础分类
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
                final DialogSpotClass spinner = DialogSpotClass.getInstance(this,ApiConstants.DW_JC_TYPE_API);
                spinner.setTitleName("请选择基础分类");
                spinner.setOnItemClickListener(new DialogSpotClass.ILeaveApplyCallBack() {
                    @Override
                    public void setOnItemListener(String typeName,int typeCode) {
                        tv_state.setText(typeName);
                        SpotClass = typeCode;
                        Log.e("TAG","typeName:"+typeName+"----typeCode:"+typeCode);
                    }
                });
                spinner.show();
                break;


            case R.id.btn_submit_daily:
                UpLoadData();

                break;


            default:
                break;
        }

    }


    //获取网络数据
    private void UpLoadData() {

        JSONObject jsonObject = new JSONObject();

        try {
            SpotBasisInfo.ResultBean result = dwinfo.getResult();

            jsonObject.put("ID", result.getID());

            jsonObject.put("BasisLocation", edt_cpbcggdw.getText().toString());
            jsonObject.put("BasisCode", edt_work_overtime.getText().toString());
            jsonObject.put("SoilClass", edt_cpbzq.getText().toString());

            jsonObject.put("BasisClass", SpotClass ==  -1? result.getBasisClass() : SpotClass);

            jsonObject.put("Remark", edt_today_plan.getText().toString());
            jsonObject.put("DesignSize", edt_sjcc.getText().toString());
            jsonObject.put("DesignCubage", edt_sjfl.getText().toString());
            jsonObject.put("ActualSize", edt_shijichicun.getText().toString());
            jsonObject.put("ActualCubage", edt_shijifangl.getText().toString());

            jsonObject.put("UpdateStaffID", data.getID());
            jsonObject.put("UpdateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.DW_XQ_EDIT_API)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<bean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Toast.makeText(DwjcAddCPinfoActivity.this, error_msg, Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, bean response) {

                        Toast.makeText(DwjcAddCPinfoActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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