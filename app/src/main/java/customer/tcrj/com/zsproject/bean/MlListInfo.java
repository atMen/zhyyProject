package customer.tcrj.com.zsproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leict on 2018/10/19.
 */

public class MlListInfo implements Serializable{


    /**
     * msg : 操作成功
     * stat : 1
     * result : [{"PID":0,"SpotID":1002,"MenuCode":"JCZP","MenuName":"基础照片","Sort":1,"Hierarchy":0,"Path":null,"ID":712,"CreateStaffID":1,"CreateTime":"2018-10-23T16:13:23.73","UpdateStaffID":null,"UpdateTime":null},{"PID":0,"SpotID":1002,"MenuCode":"SBZP","MenuName":"设备照片","Sort":2,"Hierarchy":0,"Path":null,"ID":2552,"CreateStaffID":1,"CreateTime":"2018-10-23T16:15:03.887","UpdateStaffID":null,"UpdateTime":null}]
     */

    private String msg;
    private int stat;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * PID : 0
         * SpotID : 1002
         * MenuCode : JCZP
         * MenuName : 基础照片
         * Sort : 1
         * Hierarchy : 0
         * Path : null
         * ID : 712
         * CreateStaffID : 1
         * CreateTime : 2018-10-23T16:13:23.73
         * UpdateStaffID : null
         * UpdateTime : null
         */

        private int PID;
        private int SpotID;
        private String MenuCode;
        private String MenuName;
        private int Sort;
        private int Hierarchy;
        private Object Path;
        private int ID;
        private int CreateStaffID;
        private String CreateTime;
        private Object UpdateStaffID;
        private Object UpdateTime;

        public int getPID() {
            return PID;
        }

        public void setPID(int PID) {
            this.PID = PID;
        }

        public int getSpotID() {
            return SpotID;
        }

        public void setSpotID(int SpotID) {
            this.SpotID = SpotID;
        }

        public String getMenuCode() {
            return MenuCode;
        }

        public void setMenuCode(String MenuCode) {
            this.MenuCode = MenuCode;
        }

        public String getMenuName() {
            return MenuName;
        }

        public void setMenuName(String MenuName) {
            this.MenuName = MenuName;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public int getHierarchy() {
            return Hierarchy;
        }

        public void setHierarchy(int Hierarchy) {
            this.Hierarchy = Hierarchy;
        }

        public Object getPath() {
            return Path;
        }

        public void setPath(Object Path) {
            this.Path = Path;
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

        public Object getUpdateStaffID() {
            return UpdateStaffID;
        }

        public void setUpdateStaffID(Object UpdateStaffID) {
            this.UpdateStaffID = UpdateStaffID;
        }

        public Object getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(Object UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
