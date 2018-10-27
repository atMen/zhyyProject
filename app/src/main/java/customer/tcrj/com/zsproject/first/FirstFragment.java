package customer.tcrj.com.zsproject.first;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.base.BaseFragment;
import customer.tcrj.com.zsproject.bean.LoginInfo;


/**
 * Created by leict on 2018/3/22.
 */

public class FirstFragment extends BaseFragment {

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.ll_cpxx)
    LinearLayout ll_cpxx;
    @BindView(R.id.ll_cxlc)
    LinearLayout ll_cxlc;
    @BindView(R.id.ll_zsmsq)
    LinearLayout ll_zsmsq;
    @BindView(R.id.ll_spjg)
    LinearLayout ll_spjg;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.content)
    TextView content;


    LoginInfo.ResultBean data;
    @Override
    protected int setLayout() {
        return R.layout.first_fragment;
    }

    @Override
    protected void setView() {
//      StatusBarUtil.setTranslucentForImageView(getActivity(),20,icon);
        data = (LoginInfo.ResultBean) ACache.get(mContext).getAsObject("userinfo");
        name.setText("用户名："+data.getName());


        PackageManager um = mContext.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = um.getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        content.setText("延安新区智慧运营系统( V " + pi.versionName+")");
    }


    @Override
    protected void setData() {


    }

    @OnClick({R.id.ll_cpxx,R.id.ll_cxlc,R.id.ll_zsmsq,R.id.ll_spjg})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ll_cpxx:
                toClass(mContext,CPinfoSxActivity.class);//点位基础

                break;

            case R.id.ll_cxlc:
              toClass(mContext,XminfoActivity.class);//项目列表

                 break;

            case R.id.ll_zsmsq:
                toClass(mContext,dwinfoActivity.class);//点位信息
//                Toast.makeText(mContext, "敬请期待", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_spjg:

                break;

            default:
                break;
        }
    }
//    @Override
//    public void onClick(View v) {
//
////        switch (v.getId()){
////
////            case R.id.ll_xxks:
////                toClass(mContext,XxkcActivity.class);//学习考试
////                break;
////            case R.id.ll_xxcx:
////                toClass(mContext,XxcxActivity.class);//信息查询
////                break;
////            case R.id.ll_hdjl:
////                toClass(mContext,hdjlnewActivity.class);//互动交流
////                break;
////            case R.id.ll_xxfb:
////                toClass(mContext,releaseActivity.class);//信息发布
////                break;
////
////        }
//
//    }

}
