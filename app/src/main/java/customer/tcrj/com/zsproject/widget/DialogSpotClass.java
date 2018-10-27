package customer.tcrj.com.zsproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.ArrayList;
import java.util.List;

import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.adapter.LeaveApplyAdapter;
import customer.tcrj.com.zsproject.adapter.SpotClassAdapter;
import customer.tcrj.com.zsproject.adapter.ZtListDropDownAdapter;
import customer.tcrj.com.zsproject.bean.projectInfo;
import customer.tcrj.com.zsproject.bean.zdCPLX;
import customer.tcrj.com.zsproject.first.dwinfoActivity;
import customer.tcrj.com.zsproject.net.ApiConstants;

/**
 * 类别
 * Created by leict on 2017/11/16.
 */

public class DialogSpotClass extends Dialog implements DialogInterface{
    private RelativeLayout relativeMain;
    private TextView tvTitleName;
    private ImageView spinnerClose;
    private ListView listView;
    private ILeaveApplyCallBack callBack;
    private View view;
    private int mDuration = -1;
    private static Context mContext;
    private boolean isCancelable = true;
    private static DialogSpotClass instance;
    private SpotClassAdapter adapter;
    private Context context;
    private MyOkHttp mMyOkhttp;
    private String token;
    private static String id = "-1";

    public DialogSpotClass(Context context, String id) {
        super(context, R.style.dialog_untran);
        this.context = context;
        this.id = id;
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(context).getAsString("token");
        initView(context);
        getTypeData();
    }

    public static DialogSpotClass getInstance(Context context, String zdid) {


        if (!zdid.equals(id) || instance == null || !mContext.equals(context)) {
            synchronized (DialogSpotClass.class) {
                if (!zdid.equals(id) || instance == null || !mContext.equals(context)) {
                    instance = new DialogSpotClass(context,zdid);
                }
            }
        }
        mContext = context;

        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","onCreate");
        WindowManager.LayoutParams p = getWindow().getAttributes();  // 获取对话框当前的参数值
        p.width = (int) (Utils.getWidth(mContext) * 0.8);            // 宽度设置为屏幕的0.8
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
    }

    private void initView(Context context) {
        view = View.inflate(context, R.layout.dialog_spinner_leaveapply, null);
        relativeMain = (RelativeLayout) view.findViewById(R.id.relative_main);
        tvTitleName = (TextView) view.findViewById(R.id.spinner_title);
        spinnerClose = (ImageView) view.findViewById(R.id.spinner_close);
        listView = (ListView) view.findViewById(R.id.spinner_listview);
        this.setCanceledOnTouchOutside(false);
        setContentView(view);

        spinnerClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectItem(position);
                projectInfo.ResultBean entity = (projectInfo.ResultBean) adapter.getItem(position);
                if (callBack != null) {
                    callBack.setOnItemListener(entity.getName(),entity.getID());
                    dismiss();
                }
            }
        });
    }


    public DialogSpotClass setTitleName(int textResId) {
        tvTitleName.setText(textResId);
        return this;
    }

    public DialogSpotClass setTitleName(CharSequence msg) {

        Log.e("TAG","setTitleName");
        tvTitleName.setText(msg);
        return this;
    }

    public DialogSpotClass isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public DialogSpotClass isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCancelable(cancelable);
        return this;
    }



    @Override
    public void dismiss() {
        super.dismiss();
    }




    /**
     * 获取基础分类
     */
    private void getTypeData() {

        mMyOkhttp.post()
                .url(id)
                .enqueue(new GsonResponseHandler<projectInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, projectInfo response) {

                        if(response.getStat() == 1){
                            List<projectInfo.ResultBean> dataList = response.getResult();
                            adapter = new SpotClassAdapter(context);
                            adapter.setData(dataList);
                            listView.setAdapter(adapter);
                        }else{
                            Log.e("TAG","加载错误");
                        }
                    }
                });
    }

    public void setOnItemClickListener(ILeaveApplyCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ILeaveApplyCallBack {
        void setOnItemListener(String typeName, int typeCode);
    }
}