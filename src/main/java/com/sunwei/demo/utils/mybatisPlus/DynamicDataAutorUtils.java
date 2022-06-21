package com.sunwei.demo.utils.mybatisPlus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class DynamicDataAutorUtils {
    public static final String MENU_DATA_AUTHOR_RULES = "MENU_DATA_AUTHOR_RULES";
    public static final String MENU_DATA_AUTHOR_RULE_SQL = "MENU_DATA_AUTHOR_RULE_SQL";

    public DynamicDataAutorUtils() {
    }

    public static synchronized void installDataSearchConditon(HttpServletRequest request, List<SysPermissionDataRule> dataRules) {
        List<SysPermissionDataRule> list = loadDataSearchConditon();
        if (list == null) {
            list = new ArrayList();
        }

        Iterator var3 = dataRules.iterator();

        while(var3.hasNext()) {
            SysPermissionDataRule tsDataRule = (SysPermissionDataRule)var3.next();
            ((List)list).add(tsDataRule);
        }

        request.setAttribute("MENU_DATA_AUTHOR_RULES", list);
    }

    public static synchronized List<SysPermissionDataRule> loadDataSearchConditon() {
        return (List)SpringContextUtils.getHttpServletRequest().getAttribute("MENU_DATA_AUTHOR_RULES");
    }

    public static synchronized String loadDataSearchConditonSQLString() {
        return (String)SpringContextUtils.getHttpServletRequest().getAttribute("MENU_DATA_AUTHOR_RULE_SQL");
    }

    public static synchronized void installDataSearchConditon(HttpServletRequest request, String sql) {
        String ruleSql = loadDataSearchConditonSQLString();
        if (!StringUtils.hasText(ruleSql)) {
            request.setAttribute("MENU_DATA_AUTHOR_RULE_SQL", sql);
        }

    }
}
