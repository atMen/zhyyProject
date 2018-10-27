package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.bean.dwxxInfo;
import customer.tcrj.com.zsproject.bean.proListInfo;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class dwxxAdapter extends BaseQuickAdapter<dwxxInfo.ResultBean.ItemsBean, BaseViewHolder> {

    private Context mContext;


    public dwxxAdapter(@Nullable List<dwxxInfo.ResultBean.ItemsBean> data, Context context) {
        super(R.layout.item_cpinfo, data);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, dwxxInfo.ResultBean.ItemsBean item) {

        helper.setText(R.id.cpname, item.getSpotName());


        helper.setText(R.id.type, "点位分类："+item.getSpotClassName());
        helper.setText(R.id.location, "点位进度："+item.getSpotSchedule()+"%");


//        helper.setText(R.id.icon_num, "状态："+item.getImgCount()+" 张");




        int currentState = item.getState();

        if(currentState == 1){
            helper.setText(R.id.icon_num, "状态：未开始");
            return;
        }

        if(currentState == 2){
            helper.setText(R.id.icon_num, "状态：施工中");
            return;
        }

        if(currentState == 3){
            helper.setText(R.id.icon_num, "状态：已完成");
            return;
        }



    }

}
