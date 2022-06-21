package com.sunwei.demo.utils.mybatisPlus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlInjectionUtil {
    private static final Logger log = LoggerFactory.getLogger(SqlInjectionUtil.class);
    static final String xssStr = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,";

    public SqlInjectionUtil() {
    }

    public static void filterContent(String value) {
        if (value != null && !"".equals(value)) {
            value = value.toLowerCase();
            String[] xssArr = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,".split("\\|");

            for(int i = 0; i < xssArr.length; ++i) {
                if (value.indexOf(xssArr[i]) > -1) {
                    log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                    log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                    throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }

        }
    }

    public static void filterContent(String[] values) {
        String[] xssArr = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,".split("\\|");
        String[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String value = var2[var4];
            if (value == null || "".equals(value)) {
                return;
            }

            value = value.toLowerCase();

            for(int i = 0; i < xssArr.length; ++i) {
                if (value.indexOf(xssArr[i]) > -1) {
                    log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                    log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                    throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }
        }

    }

    /** @deprecated */
    @Deprecated
    public static void specialFilterContent(String value) {
        String specialXssStr = " exec | insert | select | delete | update | drop | count | chr | mid | master | truncate | char | declare |;|+|";
        String[] xssArr = specialXssStr.split("\\|");
        if (value != null && !"".equals(value)) {
            value = value.toLowerCase();

            for(int i = 0; i < xssArr.length; ++i) {
                if (value.indexOf(xssArr[i]) > -1 || value.startsWith(xssArr[i].trim())) {
                    log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                    log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                    throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }

        }
    }

    /** @deprecated */
    @Deprecated
    public static void specialFilterContentForOnlineReport(String value) {
        String specialXssStr = " exec | insert | delete | update | drop | chr | mid | master | truncate | char | declare |";
        String[] xssArr = specialXssStr.split("\\|");
        if (value != null && !"".equals(value)) {
            value = value.toLowerCase();

            for(int i = 0; i < xssArr.length; ++i) {
                if (value.indexOf(xssArr[i]) > -1 || value.startsWith(xssArr[i].trim())) {
                    log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                    log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                    throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }

        }
    }
}
