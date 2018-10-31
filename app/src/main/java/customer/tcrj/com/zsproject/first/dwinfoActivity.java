package customer.tcrj.com.zsproject.first;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.yyydjk.library.DropDownMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.adapter.ConstellationAdapter;
import customer.tcrj.com.zsproject.adapter.GirdDropDownAdapter;
import customer.tcrj.com.zsproject.adapter.ListDropDownAdapter;
import customer.tcrj.com.zsproject.adapter.SpinnerDateAdapter;
import customer.tcrj.com.zsproject.adapter.SpinnerTypeAdapter;
import customer.tcrj.com.zsproject.adapter.TypeListDropDownAdapter;
import customer.tcrj.com.zsproject.adapter.ZtListDropDownAdapter;
import customer.tcrj.com.zsproject.adapter.dwxxAdapter;
import customer.tcrj.com.zsproject.adapter.spjgAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.dwxxInfo;
import customer.tcrj.com.zsproject.bean.proListInfo;
import customer.tcrj.com.zsproject.bean.projectInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import customer.tcrj.com.zsproject.widget.SimpleMultiStateView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class dwinfoActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


//    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
//    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;
    RelativeLayout rl_loding;

    TextView tv_empty;

    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.edt_search_result)
    EditText edt_search_result;
    @BindView(R.id.iv_search)
    ImageView iv_search;



    private MyOkHttp mMyOkhttp;
    private dwxxAdapter detailAdapter;
    private boolean canPull = true;
    private int pageNum = 1;
    private List<dwxxInfo.ResultBean.ItemsBean> beanList;

    private List<projectInfo.ResultBean> resultType;
    private List<projectInfo.ResultBean> resultProject;


    private int DayType = -1;
    private int projectId = -1;
    private int state = -1;

    private String headers[] = {"项目名称", "点位分类", "状态"};
    private List<View> popupViews = new ArrayList<>();

    private ListDropDownAdapter cityAdapter;
    private ZtListDropDownAdapter ageAdapter;
    private TypeListDropDownAdapter sexAdapter;

    private String citys[] = {"不限", "未开始", "施工中", "已完成"};
//    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
//    private String sexs[] = {"不限", "男", "女"};

    @Override
    protected int setLayout() {
        return R.layout.activity_dw_info;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();

        initview();
        getTypeData();
        getProjectData();

    }

    /**
     * 获取基础分类
     */
    private void getTypeData() {

        mMyOkhttp.post()
                .url(ApiConstants.XM_DW_TYPE_API)
                .enqueue(new GsonResponseHandler<projectInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, projectInfo response) {

                        hideLoadingDialog();
                        if(response.getStat() == 1){

                            resultType = response.getResult();
                            projectInfo.ResultBean resultBean = new projectInfo.ResultBean();
                            resultBean.setName("不限");
                            resultType.add(0,resultBean);
                            resultBean.setID(-1);


                            ageView.setDividerHeight(0);
                            ageAdapter = new ZtListDropDownAdapter(dwinfoActivity.this, resultType);
                            ageView.setAdapter(ageAdapter);
//                            setdate_Spinner_type(resultType);
                        }else{
                            Log.e("TAG","加载错误");
                        }
                    }
                });
    }

    /**
     * 获取项目
     */
    private void getProjectData() {
        mMyOkhttp.post()
                .url(ApiConstants.DW_PROJECT_API)
                .enqueue(new GsonResponseHandler<projectInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode,projectInfo response) {

                        hideLoadingDialog();
                        if(response.getStat() == 1){
                            resultProject = response.getResult();
                            projectInfo.ResultBean resultBean = new projectInfo.ResultBean();
                            resultBean.setPName("不限");
                            resultBean.setID(-1);
                            resultProject.add(0,resultBean);

                            cityAdapter = new ListDropDownAdapter(dwinfoActivity.this, resultProject);
                            cityView.setDividerHeight(0);
                            cityView.setAdapter(cityAdapter);

//                            setdate_Spinner(resultProject);
                        }else{
                            Log.e("TAG","加载错误");
                        }
                    }
                });
    }

    View inflate;
    ListView cityView;
    ListView ageView;

    private void initview() {

        inflate = View.inflate(this, R.layout.activity_dw_content, null);

//        //项目
         cityView = new ListView(this);
//        cityAdapter = new ListDropDownAdapter(this, resultProject);
//        cityView.setDividerHeight(0);
//        cityView.setAdapter(cityAdapter);

        //点位分类
         ageView = new ListView(this);
//        ageView.setDividerHeight(0);
//        ageAdapter = new ListDropDownAdapter(this, resultType);
//        ageView.setAdapter(ageAdapter);

        //状态
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new TypeListDropDownAdapter(this, Arrays.asList(citys));
        sexView.setAdapter(sexAdapter);


        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : resultProject.get(position).getPName());
                mDropDownMenu.closeMenu();

                projectId= resultProject.get(position).getID();
                pageNum = 1;
                String s = edt_search_result.getText().toString();
                rl_loding.setVisibility(View.VISIBLE);
                mPtrFrameLayout.setVisibility(View.GONE);

                getData(pageNum,s);
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : resultType.get(position).getName());
                mDropDownMenu.closeMenu();

                DayType  = resultType.get(position).getID();
                pageNum = 1;
                String s = edt_search_result.getText().toString();
                rl_loding.setVisibility(View.VISIBLE);
                mPtrFrameLayout.setVisibility(View.GONE);

                getData(pageNum,s);
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : citys[position]);
                mDropDownMenu.closeMenu();

                String city = citys[position];

                if("不限".equals(city)){
                    state  = -1;
                }
                if("未开始".equals(city)){
                    state  = 1;
                }

                if("施工中".equals(city)){
                    state  = 2;
                }

                if("已完成".equals(city)){
                    state  = 3;
                }

                rl_loding.setVisibility(View.VISIBLE);
                mPtrFrameLayout.setVisibility(View.GONE);

                pageNum = 1;
                String s = edt_search_result.getText().toString();
                getData(pageNum,s);
            }
        });



        mPtrFrameLayout = inflate.findViewById(R.id.mPtrFrameLayout);
        mRecyclerView = inflate.findViewById(R.id.recycler_view);
        tv_empty = inflate.findViewById(R.id.tv_empty);
        rl_loding = inflate.findViewById(R.id.rl_loding);


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
                rl_loding.setVisibility(View.GONE);
                mPtrFrameLayout.setVisibility(View.VISIBLE);
                pageNum = 1;
                String s = edt_search_result.getText().toString();
                getData(pageNum,s);

            }
        });
        beanList = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(dwinfoActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(detailAdapter = new dwxxAdapter(beanList, dwinfoActivity.this));
//      detailAdapter.setPreLoadNumber(1);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnItemClickListener(this);

        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e("TAG","点击重新加载数据");
                rl_loding.setVisibility(View.GONE);
                mPtrFrameLayout.setVisibility(View.VISIBLE);
                String s = edt_search_result.getText().toString();
                getData(pageNum,s);
            }
        }, mRecyclerView);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, inflate);
    }



    //获取网络数据
    private void getData(final int num,String s) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageIndex", num+"");

            if(projectId > 0){
                jsonObject.put("proID", projectId);
            }

            if(DayType > 0){
                jsonObject.put("SpotClass", DayType);
            }

            if(state > 0){

                jsonObject.put("State", state);

            }

            if(!"".equals(s)){
                jsonObject.put("SpotName", s);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.XM_DW_LIST_API)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<dwxxInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                        T(error_msg);
