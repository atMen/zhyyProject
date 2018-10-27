package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.proListInfo;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class spjgAdapter extends BaseQuickAdapter<proListInfo.ResultBean.ItemsBean, BaseViewHolder> {

    private Context mContext;


    public spjgAdapter(@Nullable List<proListInfo.ResultBean.ItemsBean> data, Context context) {
        super(R.layout.item_cpinfo, data);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, proListInfo.ResultBean.ItemsBean item) {

        helper.setText(R.id.cpname, item.getSpotName());

        helper.setText(R.id.cptime,item.getBasisCode());

        helper.setText(R.id.type, "基础分类："+item.getClassName());
        helper.setText(R.id.location, "基础位置："+item.getBasisLocation());
        helper.setText(R.id.icon_num, "图片数量："+item.getImgCount()+" 张");


    }

}
