package customer.tcrj.com.zsproject.first;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.pop.ExtendPopupWindow;
import customer.tcrj.com.zsproject.pop.PopupItem;
import customer.tcrj.com.zsproject.widget.CustomInterface;
import customer.tcrj.com.zsproject.widget.CustomViewPopup;

public class ArcGisActivity extends BaseActivity implements OnStatusChangedListener {

    @BindView(R.id.location_bmap_View)
    MapView mapView;
    @BindView(R.id.btn_tcgl)
    Button btn_tcgl;
    @BindView(R.id.btnback)
    ImageView btnback;

    private ArrayList<PopupItem> menuItemList;
    private ExtendPopupWindow popupMenu;
    private View mImg;

    private ArcGISDynamicMapServiceLayer dynaLayer1;
    private ArcGISDynamicMapServiceLayer dynaLayer2;
    private ArcGISDynamicMapServiceLayer dynaLayer3;
    private ArcGISDynamicMapServiceLayer dynaLayer4;
    private ArcGISDynamicMapServiceLayer dynaLayer5;
    private ArcGISDynamicMapServiceLayer dynaLayer6;
    private ArcGISDynamicMapServiceLayer dynaLayer7;


    @Override
    protected int setLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

        WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

        return R.layout.activity_arc_gis;
    }

    @Override
    protected void setView() {
    }

    @Override
    protected void setData() {

        showLoadingDialog("正在加载图层...");
        dynaLayer1 = new ArcGISDynamicMapServiceLayer(ApiConstants.ARCGIS_URL_DT);
        dynaLayer2 = new ArcGISDynamicMapServiceLayer(ApiConstants.ARCGIS_URL_YAXQZHDL);
        dynaLayer3 = new ArcGISDynamicMapServiceLayer(ApiConstants.ARCGIS_URL_CZWXLGJBZ);
        dynaLayer4 = new ArcGISDynamicMapServiceLayer(ApiConstants.ARCGIS_URL_ZHAFDWBZ);
        dynaLayer5 = new ArcGISDynamicMapServiceLayer(ApiConstants.ARCGIS_URL_CZW);
        dynaLayer6 = new ArcGISDynamicMapServiceLayer(ApiConstants.ARCGIS_URL_ZHAF);
        dynaLayer7 = new ArcGISDynamicMapServiceLayer(ApiConstants.ARCGIS_URL_ZHJT);

        mapView.addLayer(dynaLayer1, 0);
        mapView.addLayer(dynaLayer2, 1);
        mapView.addLayer(dynaLayer3, 2);
        mapView.addLayer(dynaLayer4, 3);
        mapView.addLayer(dynaLayer5, 4);
        mapView.addLayer(dynaLayer6, 5);
        mapView.addLayer(dynaLayer7, 6);

//        mapView.setAllowRotationByPinch(true);
        mapView.setOnStatusChangedListener(this);
//        mapView.setRotationAngle(90);
//        mapView.centerAndZoom();

    }


    private boolean enabled = false;
    @OnClick({R.id.btn_tcgl,R.id.btnback})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tcgl:

                if(enabled){
                    initPopuptWindow(ArcGisActivity.this,btn_tcgl);
                }else {
                    T("图层初始化未完成，不可进行此操作");
                }

                break;
            case R.id.btnback:
                finish();
                break;
            default:
                break;
        }
    }

    private void initPopuptWindow(Context context, View view) {
        if (popupMenu == null) {
            menuItemList = new ArrayList<>();
            popupMenu = new ExtendPopupWindow(context, menuItemList, listener);
        }
        menuItemList.clear();
        menuItemList.addAll(getMoreMenuItems(context));
        popupMenu.notifyData();
        popupMenu.show(view);
    }

    List<PopupItem> moreMenuItems;
    private  List<PopupItem> getMoreMenuItems(Context context) {
        moreMenuItems = new ArrayList<PopupItem>();

        moreMenuItems.add(new PopupItem(context,
                "基础底图", 1,0,isSele1));
        moreMenuItems.add(new PopupItem(context,
                "延安新区智慧道路网", 2,0,isSele2));
        moreMenuItems.add(new PopupItem(context,
                "承载网线路光交布置图", 3,0,isSele3));
        moreMenuItems.add(new PopupItem(context,
                "智慧安防点位布置图", 4,0,isSele4));
        moreMenuItems.add(new PopupItem(context,
                "承载网", 5,0,isSele5));
        moreMenuItems.add(new PopupItem(context,
                "智慧安防", 6,0,isSele6));
        moreMenuItems.add(new PopupItem(context,
                "智慧交通", 7,0,isSele7));

        return moreMenuItems;
    }

    boolean isSele1 = true;
    boolean isSele2 = true;
    boolean isSele3 = true;
    boolean isSele4 = true;
    boolean isSele5 = true;
    boolean isSele6 = true;
    boolean isSele7 = true;


    private ExtendPopupWindow.MenuItemClickListener listener = new ExtendPopupWindow.MenuItemClickListener() {

        @Override
        public void onItemClick(final PopupItem item) {
            switch (item.getTag()) {


                case 1:
                    isSele1 = !item.isselect();
                    dynaLayer1.setVisible(!item.isselect());
                    item.setIsselect(!item.isselect());

                    break;
                case 2:
                    isSele2 = !item.isselect();
                    dynaLayer2.setVisible(!item.isselect());

                    item.setIsselect(!item.isselect());
                    break;
                case 3:
                    isSele3 = !item.isselect();
                    dynaLayer3.setVisible(!item.isselect());
                    item.setIsselect(!item.isselect());

                    break;
                case 4:
                    isSele4 = !item.isselect();
                    dynaLayer4.setVisible(!item.isselect());
                    item.setIsselect(!item.isselect());

                    break;

                case 5:
                    isSele5 = !item.isselect();
                    dynaLayer5.setVisible(!item.isselect());
                    item.setIsselect(!item.isselect());

                    break;
                case 6:
                    isSele6 = !item.isselect();
                    dynaLayer6.setVisible(!item.isselect());
                    item.setIsselect(!item.isselect());

                    break;
                case 7:
                    isSele7 = !item.isselect();
                    dynaLayer7.setVisible(!item.isselect());
                    item.setIsselect(!item.isselect());

                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onStatusChanged(Object o, STATUS status) {

        if (status.equals(STATUS.INITIALIZED)) { //初始化完成才显示，防止黑屏
            enabled = true;
            mapView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideLoadingDialog();
                    mapView.setVisibility(View.VISIBLE);
                }
            }, 100);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.unpause();
    }
}