//                        rl_loding.setVisibility(View.GONE);
                        if(num > 1){
                            loadMoreData(null,true);
                        }else{
                            loadData(null,true);
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, dwxxInfo response) {
                        rl_loding.setVisibility(View.GONE);
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
//                          loadMoreData(response,false);
                            Log.e("TAG","加载错误");
                        }
                    }
                });

    }

    //上拉加载更多数据
    private void loadMoreData(dwxxInfo response, boolean isError) {
        Log.e("TAG","loadMoreData");

        if (response.getResult() == null) {
            if(isError){
                detailAdapter.loadMoreFail();
                Toast.makeText(this, getResources().getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
            }else{
                detailAdapter.loadMoreFail();
            }

        } else {
            List<dwxxInfo.ResultBean.ItemsBean> result = response.getResult().getItems();
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
    private void loadData(dwxxInfo.ResultBean response,boolean isError) {

        if (response == null || response.getItems().size() == 0) {
            if(mPtrFrameLayout != null){
                mPtrFrameLayout.refreshComplete();
            }
            if(isError){
                showFaild();
            }else{
               EmptyView();
            }
            canPull = false;

        } else {

            canPull = true;
            pageNum++;

            mRecyclerView.scrollToPosition(0);//在筛选时不回到顶部，要手动置顶
            detailAdapter.setNewData(response.getItems());
            if(mPtrFrameLayout != null){
                mPtrFrameLayout.refreshComplete();
            }
            Success();
//            showSuccess();
            disableLoadMoreIfNotFullPage(mRecyclerView,response.getItems().size());
        }
    }

    private void Success() {
        mPtrFrameLayout.setVisibility(View.VISIBLE);
        tv_empty.setVisibility(View.GONE);

    }

    private void EmptyView() {
        mPtrFrameLayout.setVisibility(View.GONE);
        tv_empty.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.btnback,R.id.iv_search})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:
                finish();
                break;

            case R.id.iv_search:
                rl_loding.setVisibility(View.VISIBLE);
                mPtrFrameLayout.setVisibility(View.GONE);

                String s = edt_search_result.getText().toString();
                getData(1,s);
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        dwxxInfo.ResultBean.ItemsBean item = (dwxxInfo.ResultBean.ItemsBean) adapter.getItem(position);
        int id = item.getID();
        Bundle bundle = new Bundle();
        bundle.putString("ProID",id+"");
        bundle.putString("SpotClassName",item.getSpotClassName());
        toClass(this,CPListInfoActivity.class,bundle);

//      Toast.makeText(this, item.getBasisLocation(), Toast.LENGTH_SHORT).show();

    }
}
