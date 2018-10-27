package customer.tcrj.com.zsproject.bean;

import java.util.List;

/**
 * Created by leict on 2018/10/25.
 */

public class XmListInfo {

    /**
     * msg : 操作成功
     * stat : 1
     * result : {"totalItemCount":4,"totalPageCount":1,"items":[{"ID":31,"PCode":"MR001","PName":"基础设施承载网（城域网+互联网）项目","BuildCompany":"延安弥尔智慧城市投资发展有限公司","ConCompany":"-","PStartTime":"2018-07-10T00:00:00","PFinshTime":"2018-10-31T00:00:00","Schedule":0,"ComputeSchedule":47.29,"CurrentState":7,"AcceptTime":null,"Remark":null},{"ID":32,"PCode":"MR002","PName":"延安新区基础设施智慧服务系统项目（空间地理信息系统）","BuildCompany":"延安弥尔智慧城市投资发展有限公司","ConCompany":"-","PStartTime":"2018-04-18T00:00:00","PFinshTime":"2018-12-31T00:00:00","Schedule":5,"ComputeSchedule":0,"CurrentState":4,"AcceptTime":null,"Remark":null},{"ID":33,"PCode":"MR003","PName":"延安新区智慧交通项目","BuildCompany":"延安弥尔智慧城市投资发展有限公司","ConCompany":"淄博晶莹电子工程有限公司","PStartTime":"2018-04-18T00:00:00","PFinshTime":"2018-10-31T00:00:00","Schedule":0,"ComputeSchedule":63.16,"CurrentState":7,"AcceptTime":null,"Remark":"本项目2018年度可建路口49个"},{"ID":34,"PCode":"MR004","PName":"延安新区智慧安防项目（总体规划和一期工程）","BuildCompany":"延安弥尔智慧城市投资发展有限公司","ConCompany":"榆林智能安防有限公司","PStartTime":"2018-04-18T00:00:00","PFinshTime":"2018-11-30T00:00:00","Schedule":0,"ComputeSchedule":0.94,"CurrentState":7,"AcceptTime":null,"Remark":null}]}
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
         * items : [{"ID":31,"PCode":"MR001","PName":"基础设施承载网（城域网+互联网）项目","BuildCompany":"延安弥尔智慧城市投资发展有限公司","ConCompany":"-","PStartTime":"2018-07-10T00:00:00","PFinshTime":"2018-10-31T00:00:00","Schedule":0,"ComputeSchedule":47.29,"CurrentState":7,"AcceptTime":null,"Remark":null},{"ID":32,"PCode":"MR002","PName":"延安新区基础设施智慧服务系统项目（空间地理信息系统）","BuildCompany":"延安弥尔智慧城市投资发展有限公司","ConCompany":"-","PStartTime":"2018-04-18T00:00:00","PFinshTime":"2018-12-31T00:00:00","Schedule":5,"ComputeSchedule":0,"CurrentState":4,"AcceptTime":null,"Remark":null},{"ID":33,"PCode":"MR003","PName":"延安新区智慧交通项目","BuildCompany":"延安弥尔智慧城市投资发展有限公司","ConCompany":"淄博晶莹电子工程有限公司","PStartTime":"2018-04-18T00:00:00","PFinshTime":"2018-10-31T00:00:00","Schedule":0,"ComputeSchedule":63.16,"CurrentState":7,"AcceptTime":null,"Remark":"本项目2018年度可建路口49个"},{"ID":34,"PCode":"MR004","PName":"延安新区智慧安防项目（总体规划和一期工程）","BuildCompany":"延安弥尔智慧城市投资发展有限公司","ConCompany":"榆林智能安防有限公司","PStartTime":"2018-04-18T00:00:00","PFinshTime":"2018-11-30T00:00:00","Schedule":0,"ComputeSchedule":0.94,"CurrentState":7,"AcceptTime":null,"Remark":null}]
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
             * ID : 31
             * PCode : MR001
             * PName : 基础设施承载网（城域网+互联网）项目
             * BuildCompany : 延安弥尔智慧城市投资发展有限公司
             * ConCompany : -
             * PStartTime : 2018-07-10T00:00:00
             * PFinshTime : 2018-10-31T00:00:00
             * Schedule : 0
             * ComputeSchedule : 47.29
             * CurrentState : 7
             * AcceptTime : null
             * Remark : null
             */

            private int ID;
            private String PCode;
            private String PName;
            private String BuildCompany;
            private String ConCompany;
            private String PStartTime;
            private String PFinshTime;
            private int Schedule;
            private double ComputeSchedule;
            private int CurrentState;
            private Object AcceptTime;
            private Object Remark;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getPCode() {
                return PCode;
            }

            public void setPCode(String PCode) {
                this.PCode = PCode;
            }

            public String getPName() {
                return PName;
            }

            public void setPName(String PName) {
                this.PName = PName;
            }

            public String getBuildCompany() {
                return BuildCompany;
            }

            public void setBuildCompany(String BuildCompany) {
                this.BuildCompany = BuildCompany;
            }

            public String getConCompany() {
                return ConCompany;
            }

            public void setConCompany(String ConCompany) {
                this.ConCompany = ConCompany;
            }

            public String getPStartTime() {
                return PStartTime;
            }

            public void setPStartTime(String PStartTime) {
                this.PStartTime = PStartTime;
            }

            public String getPFinshTime() {
                return PFinshTime;
            }

            public void setPFinshTime(String PFinshTime) {
                this.PFinshTime = PFinshTime;
            }

            public int getSchedule() {
                return Schedule;
            }

            public void setSchedule(int Schedule) {
                this.Schedule = Schedule;
            }

            public double getComputeSchedule() {
                return ComputeSchedule;
            }

            public void setComputeSchedule(double ComputeSchedule) {
                this.ComputeSchedule = ComputeSchedule;
            }

            public int getCurrentState() {
                return CurrentState;
            }

            public void setCurrentState(int CurrentState) {
                this.CurrentState = CurrentState;
            }

            public Object getAcceptTime() {
                return AcceptTime;
            }

            public void setAcceptTime(Object AcceptTime) {
                this.AcceptTime = AcceptTime;
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
