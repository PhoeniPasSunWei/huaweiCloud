package com.sunwei.demo.utils.mybatisPlus;

import com.alibaba.fastjson.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface ISysBaseAPI {
    void addLog(String var1, Integer var2, Integer var3);

    List<String> getRolesByUsername(String var1);

    String getDatabaseType() throws SQLException;

    List<String> queryTableDictByKeys(String var1, String var2, String var3, String[] var4);

    JSONObject queryAllUser(String[] var1, int var2, int var3);

    List<String> getRoleIdsByUsername(String var1);

    String getDepartIdsByOrgCode(String var1);

    DynamicDataSourceModel getDynamicDbSourceById(String var1);

    DynamicDataSourceModel getDynamicDbSourceByCode(String var1);

    List<String> getDeptHeadByDepId(String var1);
}
