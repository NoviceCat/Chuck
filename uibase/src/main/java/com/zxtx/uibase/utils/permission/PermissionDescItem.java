package com.zxtx.uibase.utils.permission;

import java.io.Serializable;

/**
 * @author lindiancheng
 * @date 2021-11-16
 * @desc 权限申请描述信息
 */
public class PermissionDescItem implements Serializable {
    public PermissionDescItem() {
    }

    public PermissionDescItem(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    //权限名称
    private  String title;
    //申请权限的描述
    private  String desc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
