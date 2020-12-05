package cn.mahua.vod.bean;

public class PointPurchseBean {

    /**
     * user_id : 64
     * order_code : PAY20191120011606512230
     * order_price : 200
     * order_time : 1574183766
     * order_points : 2000
     */

    private int user_id;
    private String order_code;
    private String order_price;
    private int order_time;
    private int order_points;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public int getOrder_time() {
        return order_time;
    }

    public void setOrder_time(int order_time) {
        this.order_time = order_time;
    }

    public int getOrder_points() {
        return order_points;
    }

    public void setOrder_points(int order_points) {
        this.order_points = order_points;
    }
}
