package customer.tcrj.com.zsproject.first;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.jaeger.library.StatusBarUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.List;

import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.DialogHelper;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import customer.tcrj.com.zsproject.bean.MlListInfo;
import customer.tcrj.com.zsproject.bean.bean;
import customer.tcrj.com.zsproject.bean.iconInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.SweetAlertDialog;

public class PhototViewActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    ImageView iv_dele;
    TextView photo_num;
    iconInfo item;
    List<iconInfo.ResultBean> result;
    SamplePagerAdapter samplePagerAdapter;
    private MyOkHttp mMyOkhttp;
//    http://www.yldjw.gov.cn/web.files/uploadfile/ryEbiy/ue/image/20180829/1535513217160028442.jpg
//    http://www.yldjw.gov.cn/web.files/uploadfile/ryEbiy/ue/image/20180904/1536055694552076867.jpg
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photot_view);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black),5);
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        item = (iconInfo) getIntent().getSerializableExtra("iconInfo");
        int position = getIntent().getIntExtra("position", -1);
        result = item.getResult();
        delepositing = position;
        viewPager = findViewById(R.id.view_pager);
         iv_dele = findViewById(R.id.iv_dele);
         photo_num = findViewById(R.id.photo_num);

        iv_dele.setOnClickListener(this);

         samplePagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(samplePagerAdapter);
        if(viewPager != null){
            viewPager.setCurrentItem(position);
        }
        photo_num.setText((position+1)+"/"+result.size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                delepositing = position;
//                photo_num.setText((position+1)+"/"+result.size());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private int delepositing;

    @Override
    public void onClick(View v) {
//
        showUpdateDialog();

    }

    class SamplePagerAdapter extends PagerAdapter {

//        private  final int[] sDrawables = {R.drawable.btn_dialog, R.drawable.banner1, R.drawable.btn_dialog,
//                R.drawable.banner1, R.drawable.btn_dialog, R.drawable.banner1};

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(PhototViewActivity.this, R.layout.item_base,null);

//            PhotoView photoView = new PhotoView(container.getContext());

            PhotoView photoView = view.findViewById(R.id.photo_view);


            ShowImageUtils.LoadImage(PhototViewActivity.this, result.get(delepositing).getFileUrl(),photoView);
//            ShowImageUtils.showImageView(PhototViewActivity.this,result.get(position).getFileUrl(),photoView,R.drawable.ic_placeholder);

//            photoView.setImageResource(sDrawables[position]);
            // Now just add PhotoView to ViewPager and return it
            container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


    }


    private void sureRemove() {

        showLoadingDialog("正在删除图片");

        mMyOkhttp.post()
                .url(ApiConstants.DW_ICON_DELE_API)
                .addParam("", item.getResult().get(delepositing).getID()+"")
                .tag(this)
                .enqueue(new GsonResponseHandler<bean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        hideLoadingDialog();
                        Log.e("TAG","msg"+statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, bean response) {
                        Toast.makeText(PhototViewActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        if(response.getStat() == 1){
                            int size = result.size();
                            Log.e("TAG","size"+size);


                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish(); //结束当前的activity的生命周期


//                            if(size == 1){
//                                Intent intent = new Intent();
//                                setResult(RESULT_OK, intent);
//                                finish(); //结束当前的activity的生命周期
//                            }else {
//                                result.remove(delepositing);
//                                samplePagerAdapter.notifyDataSetChanged();
//                                photo_num.setText((delepositing+1)+"/"+result.size());
//                            }
                        }
                    }
                });
    }

    protected Dialog mLoadingDialog = null;
    protected void showLoadingDialog(String str) {
        if (mLoadingDialog != null) {
            TextView tv = (TextView) mLoadingDialog.findViewById(R.id.tv_load_dialog);
            tv.setText(str);
            mLoadingDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish(); //结束当前的activity的生命周期

            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void showUpdateDialog() {

/*		Intent intent = new Intent();
        intent.setClass(mContext, IsDownLoad.class);
		mContext.startActivity(intent);*/
        final SweetAlertDialog sad = new SweetAlertDialog(this);
        sad.setTitleText("删除此图片");
        sad.setContentText("您确定要进行删除操作吗？");
        sad.setConfirmText("取消");
        sad.setCancelText("确定");
        sad.setCanceledOnTouchOutside(true);
        sad.setCancelable(true);
        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sureRemove();
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

}
