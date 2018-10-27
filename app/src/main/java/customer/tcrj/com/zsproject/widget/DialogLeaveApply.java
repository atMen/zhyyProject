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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.adapter.LeaveApplyAdapter;
import customer.tcrj.com.zsproject.bean.zdCPLX;
import customer.tcrj.com.zsproject.net.ApiConstants;

/**
 * 类别
 * Created by leict on 2017/11/16.
 */

public class DialogLeaveApply extends Dialog implements DialogInterface{
    private RelativeLayout relativeMain;
    private TextView tvTitleName;
    private ImageView spinnerClose;
    private ListView listView;
    private ILeaveApplyCallBack callBack;
    private View view;
    private int mDuration = -1;
    private static Context mContext;
    private boolean isCancelable = true;
    private static DialogLeaveApply instance;
    private LeaveApplyAdapter adapter;
    private Context context;
    private MyOkHttp mMyOkhttp;
    private String token;
    private static String id = "-1";

    public DialogLeaveApply(Context context, String id) {
        super(context, R.style.dialog_untran);
        this.context = context;
        this.id = id;
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(context).getAsString("token");
        initView(context);
        getData();
    }

    public static DialogLeaveApply getInstance(Context context,String zdid) {


        if (!zdid.equals(id) || instance == null || !mContext.equals(context)) {
            synchronized (DialogLeaveApply.class) {
                if (!zdid.equals(id) || instance == null || !mContext.equals(context)) {
                    instance = new DialogLeaveApply(context,zdid);
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
                zdCPLX.DataBean entity = (zdCPLX.DataBean) adapter.getItem(position);
                if (callBack != null) {
                    callBack.setOnItemListener(entity.getName(),entity.getSort());
                    dismiss();
                }
            }
        });
    }


    public DialogLeaveApply setTitleName(int textResId) {
        tvTitleName.setText(textResId);
        return this;
    }

    public DialogLeaveApply setTitleName(CharSequence msg) {

        Log.e("TAG","setTitleName");
        tvTitleName.setText(msg);
        return this;
    }

    public DialogLeaveApply isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public DialogLeaveApply isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCancelable(cancelable);
        return this;
    }



    @Override
    public void dismiss() {
        super.dismiss();
    }



    private String citys[] = {"未开始", "施工中", "已完成"};
    public void getData() {

            List<zdCPLX.DataBean> itemList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                zdCPLX.DataBean leave = new zdCPLX.DataBean();
                leave.setName(citys[i]);
                leave.setSort(i+1);
                itemList.add(leave);
            }

            adapter = new LeaveApplyAdapter(context);
            adapter.setData(itemList);
            listView.setAdapter(adapter);
    }

    public void setOnItemClickListener(ILeaveApplyCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ILeaveApplyCallBack {
        void setOnItemListener(String typeName, int typeCode);
    }
}