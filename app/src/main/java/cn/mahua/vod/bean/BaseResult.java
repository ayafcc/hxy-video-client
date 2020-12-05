package cn.mahua.vod.bean;

/**
 * Created by 奈蜇 on 2018-09-29.
 *  请求结果格式类
 */
public class BaseResult<T> {
    private T data;
    private int code;//状态码
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
