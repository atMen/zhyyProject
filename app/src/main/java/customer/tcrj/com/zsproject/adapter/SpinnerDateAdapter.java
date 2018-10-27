package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.bean.projectInfo;

/**
 * Created by leict on 2016/1/22.
 */
public class SpinnerDateAdapter extends BaseAdapter {
    private List<projectInfo.ResultBean> itemList;
    public Context context;
    private LayoutInflater inflater;

    public SpinnerDateAdapter(Context context) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemList = new ArrayList<>();
    }

    public void setData(List<projectInfo.ResultBean> list) {
        clear();
        this.itemList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<projectInfo.ResultBean> list) {
        this.itemList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        if (!Utils.isStringEmpty(itemList)) {
            itemList.clear();
        }
    }

    @Override
    public int getCount() {
        if (itemList == null)
            return 0;
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ViewHolder holder = null;

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final projectInfo.ResultBean entity = itemList.get(position);
        if (entity == null)
            return null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_spinner, null);
            holder.tvItemTitle = (TextView) view.findViewById(R.id.item_spinner_name);
            view.setTag(holder);
            view.setId(position);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvItemTitle.setText(entity.getPName());
        return view;
    }

    private class ViewHolder {
        TextView tvItemTitle = null;
    }
}
