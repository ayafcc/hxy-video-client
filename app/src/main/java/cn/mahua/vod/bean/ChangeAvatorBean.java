package cn.mahua.vod.bean;

public class ChangeAvatorBean {

    /**
     * file : upload/user/6/66.jpg
     * type : image
     * size : 1856.02
     * flag : user
     * ctime : 1574268430
     * thumb_class :
     */

    private String file;
    private String type;
    private double size;
    private String flag;
    private int ctime;
    private String thumb_class;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    public String getThumb_class() {
        return thumb_class;
    }

    public void setThumb_class(String thumb_class) {
        this.thumb_class = thumb_class;
    }
}
