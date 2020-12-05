package cn.mahua.vod.entity;

/**
 * Created by YangXun
 * on 2019/9/25
 * on class describe
 * 更新对象
 */
public class UpdateEntity {

    /**
     * appRemark : 第一版说明
     * appText : 中心第一版
     * appUrl : https://blog.csdn.net/kaige8312/article/details/83543699
     * appVersion : 1.0.0
     * createTime : 1569403314000
     * id : 1
     * isUpdate : 1 强制更新 其他 不强制更新
     * systemName : Android
     * systemType : 1
     */

    private String appRemark;
    private String appText;
    private String appUrl;
    private String appVersion;
    private long createTime;
    private int isUpdate;
    private int id;
    private String systemName;
    private int systemType;

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }


    public String getAppRemark() {
        return appRemark;
    }

    public void setAppRemark(String appRemark) {
        this.appRemark = appRemark;
    }

    public String getAppText() {
        return appText;
    }

    public void setAppText(String appText) {
        this.appText = appText;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public int getSystemType() {
        return systemType;
    }

    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }
}
