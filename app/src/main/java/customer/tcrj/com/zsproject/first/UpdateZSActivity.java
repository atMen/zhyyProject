package customer.tcrj.com.zsproject.first;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.DialogHelper;
import customer.tcrj.com.zsproject.bean.LoginInfo;
import customer.tcrj.com.zsproject.bean.MlListInfo;
import customer.tcrj.com.zsproject.bean.bean;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;

public class UpdateZSActivity extends Activity implements View.OnClickListener {


    EditText edt_bm;
    EditText edt_xh;
    EditText edt_work_datetime;
    Button btn_tj;
    ImageView iv_close;

    TextView name;
    cpInfo.DataBean.ContentBean cpinfo;
    private MyOkHttp mMyOkhttp;
    LoginInfo.ResultBean data;
    private String ProID;
    MlListInfo.ResultBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_zs);
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
        ProID = getIntent().getStringExtra("ProID");
        bean = (MlListInfo.ResultBean)getIntent().getSerializableExtra("bean");
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        data = (LoginInfo.ResultBean) ACache.get(this).getAsObject("userinfo");

        findview();

    }


    private void findview() {

        edt_work_datetime = findViewById(R.id.edt_work_datetime);
        edt_bm = findViewById(R.id.edt_bm);
        edt_xh = findViewById(R.id.edt_xh);

        btn_tj = findViewById(R.id.btn_tj);
        iv_close = findViewById(R.id.iv_close);
        name = findViewById(R.id.name);


        if(bean != null){
            //TODO:编辑信息时，将原信息展示
            name.setText("编辑目录");
            bm = bean.getMenuCode();
            edt_work_datetime.setText(bean.getMenuName());
            edt_xh.setText(bean.getSort()+"");
        }

        btn_tj.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }


    private void tofinish() {
        // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.putExtra("three", three); //将计算的值回传回去
            //通过intent对象返回结果，必须要调用一个setResult方法，
            //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
            setResult(2, intent);
            finish(); //结束当前的activity的生命周期
    }

    String three;
    String bm;
    String xh;
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_tj:
                // 获取用户计算后的结果
                three = edt_work_datetime.getText().toString();

                xh = edt_xh.getText().toString();
                if(three == null && "".equals(three)){

                    Toast.makeText(this, "请输入目录名称", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if(bm == null && "".equals(bm)){
//                    Toast.makeText(this, "请输入目录编码", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if(xh == null && "".equals(xh)){
                    Toast.makeText(this, "请输入目录序号", Toast.LENGTH_SHORT).show();
                    return;
                }
                addData(three);
                
                break;

            case R.id.iv_close:

                finish();
                break;

            default:
                break;
        }
    }

    private void addData(String three) {
        showLoadingDialog("正在创建目录...");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SpotBasisID", ProID);
            if(bean != null){
                jsonObject.put("ID", bean.getID());
                jsonObject.put("MenuCode", bm);
            }else {
                jsonObject.put("MenuCode", three);
            }


            jsonObject.put("MenuName", three);
            jsonObject.put("Sort", xh);
            jsonObject.put("StaffID", data.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(bean != null? ApiConstants.DW_ML_EDIT_API : ApiConstants.DW_ML_ADD_API)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<bean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, bean response) {
                        hideLoadingDialog();
                        Toast.makeText(UpdateZSActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

                        if(response.getStat() == 1){
                            tofinish();
                        }
                    }
         });
    }


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
