package customer.tcrj.com.zsproject.first;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.adapter.spjgAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.proListInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class CPinfoActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.edt_search_result)
    EditText edt_search_result;

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    private MyOkHttp mMyOkhttp;
    private spjgAdapter detailAdapter;
    private boolean canPull = true;
    private int pageNum = 1;
    private List<proListInfo.ResultBean.ItemsBean> beanList;


    @Override
    protected int setLayout() {
        return R.layout.activity_cpinfo;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        initview();
    }

    private void initview() {
        txtTitle.setText("点位基础信息");

        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                if(!canPull){
                    return false;
                }
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                String s = edt_search_result.getText().toString();
                getData(pageNum,s);

            }
        });
        beanList = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(CPinfoActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(detailAdapter = new spjgAdapter(beanList, CPinfoActivity.this));
        detailAdapter.setPreLoadNumber(1);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnItemClickListener(this);

        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e("TAG","点击重新加载数据");
                String s = edt_search_result.getText().toString();
                getData(pageNum,s);
            }
        }, mRecyclerView);
    }

    //获取网络数据
    private void getData(final int num,String s) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("pageIndex", num+"");
//            jsonObject.put("size", "30");
//            jsonObject.put("token", token);
//            jsonObject.put("cpmc", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.DW_LIST_API)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<proListInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);

                        if(num > 1){
                            loadMoreData(null,true);
                        }else{
                            loadData(null,true);

                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, proListInfo response) {

                        hideLoadingDialog();
                        if(response.getStat() == 1){

                            if(num > 1){//上拉加载
                                Log.e("TAG","上拉加载更多数据");
                                loadMoreData(response,false);
                            }else{//下拉刷新
                                loadData(response.getResult().getItems(),false);
                            }

                        }else{

                            Log.e("TAG","加载错误");
                        }
                    }
                });

    }

    //上拉加载更多数据
    private void loadMoreData(proListInfo response, boolean isError) {
        Log.e("TAG","loadMoreData");

        List<proListInfo.ResultBean.ItemsBean> result = response.getResult().getItems();

        if (response == null) {
            if(isError){
                detailAdapter.loadMoreFail();
                Toast.makeText(this, getResources().getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
            }else{
                detailAdapter.loadMoreFail();
            }

        } else {
            if(result == null || result.size() == 0){//没有更多数据
                detailAdapter.loadMoreFail();
            }else{
                pageNum++;
                detailAdapter.addData(result);
                detailAdapter.loadMoreComplete();
            }

        }

    }

    //下拉刷新
    private void loadData(List<proListInfo.ResultBean.ItemsBean> response,boolean isError) {

        if (response == null  || response.size() <= 0) {
            if(mPtrFrameLayout != null){
                mPtrFrameLayout.refreshComplete();
            }
            if(isError){
                showFaild();
            }else{
                showEmptyView();
            }
            canPull = false;

        } else {

            canPull = true;
            pageNum++;
            detailAdapter.setNewData(response);
            if(mPtrFrameLayout != null){
                mPtrFrameLayout.refreshComplete();
            }
            showSuccess();
            disableLoadMoreIfNotFullPage(mRecyclerView,response.size());
        }
    }

    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView, final int size) {
        detailAdapter.setEnableLoadMore(false);
        if (recyclerView == null) return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) return;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;

            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //要等到列表显示出来才可以去获取：findLastCompletelyVisibleItemPosition
                    if ((linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1) != size) {
                        detailAdapter.setEnableLoadMore(true);
                        Log.e("TAG","setEnableLoadMore(true)");
                    }

                    Log.e("TAG","测试："+(linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1));
                }
            }, 1000);


        }
    }

    @Override
    protected void setData() {
        getData(pageNum,"");
    }

    @OnClick({R.id.btnback,R.id.tv_search})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:
                finish();
                break;
            case R.id.tv_search:
                showLoadingDialog("正在搜索..");
                String s = edt_search_result.getText().toString();
                getData(1,s);

                break;
            default:
                break;

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        proListInfo.ResultBean.ItemsBean item = (proListInfo.ResultBean.ItemsBean) adapter.getItem(position);
        int id = item.getID();
        Bundle bundle = new Bundle();
        bundle.putString("ProID",id+"");
        toClass(this,ListActivity.class,bundle);

//        Toast.makeText(this, item.getBasisLocation(), Toast.LENGTH_SHORT).show();

    }


}
