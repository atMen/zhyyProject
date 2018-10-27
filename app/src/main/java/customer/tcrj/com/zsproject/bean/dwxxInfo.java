package customer.tcrj.com.zsproject.bean;

import java.util.List;

/**
 * Created by leict on 2018/10/26.
 */

public class dwxxInfo {

    /**
     * msg : 操作成功
     * stat : 1
     * result : {"totalItemCount":396,"totalPageCount":40,"items":[{"ID":4,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zj02","SpotName":"中继2号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":0,"State":1,"Remark":null},{"ID":5,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"jr01","SpotName":"金融1号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":""},{"ID":6,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"jr02","SpotName":"金融2号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":7,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"jr03","SpotName":"金融3号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":8,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zw01","SpotName":"政务1号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":50,"State":2,"Remark":null},{"ID":9,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zw02","SpotName":"政务2号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":10,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zw03","SpotName":"政务3号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":60,"State":2,"Remark":null},{"ID":12,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zh01","SpotName":"智慧1号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":14,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zh02","SpotName":"智慧2号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":15,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zh03","SpotName":"智慧3号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null}]}
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
         * totalItemCount : 396
         * totalPageCount : 40
         * items : [{"ID":4,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zj02","SpotName":"中继2号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":0,"State":1,"Remark":null},{"ID":5,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"jr01","SpotName":"金融1号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":""},{"ID":6,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"jr02","SpotName":"金融2号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":7,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"jr03","SpotName":"金融3号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":8,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zw01","SpotName":"政务1号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":50,"State":2,"Remark":null},{"ID":9,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zw02","SpotName":"政务2号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":10,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zw03","SpotName":"政务3号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":60,"State":2,"Remark":null},{"ID":12,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zh01","SpotName":"智慧1号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":14,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zh02","SpotName":"智慧2号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null},{"ID":15,"ProID":31,"PName":"基础设施承载网（城域网+互联网）项目","SpotCode":"zh03","SpotName":"智慧3号线","SpotClass":324,"SpotClassName":"承载网线路","SpotSchedule":100,"State":3,"Remark":null}]
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
             * ID : 4
             * ProID : 31
             * PName : 基础设施承载网（城域网+互联网）项目
             * SpotCode : zj02
             * SpotName : 中继2号线
             * SpotClass : 324
             * SpotClassName : 承载网线路
             * SpotSchedule : 0
             * State : 1
             * Remark : null
             */

            private int ID;
            private int ProID;
            private String PName;
            private String SpotCode;
            private String SpotName;
            private int SpotClass;
            private String SpotClassName;
            private int SpotSchedule;
            private int State;
            private Object Remark;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getProID() {
                return ProID;
            }

            public void setProID(int ProID) {
                this.ProID = ProID;
            }

            public String getPName() {
                return PName;
            }

            public void setPName(String PName) {
                this.PName = PName;
            }

            public String getSpotCode() {
                return SpotCode;
            }

            public void setSpotCode(String SpotCode) {
                this.SpotCode = SpotCode;
            }

            public String getSpotName() {
                return SpotName;
            }

            public void setSpotName(String SpotName) {
                this.SpotName = SpotName;
            }

            public int getSpotClass() {
                return SpotClass;
            }

            public void setSpotClass(int SpotClass) {
                this.SpotClass = SpotClass;
            }

            public String getSpotClassName() {
                return SpotClassName;
            }

            public void setSpotClassName(String SpotClassName) {
                this.SpotClassName = SpotClassName;
            }

            public int getSpotSchedule() {
                return SpotSchedule;
            }

            public void setSpotSchedule(int SpotSchedule) {
                this.SpotSchedule = SpotSchedule;
            }

            public int getState() {
                return State;
            }

            public void setState(int State) {
                this.State = State;
            }

            public Object getRemark() {
                return Remark;
            }

            public void setRemark(Object Remark) {
                this.Remark = Remark;
            }
        }
    }
}
