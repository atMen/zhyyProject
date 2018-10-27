package customer.tcrj.com.zsproject.bean;

import java.util.List;

/**
 * Created by leict on 2018/10/19.
 */

public class proListInfo {


    /**
     * msg : 操作成功
     * stat : 1
     * result : {"totalItemCount":4,"totalPageCount":1,"items":[{"ID":11,"SpotID":2,"SpotName":"大庆路十字","BasisCode":"001","BasisClass":314,"ClassName":"杆件基础","BasisLocation":"嗯嗯","SoilClass":"阿萨德","DesignSize":"3","DesignCubage":"4","ActualSize":"2","ActualCubage":"3","Remark":null,"ProID":2,"ImgCount":0},{"ID":12,"SpotID":16,"SpotName":"测试","BasisCode":"1111","BasisClass":315,"ClassName":"机箱基础","BasisLocation":"嘉昱大厦","SoilClass":"3","DesignSize":"4","DesignCubage":"3","ActualSize":"4","ActualCubage":"3","Remark":"士大夫撒","ProID":29,"ImgCount":0},{"ID":13,"SpotID":16,"SpotName":"测试","BasisCode":"002","BasisClass":316,"ClassName":"管沟","BasisLocation":"随便","SoilClass":"2","DesignSize":null,"DesignCubage":null,"ActualSize":null,"ActualCubage":null,"Remark":null,"ProID":29,"ImgCount":0},{"ID":14,"SpotID":15,"SpotName":"asdf","BasisCode":"55","BasisClass":315,"ClassName":"机箱基础","BasisLocation":"sx","SoilClass":null,"DesignSize":null,"DesignCubage":null,"ActualSize":null,"ActualCubage":null,"Remark":null,"ProID":2,"ImgCount":0}]}
     */

    private String msg;
    private int stat;
    private ResultBean result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * totalItemCount : 4
         * totalPageCount : 1
         * items : [{"ID":11,"SpotID":2,"SpotName":"大庆路十字","BasisCode":"001","BasisClass":314,"ClassName":"杆件基础","BasisLocation":"嗯嗯","SoilClass":"阿萨德","DesignSize":"3","DesignCubage":"4","ActualSize":"2","ActualCubage":"3","Remark":null,"ProID":2,"ImgCount":0},{"ID":12,"SpotID":16,"SpotName":"测试","BasisCode":"1111","BasisClass":315,"ClassName":"机箱基础","BasisLocation":"嘉昱大厦","SoilClass":"3","DesignSize":"4","DesignCubage":"3","ActualSize":"4","ActualCubage":"3","Remark":"士大夫撒","ProID":29,"ImgCount":0},{"ID":13,"SpotID":16,"SpotName":"测试","BasisCode":"002","BasisClass":316,"ClassName":"管沟","BasisLocation":"随便","SoilClass":"2","DesignSize":null,"DesignCubage":null,"ActualSize":null,"ActualCubage":null,"Remark":null,"ProID":29,"ImgCount":0},{"ID":14,"SpotID":15,"SpotName":"asdf","BasisCode":"55","BasisClass":315,"ClassName":"机箱基础","BasisLocation":"sx","SoilClass":null,"DesignSize":null,"DesignCubage":null,"ActualSize":null,"ActualCubage":null,"Remark":null,"ProID":2,"ImgCount":0}]
         */

        private int totalItemCount;
        private int totalPageCount;
        private List<ItemsBean> items;

        public int getTotalItemCount() {
            return totalItemCount;
        }

        public void setTotalItemCount(int totalItemCount) {
            this.totalItemCount = totalItemCount;
        }

        public int getTotalPageCount() {
            return totalPageCount;
        }

        public void setTotalPageCount(int totalPageCount) {
            this.totalPageCount = totalPageCount;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * ID : 11
             * SpotID : 2
             * SpotName : 大庆路十字
             * BasisCode : 001
             * BasisClass : 314
             * ClassName : 杆件基础
             * BasisLocation : 嗯嗯
             * SoilClass : 阿萨德
             * DesignSize : 3
             * DesignCubage : 4
             * ActualSize : 2
             * ActualCubage : 3
             * Remark : null
             * ProID : 2
             * ImgCount : 0
             */

            private int ID;
            private int SpotID;
            private String SpotName;
            private String BasisCode;
            private int BasisClass;
            private String ClassName;
            private String BasisLocation;
            private String SoilClass;
            private String DesignSize;
            private String DesignCubage;
            private String ActualSize;
            private String ActualCubage;
            private Object Remark;
            private int ProID;
            private int ImgCount;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getSpotID() {
                return SpotID;
            }

            public void setSpotID(int SpotID) {
                this.SpotID = SpotID;
            }

            public String getSpotName() {
                return SpotName;
            }

            public void setSpotName(String SpotName) {
                this.SpotName = SpotName;
            }

            public String getBasisCode() {
                return BasisCode;
            }

            public void setBasisCode(String BasisCode) {
                this.BasisCode = BasisCode;
            }

            public int getBasisClass() {
                return BasisClass;
            }

            public void setBasisClass(int BasisClass) {
                this.BasisClass = BasisClass;
            }

            public String getClassName() {
                return ClassName;
            }

            public void setClassName(String ClassName) {
                this.ClassName = ClassName;
            }

            public String getBasisLocation() {
                return BasisLocation;
            }

            public void setBasisLocation(String BasisLocation) {
                this.BasisLocation = BasisLocation;
            }

            public String getSoilClass() {
                return SoilClass;
            }

            public void setSoilClass(String SoilClass) {
                this.SoilClass = SoilClass;
            }

            public String getDesignSize() {
                return DesignSize;
            }

            public void setDesignSize(String DesignSize) {
                this.DesignSize = DesignSize;
            }

            public String getDesignCubage() {
                return DesignCubage;
            }

            public void setDesignCubage(String DesignCubage) {
                this.DesignCubage = DesignCubage;
            }

            public String getActualSize() {
                return ActualSize;
            }

            public void setActualSize(String ActualSize) {
                this.ActualSize = ActualSize;
            }

            public String getActualCubage() {
                return ActualCubage;
            }

            public void setActualCubage(String ActualCubage) {
                this.ActualCubage = ActualCubage;
            }

            public Object getRemark() {
                return Remark;
            }

            public void setRemark(Object Remark) {
                this.Remark = Remark;
            }

            public int getProID() {
                return ProID;
            }

            public void setProID(int ProID) {
                this.ProID = ProID;
            }

            public int getImgCount() {
                return ImgCount;
            }

            public void setImgCount(int ImgCount) {
                this.ImgCount = ImgCount;
            }
        }
    }
}
