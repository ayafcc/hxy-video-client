package cn.mahua.vod.bean;

public class OrderBean {

    /**
     * order_id : 1
     * user_id : 2
     * order_status : 0
     * order_code : PAY20190706160152412630
     * order_price : 10.00
     * order_time : 1562400112
     * order_points : 10
     * order_pay_type :
     * order_pay_time : 0
     * order_remarks :
     */

    private int order_id;
    private int user_id;
    private int order_status;
    private String order_code;
    private String order_price;
    private int order_time;
    private int order_points;
    private String order_pay_type;
    private int order_pay_time;
    private String order_remarks;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
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

    public String getOrder_pay_type() {
        return order_pay_type;
    }

    public void setOrder_pay_type(String order_pay_type) {
        this.order_pay_type = order_pay_type;
    }

    public int getOrder_pay_time() {
        return order_pay_time;
    }

    public void setOrder_pay_time(int order_pay_time) {
        this.order_pay_time = order_pay_time;
    }

    public String getOrder_remarks() {
        return order_remarks;
    }

    public void setOrder_remarks(String order_remarks) {
        this.order_remarks = order_remarks;
    }
}
