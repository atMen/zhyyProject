package customer.tcrj.com.zsproject.bean;

import java.io.Serializable;

/**
 * Created by leict on 2018/10/19.
 */

public class LoginInfo implements Serializable{

    /**
     * msg : 操作成功
     * stat : 1
     * result : {"ID":1,"Name":"超级管理员","DeptName":"天诚软件有限公司"}
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

    public static class ResultBean implements Serializable{
        /**
         * ID : 1
         * Name : 超级管理员
         * DeptName : 天诚软件有限公司
         */

        private int ID;
        private String Name;
        private String DeptName;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getDeptName() {
            return DeptName;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }
    }
}
