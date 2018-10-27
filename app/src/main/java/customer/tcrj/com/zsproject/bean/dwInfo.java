package customer.tcrj.com.zsproject.bean;

import java.io.Serializable;

/**
 * Created by leict on 2018/10/26.
 */

public class dwInfo implements Serializable{

    /**
     * msg : 操作成功
     * stat : 1
     * result : {"ProjectInfo":{"PCode":"MR001","PName":"基础设施承载网（城域网+互联网）项目","BuildCompany":"延安弥尔智慧城市投资发展有限公司","BuildCompanyInfo":"-","ConCompany":"-","ConCompanyInfo":"-","PStartTime":"2018-07-10T00:00:00","PFinshTime":"2018-10-31T00:00:00","Schedule":0,"CurrentState":7,"AcceptTime":null,"Remark":null,"IsDel":false,"GISProType":0,"GISObjID":0,"Plans":null,"ID":31,"CreateStaffID":0,"CreateTime":"1900-01-01T00:00:00","UpdateStaffID":1,"UpdateTime":"2018-10-16T16:46:40.847"},"ProID":31,"SpotCode":"zj02","SpotName":"中继2号线","SpotClass":324,"SpotSchedule":0,"State":1,"Remark":null,"IsDel":false,"GISSoptType":1,"GISSoptID":0,"ID":4,"CreateStaffID":15,"CreateTime":"2018-10-09T08:28:35.55","UpdateStaffID":1,"UpdateTime":"2018-10-22T18:58:11.137"}
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

    public static class ResultBean  implements Serializable{
        /**
         * ProjectInfo : {"PCode":"MR001","PName":"基础设施承载网（城域网+互联网）项目","BuildCompany":"延安弥尔智慧城市投资发展有限公司","BuildCompanyInfo":"-","ConCompany":"-","ConCompanyInfo":"-","PStartTime":"2018-07-10T00:00:00","PFinshTime":"2018-10-31T00:00:00","Schedule":0,"CurrentState":7,"AcceptTime":null,"Remark":null,"IsDel":false,"GISProType":0,"GISObjID":0,"Plans":null,"ID":31,"CreateStaffID":0,"CreateTime":"1900-01-01T00:00:00","UpdateStaffID":1,"UpdateTime":"2018-10-16T16:46:40.847"}
         * ProID : 31
         * SpotCode : zj02
         * SpotName : 中继2号线
         * SpotClass : 324
         * SpotSchedule : 0
         * State : 1
         * Remark : null
         * IsDel : false
         * GISSoptType : 1
         * GISSoptID : 0
         * ID : 4
         * CreateStaffID : 15
         * CreateTime : 2018-10-09T08:28:35.55
         * UpdateStaffID : 1
         * UpdateTime : 2018-10-22T18:58:11.137
         */

        private ProjectInfoBean ProjectInfo;
        private int ProID;
        private String SpotCode;
        private String SpotName;
        private int SpotClass;
        private int SpotSchedule;
        private int State;
        private String Remark;
        private boolean IsDel;
        private int GISSoptType;
        private int GISSoptID;
        private int ID;
        private int CreateStaffID;
        private String CreateTime;
        private int UpdateStaffID;
        private String UpdateTime;

        public ProjectInfoBean getProjectInfo() {
            return ProjectInfo;
        }

        public void setProjectInfo(ProjectInfoBean ProjectInfo) {
            this.ProjectInfo = ProjectInfo;
        }

        public int getProID() {
            return ProID;
        }

        public void setProID(int ProID) {
            this.ProID = ProID;
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

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public boolean isIsDel() {
            return IsDel;
        }

        public void setIsDel(boolean IsDel) {
            this.IsDel = IsDel;
        }

        public int getGISSoptType() {
            return GISSoptType;
        }

        public void setGISSoptType(int GISSoptType) {
            this.GISSoptType = GISSoptType;
        }

        public int getGISSoptID() {
            return GISSoptID;
        }

        public void setGISSoptID(int GISSoptID) {
            this.GISSoptID = GISSoptID;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getCreateStaffID() {
            return CreateStaffID;
        }

        public void setCreateStaffID(int CreateStaffID) {
            this.CreateStaffID = CreateStaffID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getUpdateStaffID() {
            return UpdateStaffID;
        }

        public void setUpdateStaffID(int UpdateStaffID) {
            this.UpdateStaffID = UpdateStaffID;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public static class ProjectInfoBean  implements Serializable{
            /**
             * PCode : MR001
             * PName : 基础设施承载网（城域网+互联网）项目
             * BuildCompany : 延安弥尔智慧城市投资发展有限公司
             * BuildCompanyInfo : -
             * ConCompany : -
             * ConCompanyInfo : -
             * PStartTime : 2018-07-10T00:00:00
             * PFinshTime : 2018-10-31T00:00:00
             * Schedule : 0
             * CurrentState : 7
             * AcceptTime : null
             * Remark : null
             * IsDel : false
             * GISProType : 0
             * GISObjID : 0
             * Plans : null
             * ID : 31
             * CreateStaffID : 0
             * CreateTime : 1900-01-01T00:00:00
             * UpdateStaffID : 1
             * UpdateTime : 2018-10-16T16:46:40.847
             */

            private String PCode;
            private String PName;
            private String BuildCompany;
            private String BuildCompanyInfo;
            private String ConCompany;
            private String ConCompanyInfo;
            private String PStartTime;
            private String PFinshTime;
            private int Schedule;
            private int CurrentState;
            private Object AcceptTime;
            private Object Remark;
            private boolean IsDel;
            private int GISProType;
            private int GISObjID;
            private Object Plans;
            private int ID;
            private int CreateStaffID;
            private String CreateTime;
            private int UpdateStaffID;
            private String UpdateTime;

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

            public String getBuildCompanyInfo() {
                return BuildCompanyInfo;
            }

            public void setBuildCompanyInfo(String BuildCompanyInfo) {
                this.BuildCompanyInfo = BuildCompanyInfo;
            }

            public String getConCompany() {
                return ConCompany;
            }

            public void setConCompany(String ConCompany) {
                this.ConCompany = ConCompany;
            }

            public String getConCompanyInfo() {
                return ConCompanyInfo;
            }

            public void setConCompanyInfo(String ConCompanyInfo) {
                this.ConCompanyInfo = ConCompanyInfo;
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

            public boolean isIsDel() {
                return IsDel;
            }

            public void setIsDel(boolean IsDel) {
                this.IsDel = IsDel;
            }

            public int getGISProType() {
                return GISProType;
            }

            public void setGISProType(int GISProType) {
                this.GISProType = GISProType;
            }

            public int getGISObjID() {
                return GISObjID;
            }

            public void setGISObjID(int GISObjID) {
                this.GISObjID = GISObjID;
            }

            public Object getPlans() {
                return Plans;
            }

            public void setPlans(Object Plans) {
                this.Plans = Plans;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getCreateStaffID() {
                return CreateStaffID;
            }

            public void setCreateStaffID(int CreateStaffID) {
                this.CreateStaffID = CreateStaffID;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public int getUpdateStaffID() {
                return UpdateStaffID;
            }

            public void setUpdateStaffID(int UpdateStaffID) {
                this.UpdateStaffID = UpdateStaffID;
            }

            public String getUpdateTime() {
                return UpdateTime;
            }

            public void setUpdateTime(String UpdateTime) {
                this.UpdateTime = UpdateTime;
            }
        }
    }
}
