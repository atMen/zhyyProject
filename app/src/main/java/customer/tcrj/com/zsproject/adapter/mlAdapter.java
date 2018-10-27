package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.bean.MlListInfo;
import customer.tcrj.com.zsproject.bean.proListInfo;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class mlAdapter extends BaseQuickAdapter<MlListInfo.ResultBean, BaseViewHolder> {
    public static final int LAST_POSITION = -1;
    private Context mContext;


    public mlAdapter(@Nullable List<MlListInfo.ResultBean> data, Context context) {
        super(R.layout.item_list, data);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MlListInfo.ResultBean item) {

        helper.setText(R.id.tv_listname, item.getMenuName());

        TextView view = (TextView) helper.getView(R.id.tv_dele);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.OnItemClick(item,helper.getPosition());
                }
            }
        });

        TextView editview = (TextView) helper.getView(R.id.tv_edit);
        editview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemEditClickListener != null){
                    onItemEditClickListener.OnItemEditClick(item,helper.getPosition());
                }
            }
        });


    }


    public void remove(int position){
        Log.e("remove","position:"+position);
        if (position == LAST_POSITION && getItemCount()>0) {
            position = getItemCount() - 1;
        }
        if (position > LAST_POSITION && position < getItemCount()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {  //定义接口，实现Recyclerview点击事件
        void OnItemClick(MlListInfo.ResultBean bean, int Position);
    }


    public void setOnItemRemoveClickListener(OnItemClickListener onItemClickListener) {//实现点击
        this.onItemClickListener = onItemClickListener;
    }


    private OnItemEditClickListener onItemEditClickListener;

    public interface OnItemEditClickListener {  //定义接口，实现Recyclerview点击事件
        void OnItemEditClick(MlListInfo.ResultBean bean, int Position);
    }


    public void setOnItemEditClickListener(OnItemEditClickListener onItemEditClickListener) {//实现点击
        this.onItemEditClickListener = onItemEditClickListener;
    }

}
