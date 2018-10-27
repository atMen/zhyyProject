package customer.tcrj.com.zsproject.first;


import android.content.Intent;
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
import customer.tcrj.com.zsproject.adapter.mlAdapter;
import customer.tcrj.com.zsproject.adapter.spjgAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.MlListInfo;
import customer.tcrj.com.zsproject.bean.bean;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.proListInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import customer.tcrj.com.zsproject.widget.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class ListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, mlAdapter.OnItemClickListener, mlAdapter.OnItemEditClickListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;


    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.num)
    TextView num;


    private MyOkHttp mMyOkhttp;
    private mlAdapter detailAdapter;
    private boolean canPull = true;
    private int pageNum = 1;
    private List<MlListInfo.ResultBean> beanList;
    private String ProID;


    @Override
    protected int setLayout() {
        return R.layout.activity_zscode;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        ProID = getIntent().getStringExtra("ProID");
        initview();
    }

    private void initview() {
        num.setVisibility(View.VISIBLE);
        txtTitle.setText("目录列表");
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
                getData(pageNum);
            }
        });

        beanList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(detailAdapter = new mlAdapter(beanList, ListActivity.this));
        detailAdapter.setEnableLoadMore(false);
        detailAdapter.setOnItemClickListener(this);
        detailAdapter.setOnItemRemoveClickListener(this);
        detailAdapter.setOnItemEditClickListener(this);
        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e("TAG","点击重新加载数据");
                getData(pageNum);
            }
        }, mRecyclerView);
    }

    //获取网络数据
    private void getData(final int num) {
        detailAdapter.setEnableLoadMore(false);
        mMyOkhttp.post()
                .url(ApiConstants.DW_ML_LIST_API)
                .addParam("", ProID)
                .tag(this)
                .enqueue(new GsonResponseHandler<MlListInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);

                        if(num > 1){
//                            loadMoreData(null,true);
                        }else{
                            loadData(null,true);
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, MlListInfo response) {

                        hideLoadingDialog();
                        if(response.getStat() == 1){

                            if(num > 1){//上拉加载
                                Log.e("TAG","上拉加载更多数据");
//                                loadMoreData(response,false);
                            }else{//下拉刷新
                                loadData(response.getResult(),false);
                            }
                        }else{
                            Log.e("TAG","加载错误");
                        }
                    }
                });
    }

    //上拉加载更多数据
    private void loadMoreData(MlListInfo response, boolean isError) {
        Log.e("TAG","loadMoreData");

        List<MlListInfo.ResultBean> result = response.getResult();

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
    private void loadData(List<MlListInfo.ResultBean> response,boolean isError) {

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
                        detailAdapter.setEnableLoadMore(false);
                        Log.e("TAG","setEnableLoadMore(true)");
                    }

                    Log.e("TAG","测试："+(linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1));
                }
            }, 1000);


        }
    }

    @Override
    protected void setData() {
        getData(pageNum);
    }

    @OnClick({R.id.btnback,R.id.num})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:
                finish();
                break;
            case R.id.num:
                Bundle bundle = new Bundle();
                bundle.putString("ProID",ProID);
                toClass(this,UpdateZSActivity.class,bundle,REQUESTCODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        MlListInfo.ResultBean item = (MlListInfo.ResultBean) adapter.getItem(position);
        int id = item.getID();
        Bundle bundle = new Bundle();
        bundle.putString("mlid",id+"");
        bundle.putString("ProID",ProID);
        bundle.putString("MenuName",item.getMenuName());
        toClass(this,IconActivity.class,bundle);
//      Toast.makeText(this, item.getName(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void OnItemClick(MlListInfo.ResultBean bean, int Position) {
        //Toast.makeText(this, "删除："+Position, Toast.LENGTH_SHORT).show();

        showUpdateDialog(bean,Position);
    }
    private final static int REQUESTCODE = 1; // 返回的结果码

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
        // operation succeeded. 默认值是-1
        if (resultCode == 2) {
            if (requestCode == REQUESTCODE) {
                String three = data.getStringExtra("three");
                Log.e("TAG","three"+three);
                getData(1);
            }
        }
    }

    private void showUpdateDialog(final MlListInfo.ResultBean contentBean, final int position) {

/*		Intent intent = new Intent();
        intent.setClass(mContext, IsDownLoad.class);
		mContext.startActivity(intent);*/
        final SweetAlertDialog sad = new SweetAlertDialog(this);
        sad.setTitleText("删除此目录");
        sad.setContentText("您确定要进行删除操作吗？");
        sad.setConfirmText("取消");
        sad.setCancelText("确定");
        sad.setCanceledOnTouchOutside(true);
        sad.setCancelable(true);
        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sureRemove(contentBean,position);
                sad.dismiss();


            }
        });
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.dismiss();

            }
        });
        sad.show();
    }

    private void sureRemove(MlListInfo.ResultBean contentBean, final int position) {

        showLoadingDialog("正在删除数据");

        mMyOkhttp.post()
                .url(ApiConstants.DW_ML_DEL_API)
                .addParam("", contentBean.getID()+"")
                .tag(this)
                .enqueue(new GsonResponseHandler<bean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        hideLoadingDialog();
                        Log.e("TAG","msg"+statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, bean response) {
                        Toast.makeText(ListActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        if(response.getStat() == 1){
                            detailAdapter.remove(position);//删除
                        }
                    }
                });




    }

    @Override
    public void OnItemEditClick(MlListInfo.ResultBean bean, int Position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("bean",bean);
        bundle.putString("ProID",ProID);
        toClass(this,UpdateZSActivity.class,bundle,REQUESTCODE);
    }
}
