package customer.tcrj.com.zsproject.net;

/**
 * desc:
 * author: Will .
 * date: 2017/9/2 .
 */
public class ApiConstants {

    /**
     *
     * http://192.168.20.43:8788/api
     *
     * http://123.139.46.180:8014/Api
     */

//    private static final String URLROOT = "http://192.168.20.43:8788/api";
    private static final String URLROOT = "http://123.139.46.180:8014/Api";
    public static final String ImageURLROOT = "http://demo.tcrj.com.cn:8013/web.files";//回测

//    private static final String URLROOT = "http://221.11.18.88:9999/";
//    public static final String ImageURLROOT = "http://221.11.18.88:9999/";//回测
//    public static final String YPTURLROOT = "http://221.11.18.88:9999/web.files";//回测
//
//    public static final String EWMURLROOT = "http://221.11.18.88:9999/front/product/showProduct.chtml?productId=";

    /**
     * 登录
     */
    public static final String LOGIN_API = URLROOT+"/StaffInfo/PostLoginResult";

    /**
     * 点位基础列表
     */
    public static final String DW_LIST_API = URLROOT+"/Spot/PostSpotBasis";

    /**
     * 点位基础详情
     */
    public static final String DW_XQ_API = URLROOT+"/Spot/PostSpotBasisDetails";


    /**
     * 点位基础修改
     */
    public static final String DW_XQ_EDIT_API = URLROOT+"/Spot/PostSpotBasisEidt";


    /**
     * 点位基础目录列表
     */
    public static final String DW_ML_LIST_API = URLROOT+"/File/PostMenuList";

    /**
     * 新建目录
     */
    public static final String DW_ML_ADD_API = URLROOT+"/File/PostMenuAdd";

    /**
     * 编辑目录
     */
    public static final String DW_ML_EDIT_API = URLROOT+"/File/PostMenuEdit";



    /**
     * 删除目录
     */
    public static final String DW_ML_DEL_API = URLROOT+"/File/PostMenuDel";


    /**
     * 基础分类
     */
    public static final String DW_JC_TYPE_API = URLROOT+"/Spot/PostBasisClass";

    /**
     * 项目数据
     */
    public static final String DW_PROJECT_API = URLROOT+"/Spot/PostProjectList";

    /**
     * 图片信息
     */
    public static final String DW_ICON_API = URLROOT+"/File/PostFielList";

    /**
     * 图片上传
     */
    public static final String DW_ICON_PUCH_API = URLROOT+"/File/PostFileUploadImg";

    /**
     * 图片删除
     */
    public static final String DW_ICON_DELE_API = URLROOT+"/File/PostFielDel";



    //---------------------------------------------------------------------------------------------


    /**
     * 项目- 目录列表
     */
    public static final String XM_LIST_API = URLROOT+"/ProjectInfo/PostProjectList";

    /**
     * 项目- 目录列表
     */
    public static final String XM_ML_LIST_API = URLROOT+"/File/PostSceneMenuList";

    /**
     * 项目- 新建目录
     */
    public static final String XM_ML_ADD_API = URLROOT+"/File/PostSceneMenuAdd";

    /**
     * 项目- 删除目录
     */
    public static final String XM_ML_DEL_API = URLROOT+"/File/PostSceneMenuDel";

    /**
     * 项目- 编辑目录
     */
    public static final String XM_ML_EDIT_API = URLROOT+"/File/PostSceneMenuEdit";


    /**
     * 项目- 图片信息
     *
     */
    public static final String XM_ICON_API = URLROOT+"/File/PostSceneFielList";

    /**
     * 项目- 图片上传
     */
    public static final String XM_ICON_PUCH_API = URLROOT+"/File/PostSceneFileUploadImg";

    /**
     * 项目- 图片删除
     */
    public static final String XM_ICON_DELE_API = URLROOT+"/File/PostSceneFielDel";

    /**
     * 项目- 点位信息列表
     */
    public static final String XM_DW_LIST_API = URLROOT+"/Spot/PostSpotList";


    /**
     * 项目- 点位信息分类
     */
    public static final String XM_DW_TYPE_API = URLROOT+"/Spot/PostSpotClass";


    /**
     * 项目- 点位信息详情
     */
    public static final String XM_DW_INFO_API = URLROOT+"/Spot/PostSpotDetails";


    /**
     * 项目- 点位信息修改
     */
    public static final String XM_DW_INFO_EDIT_API = URLROOT+"/Spot/PostSpotEidt";


}
