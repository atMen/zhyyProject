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
import customer.tcrj.com.zsproject.bean.SpotBasisInfo;
import customer.tcrj.com.zsproject.bean.dwInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;

public class DwjcListInfoActivity extends BaseActivity {

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

    @BindView(R.id.tv07)
    TextView tv07;
    @BindView(R.id.tv08)
    TextView tv08;
    @BindView(R.id.tv09)
    TextView tv09;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv11)
    TextView tv11;



    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnback)
    ImageView btnback;

    private MyOkHttp mMyOkhttp;
    String proID;
    String SpotClassName;

    @Override
    protected int setLayout() {
        return R.layout.activity_dwjclist_info;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        proID = getIntent().getStringExtra("ProID");
        SpotClassName = getIntent().getStringExtra("SpotClassName");
        txtTitle.setText("点位基础信息详情");
    }

    @Override
    protected void setData() {

        getData(proID);

    }

    //获取网络数据
    private void getData(String cpid) {

        showLoadingDialog();

        mMyOkhttp.post()
                .url(ApiConstants.DW_XQ_API)
                .addParam("", cpid)
                .enqueue(new GsonResponseHandler<SpotBasisInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, SpotBasisInfo response) {


                        hideLoadingDialog();
                        if(response.getStat() == 1){
                            dwinfo = response;
                            setCpInfo(response);

                        }else{

                            Toast.makeText(DwjcListInfoActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    SpotBasisInfo dwinfo;
    private void setCpInfo(SpotBasisInfo cpinfo) {
        SpotBasisInfo.ResultBean result = cpinfo.getResult();
        SpotBasisInfo.ResultBean.SpottingBean spotting = cpinfo.getResult().getSpotting();
        if(spotting != null){
            SpotBasisInfo.ResultBean.SpottingBean.ProjectInfoBean projectInfo = spotting.getProjectInfo();
            cpname.setText("项目名称：" + projectInfo.getPName());
            tv01.setText(spotting.getSpotName());
        }


        if (result != null) {

            //TODO:服务器返回基础分类名称
            tv02.setText(result.getBasisClass()+"");
            tv03.setText(result.getBasisCode());
            tv04.setText(result.getBasisLocation());
            tv06.setText(result.getRemark());
            tv07.setText(result.getSoilClass());
            tv08.setText(result.getDesignSize());
            tv09.setText(result.getDesignCubage());
            tv10.setText(result.getActualSize());
            tv11.setText(result.getActualCubage());

        }
    }


    @OnClick({R.id.btnback,R.id.btn_edit,R.id.btn_icon})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:

                finish();
                break;

            case R.id.btn_edit:

                Bundle bundle = new Bundle();
                bundle.putSerializable("dwinfo",dwinfo);
                bundle.putString("SpotClassName",SpotClassName);
                toClass(DwjcListInfoActivity.this,DwjcAddCPinfoActivity.class,bundle,CPREQUESTCODE);//产品信息录入
                break;
            case R.id.btn_icon:
                Bundle bundle1 = new Bundle();
                bundle1.putString("ProID",proID+"");
                toClass(this,ListActivity.class,bundle1);
                break;

//            case R.id.num:
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("dwinfo",dwinfo);
//                bundle.putString("SpotClassName",SpotClassName);
//                toClass(DwjcListInfoActivity.this,AddCPinfoActivity.class,bundle,CPREQUESTCODE);//产品信息录入
//                break;

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


