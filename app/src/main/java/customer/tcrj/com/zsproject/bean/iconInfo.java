package customer.tcrj.com.zsproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leict on 2018/10/19.
 */

public class iconInfo implements Serializable{

    /**
     * msg : 操作成功
     * stat : 1
     * result : [{"ID":65,"FileUrl":"http://192.168.20.43/UploadedFiles/SpotFile/27/12166973_1340949652397_800x60020181018174540192.jpg"},{"ID":66,"FileUrl":"http://192.168.20.43/UploadedFiles/SpotFile/27/39380899_1412741915392_800x60020181018174540195.jpg"}]
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
         * ID : 65
         * FileUrl : http://192.168.20.43/UploadedFiles/SpotFile/27/12166973_1340949652397_800x60020181018174540192.jpg
         */

        private int ID;
        private String FileUrl;

        public String getFileType() {
            return FileType;
        }

        public void setFileType(String fileType) {
            FileType = fileType;
        }

        private String FileType;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getFileUrl() {
            return FileUrl;
        }

        public void setFileUrl(String FileUrl) {
            this.FileUrl = FileUrl;
        }
    }
}
