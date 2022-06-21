package com.sunwei.demo.utils.mybatisPlus;

import org.springframework.beans.BeanUtils;

public class DynamicDataSourceModel {
    private String id;
    private String code;
    private String dbType;
    private String dbDriver;
    private String dbUrl;
    private String dbName;
    private String dbUsername;
    private String dbPassword;

    public DynamicDataSourceModel(Object dbSource) {
        if (dbSource != null) {
            BeanUtils.copyProperties(dbSource, this);
        }

    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getDbType() {
        return this.dbType;
    }

    public String getDbDriver() {
        return this.dbDriver;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getDbUsername() {
        return this.dbUsername;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DynamicDataSourceModel)) {
            return false;
        } else {
            DynamicDataSourceModel other = (DynamicDataSourceModel)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label107: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label107;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label107;
                    }

                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$dbType = this.getDbType();
                Object other$dbType = other.getDbType();
                if (this$dbType == null) {
                    if (other$dbType != null) {
                        return false;
                    }
                } else if (!this$dbType.equals(other$dbType)) {
                    return false;
                }

                label86: {
                    Object this$dbDriver = this.getDbDriver();
                    Object other$dbDriver = other.getDbDriver();
                    if (this$dbDriver == null) {
                        if (other$dbDriver == null) {
                            break label86;
                        }
                    } else if (this$dbDriver.equals(other$dbDriver)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$dbUrl = this.getDbUrl();
                    Object other$dbUrl = other.getDbUrl();
                    if (this$dbUrl == null) {
                        if (other$dbUrl == null) {
                            break label79;
                        }
                    } else if (this$dbUrl.equals(other$dbUrl)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$dbName = this.getDbName();
                    Object other$dbName = other.getDbName();
                    if (this$dbName == null) {
                        if (other$dbName == null) {
                            break label72;
                        }
                    } else if (this$dbName.equals(other$dbName)) {
                        break label72;
                    }

                    return false;
                }

                Object this$dbUsername = this.getDbUsername();
                Object other$dbUsername = other.getDbUsername();
                if (this$dbUsername == null) {
                    if (other$dbUsername != null) {
                        return false;
                    }
                } else if (!this$dbUsername.equals(other$dbUsername)) {
                    return false;
                }

                Object this$dbPassword = this.getDbPassword();
                Object other$dbPassword = other.getDbPassword();
                if (this$dbPassword == null) {
                    if (other$dbPassword != null) {
                        return false;
                    }
                } else if (!this$dbPassword.equals(other$dbPassword)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DynamicDataSourceModel;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $dbType = this.getDbType();
        result = result * 59 + ($dbType == null ? 43 : $dbType.hashCode());
        Object $dbDriver = this.getDbDriver();
        result = result * 59 + ($dbDriver == null ? 43 : $dbDriver.hashCode());
        Object $dbUrl = this.getDbUrl();
        result = result * 59 + ($dbUrl == null ? 43 : $dbUrl.hashCode());
        Object $dbName = this.getDbName();
        result = result * 59 + ($dbName == null ? 43 : $dbName.hashCode());
        Object $dbUsername = this.getDbUsername();
        result = result * 59 + ($dbUsername == null ? 43 : $dbUsername.hashCode());
        Object $dbPassword = this.getDbPassword();
        result = result * 59 + ($dbPassword == null ? 43 : $dbPassword.hashCode());
        return result;
    }

    public String toString() {
        return "DynamicDataSourceModel(id=" + this.getId() + ", code=" + this.getCode() + ", dbType=" + this.getDbType() + ", dbDriver=" + this.getDbDriver() + ", dbUrl=" + this.getDbUrl() + ", dbName=" + this.getDbName() + ", dbUsername=" + this.getDbUsername() + ", dbPassword=" + this.getDbPassword() + ")";
    }
}
