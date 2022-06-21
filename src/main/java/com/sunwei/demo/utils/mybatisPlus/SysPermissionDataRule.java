package com.sunwei.demo.utils.mybatisPlus;

import java.util.Date;

public class SysPermissionDataRule {
    private String id;
    private String permissionId;
    private String ruleName;
    private String ruleColumn;
    private String ruleConditions;
    private String ruleValue;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;

    public SysPermissionDataRule() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleColumn() {
        return this.ruleColumn;
    }

    public void setRuleColumn(String ruleColumn) {
        this.ruleColumn = ruleColumn;
    }

    public String getRuleConditions() {
        return this.ruleConditions;
    }

    public void setRuleConditions(String ruleConditions) {
        this.ruleConditions = ruleConditions;
    }

    public String getRuleValue() {
        return this.ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
