package customer.tcrj.com.zsproject.first;


import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
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
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.adapter.ldjhAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.LoginInfo;
import customer.tcrj.com.zsproject.bean.bean;
import customer.tcrj.com.zsproject.bean.iconInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class XmIconActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private final static int REQUESTCODE = 1; // 返回的结果码

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
    private ldjhAdapter detailAdapter;
    private boolean canPull = true;
    private int pageNum = 1;
    private List<iconInfo.ResultBean> beanList;
    private String ProID;
    private String id;
    private String MenuName;
    LoginInfo.ResultBean data;

    @Override
    protected int setLayout() {
        return R.layout.activity_cpinfo;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        data = (LoginInfo.ResultBean) ACache.get(this).getAsObject("userinfo");
        id = getIntent().getStringExtra("mlid");
        ProID = getIntent().getStringExtra("ProID");
        MenuName = getIntent().getStringExtra("MenuName");
        initview();
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(XmIconActivity.this);
                } else {
                    Toast.makeText(XmIconActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void initview() {
        num.setVisibility(View.VISIBLE);
        num.setText("添加图片");
        txtTitle.setText(MenuName);

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

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(detailAdapter = new ldjhAdapter(beanList, this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        detailAdapter.setPreLoadNumber(1);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnItemClickListener(this);

        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e("TAG","点击重新加载数据");

                getData(pageNum);
            }
        }, mRecyclerView);
    }

    iconInfo iconInfo;
    //获取网络数据
    private void getData(final int num) {

        showLoadingDialog("正在获取图片信息...");
        mMyOkhttp.post()
                .url(ApiConstants.XM_ICON_API)
                .addParam("", id)
                .enqueue(new GsonResponseHandler<iconInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);

//                        if(num > 1){
//                            loadMoreData(null,true);
//                        }else{
//                            loadData(null,true);
//
//                        }

                        loadData(null,true);
                    }

                    @Override
                    public void onSuccess(int statusCode, iconInfo response) {

                        hideLoadingDialog();
                        if(response.getStat() == 1){
                            iconInfo = response;
//                            if(num > 1){//上拉加载
//                                Log.e("TAG","上拉加载更多数据");
//                                loadMoreData(response,false);
//                            }else{//下拉刷新
//                                loadData(response.getResult(),false);
//                            }

                            loadData(response.getResult(),false);

                        }else{

                            Log.e("TAG","加载错误");
                        }
                    }
                });

    }
    //上拉加载更多数据
    private void loadMoreData(iconInfo response, boolean isError) {
        Log.e("TAG","loadMoreData");

        List<iconInfo.ResultBean> result = response.getResult();

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
    private void loadData(List<iconInfo.ResultBean> response,boolean isError) {

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
        getData(1);
    }

    @OnClick({R.id.btnback,R.id.num})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnback:
                finish();
                break;
            case R.id.num:
                addIcon();
                break;
            default:
                break;

        }
    }
    private List<LocalMedia> selectList = new ArrayList<>();
    private void addIcon() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(XmIconActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(6)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(null)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//              .videoMaxSecond(15)
//              .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("原图片-----》", media.getPath());
                        Log.i("压缩图片-----》", media.getCompressPath());
                        //1.4M可压缩到500多K
                    }

                    showLoadingDialog("正在努力上传...");
                    new MyAsyncTask().execute(1);

                    break;

                case REQUESTCODE:

                    getData(1);
                    break;

                default:
                    break;
            }
        }
    }

    public class MyAsyncTask extends AsyncTask<Integer, String, String> {
        public MyAsyncTask() {
            super();
        }

        //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Integer... integers) {
            Log.e("xxxxxx","xxxxxxexecute传入参数="+integers[0]);

            StringBuffer stringb = new StringBuffer();
            for (LocalMedia media : selectList) {
                Log.i("原图片-----》", media.getPath());
                Log.i("压缩图片-----》", media.getCompressPath());
                //1.4M可压缩到500多K

                try {
                    String base64 = Utils.encodeBase64File(media.getCompressPath());

                    stringb = stringb.append("sfz.jpg;"+base64+"|");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            return stringb.toString();
        }
        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(String result) {
            Log.e("TAG","线程结束"+result);

//            hideLoadingDialog();
//            String time = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss",
//                    Locale.getDefault()).format(System.currentTimeMillis());
            if(result != null){
                getDataFromNet(result);
            }
        }

        /**
         * 这里的Intege参数对应AsyncTask中的第二个参数
         * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
         * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
         */
        @Override
        protected void onProgressUpdate(String... values) {
            String vlaue = values[0]+"";
            Log.e("TAG","xxxxxx vlaue="+vlaue);
        }
    }

    private void getDataFromNet(String result) {

        String substring = result.substring(0, result.length() - 1);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("fileStr", substring);
            jsonObject.put("StaffID", data.getName());
            jsonObject.put("DataID", ProID);
            jsonObject.put("MenuID", id);
            jsonObject.put("TableType", "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.XM_ICON_PUCH_API)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new GsonResponseHandler<bean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Toast.makeText(XmIconActivity.this, error_msg, Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        Log.e("TAG","msg"+error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, bean response) {
                        Toast.makeText(XmIconActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        if(response.getStat() == 1){
                            getData(1);
                        }
                    }
                });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        iconInfo.ResultBean item = (iconInfo.ResultBean) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("iconInfo",iconInfo);
        bundle.putInt("position",position);
        toClass(this,XmPhototViewActivity.class,bundle,REQUESTCODE);

    }





}
