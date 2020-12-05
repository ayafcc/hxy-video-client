package cn.mahua.vod.bean;

public class GoldWithdrawRecordBean {

    /**
     * id : 26
     * user_id : 23
     * num : 10.00
     * status : 0
     * remark : null
     * created_time : 1571759130
     * updated_time : 1571759130
     * success_time : 0
     * fail_time : 0
     * type : 1
     * account : 丰富的
     * realname : 豆腐干豆腐
     * gold_num : 1000
     * user_name : 13594239746
     */

    private int id;
    private int user_id;
    private String num;
    private int status;
    private Object remark;
    private long created_time;
    private long updated_time;
    private long success_time;
    private int fail_time;
    private int type;
    private String account;
    private String realname;
    private int gold_num;
    private String user_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public long getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(long updated_time) {
        this.updated_time = updated_time;
    }

    public long getSuccess_time() {
        return success_time;
    }

    public void setSuccess_time(long success_time) {
        this.success_time = success_time;
    }

    public int getFail_time() {
        return fail_time;
    }

    public void setFail_time(int fail_time) {
        this.fail_time = fail_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getGold_num() {
        return gold_num;
    }

    public void setGold_num(int gold_num) {
        this.gold_num = gold_num;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
