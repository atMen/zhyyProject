package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import customer.tcrj.com.zsproject.bean.iconInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class ldjhAdapter extends BaseQuickAdapter<iconInfo.ResultBean, BaseViewHolder> {
    private Context mContext;

    public ldjhAdapter(@Nullable List<iconInfo.ResultBean> data, Context context) {
        super(R.layout.item_ldjh, data);
        this.mContext = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper,iconInfo.ResultBean item) {
//        TextView name = helper.getView(R.id.name);
//        TextView time = helper.getView(R.id.time);
//        TextView sex = helper.getView(R.id.sex);
//        TextView duty = helper.getView(R.id.duty);
        ImageView im_icon = helper.getView(R.id.content);
        ShowImageUtils.LoadImage(mContext, item.getFileUrl(),im_icon);
//        ShowImageUtils.showImageView(mContext,item.getFileUrl(),im_icon,R.drawable.ic_placeholder);



    }


}
