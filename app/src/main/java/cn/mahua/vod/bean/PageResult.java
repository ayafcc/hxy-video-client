package cn.mahua.vod.bean;

@SuppressWarnings("unused")
public class PageResult<T> {

    private Page<T> data;
    private int code;//状态码
    private String msg;


    public Page<T> getData() {
        return data;
    }

    public void setData(Page<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccessful(){
        return code == 200;
    }

}
