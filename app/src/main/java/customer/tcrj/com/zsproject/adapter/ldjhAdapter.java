package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView duty = helper.getView(R.id.type);
        ImageView im_icon = helper.getView(R.id.content);
        ImageView im_icon2 = helper.getView(R.id.content2);
        String fileType = item.getFileType();
        Log.e("TAG","fileType--"+fileType);
        if(".mp4".equals(fileType) || ".flv".equals(fileType)){
            duty.setText("视频");
            im_icon2.setVisibility(View.GONE);
            im_icon.setVisibility(View.VISIBLE);
            ShowImageUtils.LoadImage(mContext, item.getFileUrl(),im_icon);
        }else if(".jpeg".equals(fileType) || ".jpg".equals(fileType) || ".png".equals(fileType)){
            duty.setText("图片");
            im_icon2.setVisibility(View.GONE);
            im_icon.setVisibility(View.VISIBLE);
            ShowImageUtils.LoadImage(mContext, item.getFileUrl(),im_icon);
        }else if(".pdf".equals(fileType)){
            duty.setText("文档");
            im_icon2.setVisibility(View.VISIBLE);
            im_icon.setVisibility(View.GONE);
        }


//        ShowImageUtils.showImageView(mContext,item.getFileUrl(),im_icon,R.drawable.ic_placeholder);

    }

}
