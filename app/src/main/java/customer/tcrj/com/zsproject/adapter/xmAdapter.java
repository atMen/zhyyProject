package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.bean.XmListInfo;
import customer.tcrj.com.zsproject.bean.proListInfo;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class xmAdapter extends BaseQuickAdapter<XmListInfo.ResultBean.ItemsBean, BaseViewHolder> {

    private Context mContext;


    public xmAdapter(@Nullable List<XmListInfo.ResultBean.ItemsBean> data, Context context) {
        super(R.layout.item_xm, data);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, XmListInfo.ResultBean.ItemsBean item) {

        int currentState = item.getCurrentState();
        helper.setText(R.id.cpname, item.getPName());

        helper.setText(R.id.cptime,item.getPCode());


        helper.setText(R.id.type, "项目进度："+(item.getSchedule() != 0? item.getSchedule():item.getComputeSchedule())+" %");

        if(currentState == 1){
            helper.setText(R.id.location, "项目状态：售前");
            return;
        }

        if(currentState == 3){
            helper.setText(R.id.location, "项目状态：需求");
            return;
        }

        if(currentState == 4){
            helper.setText(R.id.location, "项目状态：设计");
            return;
        }

        if(currentState == 5){
            helper.setText(R.id.location, "项目状态：开发");
            return;
        }

        if(currentState == 6){
            helper.setText(R.id.location, "项目状态：测试");
            return;
        }

        if(currentState == 7){
            helper.setText(R.id.location, "项目状态：实施");
            return;
        }

        if(currentState == 8){
            helper.setText(R.id.location, "项目状态：待验收");
            return;
        }

        if(currentState == 9){
            helper.setText(R.id.location, "项目状态：已验收");
            return;
        }

        if(currentState == 10){
            helper.setText(R.id.location, "项目状态：暂不维护");
            return;
        }

        if(currentState == 11){
            helper.setText(R.id.location, "项目状态：不维护");
            return;
        }



    }

}
