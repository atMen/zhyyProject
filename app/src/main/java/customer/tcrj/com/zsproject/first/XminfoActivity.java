package customer.tcrj.com.zsproject.first;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import customer.tcrj.com.zsproject.adapter.SpinnerDateAdapter;
import customer.tcrj.com.zsproject.adapter.SpinnerTypeAdapter;
import customer.tcrj.com.zsproject.adapter.spjgAdapter;
import customer.tcrj.com.zsproject.adapter.xmAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.XmListInfo;
import customer.tcrj.com.zsproject.bean.proListInfo;
import customer.tcrj.com.zsproject.bean.projectInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class XminfoActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;


    @BindView(R.id.subordinate_type)
    Spinner subordinate_type;


    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.edt_search_result)
    EditText edt_search_result;
    @BindView(R.id.iv_search)
    ImageView iv_search;


    private MyOkHttp mMyOkhttp;
    private xmAdapter detailAdapter;
    private boolean canPull = true;
    private int pageNum = 1;
    private List<XmListInfo.ResultBean.ItemsBean> beanList;

    private List<projectInfo.ResultBean> resultType;

    @Override
    protected int setLayout() {
        return R.layout.activity_xm_info;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        initview();

        SpinnerLoad();

    }


    private void SpinnerLoad() {
        resultType = new ArrayList<>();
        CharSequence[] dates = this.getResources().getStringArray(R.array.xm_type);
        int[] intArray = this.getResources().getIntArray(R.array.xm_typeid);
        for (int i = 0; i < dates.length; i++) {
            projectInfo.ResultBean dateentity = new projectInfo.ResultBean();
            dateentity.setID(intArray[i]);
            dateentity.setName(dates[i].toString());
            resultType.add(dateentity);
        }
        setdate_Spinner_type(resultType);
    }


    private void initview() {

        edt_search_result.setHint("请输入项目名称查询");

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(XminfoActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(detailAdapter = new xmAdapter(beanList, XminfoActivity.this));
//      detailAdapter.setPreLoadNumber(1);
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

    private int DayType = -1;

    //获取网络数据
    private void getData(final int num,String s) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("pageIndex", num+"");


            if(DayType > 0){
                jsonObject.put("Status", DayType);
            }

            if(!"".equals(s)){
                jsonObject.put("ProjectName", s);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.XM_LIST_API)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<XmListInfo>() {
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
                    public void onSuccess(int statusCode, XmListInfo response) {

                        hideLoadingDialog();
                        if(response.getStat() == 1){

                            if(num > 1){//上拉加载
                                Log.e("TAG","上拉加载更多数据");
                                loadMoreData(response,false);
                            }else{//下拉刷新
                                loadData(response.getResult(),false);
                            }

                        }else if(response.getStat() == 0 & "超出总页数".equals(response.getMsg())){

                            if(num > 1){//上拉加载
                                Log.e("TAG","上拉加载更多数据");
                                loadMoreData(response,false);
                            }else{//下拉刷新
                                loadData(response.getResult(),false);
                            }
//                            loadMoreData(response,false);
                            Log.e("TAG","加载错误");
                        }
                    }
                });

    }

    //上拉加载更多数据
    private void loadMoreData(XmListInfo response, boolean isError) {
        Log.e("TAG","loadMoreData");

        if (response.getResult() == null) {
            if(isError){
                detailAdapter.loadMoreFail();
                Toast.makeText(this, getResources().getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
            }else{
                detailAdapter.loadMoreFail();
            }

        } else {
            List<XmListInfo.ResultBean.ItemsBean> result = response.getResult().getItems();
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
    private void loadData(XmListInfo.ResultBean response,boolean isError) {

        if (response == null || response.getItems().size() == 0) {
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
            mRecyclerView.scrollToPosition(0);
            detailAdapter.setNewData(response.getItems());
            if(mPtrFrameLayout != null){
                mPtrFrameLayout.refreshComplete();
            }
            showSuccess();
            disableLoadMoreIfNotFullPage(mRecyclerView,response.getItems().size());
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
//        getData(pageNum,"");
    }

    @OnClick({R.id.btnback,R.id.iv_search})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:
                finish();
                break;

            case R.id.iv_search:
                String s = edt_search_result.getText().toString();
                getData(1,s);
                break;

            default:
                break;

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        XmListInfo.ResultBean.ItemsBean item = (XmListInfo.ResultBean.ItemsBean) adapter.getItem(position);
        int id = item.getID();
        Bundle bundle = new Bundle();
        bundle.putString("ProID",id+"");
        toClass(this,XmListActivity.class,bundle);

//      Toast.makeText(this, item.getBasisLocation(), Toast.LENGTH_SHORT).show();

    }

    SpinnerTypeAdapter Dadapter1;
    public void setdate_Spinner_type(List<projectInfo.ResultBean> r_Spinner) {

        Dadapter1 = new SpinnerTypeAdapter(this);
        Dadapter1.setData(r_Spinner);
        subordinate_type.setAdapter(Dadapter1);

        subordinate_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectInfo.ResultBean entity = (projectInfo.ResultBean) Dadapter1.getItem(position);
                DayType = entity.getID();

                pageNum = 1;
                String s = edt_search_result.getText().toString();
                getData(pageNum,s);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
