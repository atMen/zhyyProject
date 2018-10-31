package customer.tcrj.com.zsproject.first;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;


import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.dwInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;

public class CPListInfoActivity extends BaseActivity {

    private static final int CPREQUESTCODE = 001;
    @BindView(R.id.cpname)
    TextView cpname;
    @BindView(R.id.cptime)
    TextView cptime;
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.tv03)
    TextView tv03;
    @BindView(R.id.tv04)
    TextView tv04;

    @BindView(R.id.tv06)
    TextView tv06;


    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnback)
    ImageView btnback;

    private MyOkHttp mMyOkhttp;
    String proID;
    String SpotClassName;

    @Override
    protected int setLayout() {
        return R.layout.activity_cplist_info;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        proID = getIntent().getStringExtra("ProID");
        SpotClassName = getIntent().getStringExtra("SpotClassName");

        txtTitle.setText("点位信息详情");
    }

    @Override
    protected void setData() {
//        num.setVisibility(View.VISIBLE);
//        num.setText("编辑");
        getData(proID);

    }

    //获取网络数据
    private void getData(String cpid) {

        showLoadingDialog();

        mMyOkhttp.post()
                .url(ApiConstants.XM_DW_INFO_API)
                .addParam("", cpid)
                .enqueue(new GsonResponseHandler<dwInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, dwInfo response) {


                        hideLoadingDialog();
                        if(response.getStat() == 1){
                            dwinfo = response;
                            setCpInfo(response);

                        }else{

                            Toast.makeText(CPListInfoActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    dwInfo dwinfo;
    private void setCpInfo(dwInfo cpinfo) {

        dwInfo.ResultBean.ModelBean.ProjectInfoBean result = cpinfo.getResult().getModel().getProjectInfo();
        dwInfo.ResultBean.ModelBean result1 = cpinfo.getResult().getModel();
        if(result != null){
            cpname.setText("项目名称：" + result.getPName());
        }

        if (result1 != null) {
            int state = result1.getState();
            if(state == 1){
                cptime.setText("未开始");
            }else if(state == 2){
                cptime.setText("施工中");
            }else {
                cptime.setText("已完成");
            }

            tv01.setText(result1.getSpotName());
            tv02.setText(cpinfo.getResult().getClassname());
            tv03.setText(result1.getSpotCode()+"");
            tv04.setText(result1.getSpotSchedule()+" %");
            tv06.setText(result1.getRemark());
        }
    }


    @OnClick({R.id.btnback, R.id.num,R.id.btn_edit,R.id.btn_icon})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:

                finish();
                break;

            case R.id.num:
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("dwinfo",dwinfo);
//                bundle.putString("SpotClassName",SpotClassName);
//                toClass(CPListInfoActivity.this,AddCPinfoActivity.class,bundle,CPREQUESTCODE);//产品信息录入
                break;

            case R.id.btn_edit:

                Bundle bundle = new Bundle();
                bundle.putSerializable("dwinfo",dwinfo);
                bundle.putString("SpotClassName",SpotClassName);
                toClass(CPListInfoActivity.this,AddCPinfoActivity.class,bundle,CPREQUESTCODE);//产品信息录入
                break;
            case R.id.btn_icon:
                Bundle bundle1 = new Bundle();
                bundle1.putString("ProID",proID+"");
                toClass(this,ListActivity.class,bundle1);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case CPREQUESTCODE:
                    getData(proID);
                    break;

                default:
                    break;

            }
        }
    }


}


