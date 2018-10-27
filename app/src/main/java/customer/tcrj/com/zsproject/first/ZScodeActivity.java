package customer.tcrj.com.zsproject.first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;

public class ZScodeActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected int setLayout() {
        return R.layout.activity_zscode;
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setData() {

    }
}
