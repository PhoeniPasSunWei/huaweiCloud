package com.sunwei.demo.utils.mybatisPlus;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;

public class QueryGenerator {
    private static final Logger log = LoggerFactory.getLogger(QueryGenerator.class);
    public static final String SQL_RULES_COLUMN = "SQL_RULES_COLUMN";
    public static final String BEGIN = "_begin";
    public static final String END = "_end";
    private static final String STAR = "*";
    private static final String COMMA = ",";
    private static final String NOT_EQUAL = "!";
    private static final String QUERY_SEPARATE_KEYWORD = " ";
    private static final String SUPER_QUERY_PARAMS = "superQueryParams";
    private static final String ORDER_COLUMN = "column";
    private static final String ORDER_TYPE = "order";
    private static final String ORDER_TYPE_ASC = "ASC";
    private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal();
    private static String DB_TYPE;

    public QueryGenerator() {
    }

    private static SimpleDateFormat getTime() {
        SimpleDateFormat time = (SimpleDateFormat)local.get();
        if (time == null) {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            local.set(time);
        }

        return time;
    }

    public static <T> QueryWrapper<T> initQueryWrapper(T searchObj, Map<String, String[]> parameterMap) {
        long start = System.currentTimeMillis();
        QueryWrapper<T> queryWrapper = new QueryWrapper();
        installMplus(queryWrapper, searchObj, parameterMap);
        log.debug("---查询条件构造器初始化完成,耗时:" + (System.currentTimeMillis() - start) + "毫秒----");
        return queryWrapper;
    }

    public static void installMplus(QueryWrapper<?> queryWrapper, Object searchObj, Map<String, String[]> parameterMap) {
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(searchObj);
        Map<String, SysPermissionDataRule> ruleMap = getRuleMap();
        Iterator var5 = ruleMap.keySet().iterator();

        String type;
        while(var5.hasNext()) {
            type = (String)var5.next();
            if (ObjConvertUtils.isNotEmpty(type) && type.startsWith("SQL_RULES_COLUMN")) {
                String finalType = type;
                queryWrapper.and((ix) -> {
                    //QueryWrapper var10000 = (QueryWrapper)ix.apply(getSqlRuleValue(((SysPermissionDataRule)ruleMap.get(type)).getRuleValue()), new Object[0]);
                    QueryWrapper var10000 = (QueryWrapper)ix.apply(getSqlRuleValue(((SysPermissionDataRule)ruleMap.get(finalType)).getRuleValue()), new Object[0]);
                });
            }
        }

        for(int i = 0; i < origDescriptors.length; ++i) {
            String name = origDescriptors[i].getName();
            type = origDescriptors[i].getPropertyType().toString();

            try {
                if (!judgedIsUselessField(name) && PropertyUtils.isReadable(searchObj, name)) {
                    if (ruleMap.containsKey(name)) {
                        addRuleToQueryWrapper((SysPermissionDataRule)ruleMap.get(name), name, origDescriptors[i].getPropertyType(), queryWrapper);
                    }

                    String endValue = null;
                    String beginValue = null;
                    if (parameterMap != null && parameterMap.containsKey(name + "_begin")) {
                        beginValue = ((String[])parameterMap.get(name + "_begin"))[0].trim();
                        addQueryByRule(queryWrapper, name, type, beginValue, QueryRuleEnum.GE);
                    }

                    if (parameterMap != null && parameterMap.containsKey(name + "_end")) {
                        endValue = ((String[])parameterMap.get(name + "_end"))[0].trim();
                        addQueryByRule(queryWrapper, name, type, endValue, QueryRuleEnum.LE);
                    }

                    Object value = PropertyUtils.getSimpleProperty(searchObj, name);
                    if (null != value && value.toString().startsWith(",") && value.toString().endsWith(",")) {
                        String multiLikeval = value.toString().replace(",,", ",");
                        String[] vals = multiLikeval.substring(1, multiLikeval.length()).split(",");
                        String field = ObjConvertUtils.camelToUnderline(name);
                        if (vals.length > 1) {
                            queryWrapper.and((j) -> {
                                j = (QueryWrapper)j.like(field, vals[0]);

                                for(int k = 1; k < vals.length; ++k) {
                                    j = (QueryWrapper)((QueryWrapper)j.or()).like(field, vals[k]);
                                }

                            });
                        } else {
                            queryWrapper.and((j) -> {
                                QueryWrapper var10000 = (QueryWrapper)j.like(field, vals[0]);
                            });
                        }
                    } else {
                        QueryRuleEnum rule = convert2Rule(value);
                        value = replaceValue(rule, value);
                        addEasyQuery(queryWrapper, name, rule, value);
                    }
                }
            } catch (Exception var14) {
                log.error(var14.getMessage(), var14);
            }
        }

        doMultiFieldsOrder(queryWrapper, parameterMap);
        doSuperQuery(queryWrapper, parameterMap);
    }

    public static void doMultiFieldsOrder(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap) {
        String column = null;
        String order = null;
        if (parameterMap != null && parameterMap.containsKey("column")) {
            column = ((String[])parameterMap.get("column"))[0];
        }

        if (parameterMap != null && parameterMap.containsKey("order")) {
            order = ((String[])parameterMap.get("order"))[0];
        }

        log.debug("排序规则>>列:" + column + ",排序方式:" + order);
        if (ObjConvertUtils.isNotEmpty(column) && ObjConvertUtils.isNotEmpty(order)) {
            if (column.endsWith("_dictText")) {
                column = column.substring(0, column.lastIndexOf("_dictText"));
            }

            SqlInjectionUtil.filterContent(column);
            if (order.toUpperCase().indexOf("ASC") >= 0) {
                queryWrapper.orderByAsc(ObjConvertUtils.camelToUnderline(column));
            } else {
                queryWrapper.orderByDesc(ObjConvertUtils.camelToUnderline(column));
            }
        }

    }

    public static void doSuperQuery(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap) {
        if (parameterMap != null && parameterMap.containsKey("superQueryParams")) {
            String superQueryParams = ((String[])parameterMap.get("superQueryParams"))[0];

            try {
                superQueryParams = URLDecoder.decode(superQueryParams, "UTF-8");
            } catch (UnsupportedEncodingException var6) {
                log.error("--高级查询参数转码失败!", var6);
            }

            List<QueryCondition> conditions = JSON.parseArray(superQueryParams, QueryCondition.class);
            log.info("---高级查询参数-->" + conditions.toString());
            Iterator var4 = conditions.iterator();

            while(var4.hasNext()) {
                QueryCondition rule = (QueryCondition)var4.next();
                if (ObjConvertUtils.isNotEmpty(rule.getField()) && ObjConvertUtils.isNotEmpty(rule.getRule()) && ObjConvertUtils.isNotEmpty(rule.getVal())) {
                    addEasyQuery(queryWrapper, rule.getField(), QueryRuleEnum.getByValue(rule.getRule()), rule.getVal());
                }
            }
        }

    }

    private static QueryRuleEnum convert2Rule(Object value) {
        if (value == null) {
            return null;
        } else {
            String val = (value + "").toString().trim();
            if (val.length() == 0) {
                return null;
            } else {
                QueryRuleEnum rule = null;
                if (rule == null && val.length() >= 3 && " ".equals(val.substring(2, 3))) {
                    rule = QueryRuleEnum.getByValue(val.substring(0, 2));
                }

                if (rule == null && val.length() >= 2 && " ".equals(val.substring(1, 2))) {
                    rule = QueryRuleEnum.getByValue(val.substring(0, 1));
                }

                if (rule == null && val.contains("*")) {
                    if (val.startsWith("*") && val.endsWith("*")) {
                        rule = QueryRuleEnum.LIKE;
                    } else if (val.startsWith("*")) {
                        rule = QueryRuleEnum.LEFT_LIKE;
                    } else if (val.endsWith("*")) {
                        rule = QueryRuleEnum.RIGHT_LIKE;
                    }
                }

                if (rule == null && val.contains(",")) {
                    rule = QueryRuleEnum.IN;
                }

                if (rule == null && val.startsWith("!")) {
                    rule = QueryRuleEnum.NE;
                }

                return rule != null ? rule : QueryRuleEnum.EQ;
            }
        }
    }

    private static Object replaceValue(QueryRuleEnum rule, Object value) {
        if (rule == null) {
            return null;
        } else if (!(value instanceof String)) {
            return value;
        } else {
            String val = (value + "").toString().trim();
            if (rule == QueryRuleEnum.LIKE) {
                value = val.substring(1, val.length() - 1);
            } else if (rule != QueryRuleEnum.LEFT_LIKE && rule != QueryRuleEnum.NE) {
                if (rule == QueryRuleEnum.RIGHT_LIKE) {
                    value = val.substring(0, val.length() - 1);
                } else if (rule == QueryRuleEnum.IN) {
                    value = val.split(",");
                } else if (val.startsWith(rule.getValue())) {
                    value = val.replaceFirst(rule.getValue(), "");
                } else if (val.startsWith(rule.getCondition() + " ")) {
                    value = val.replaceFirst(rule.getCondition() + " ", "").trim();
                }
            } else {
                value = val.substring(1);
            }

            return value;
        }
    }

    private static void addQueryByRule(QueryWrapper<?> queryWrapper, String name, String type, String value, QueryRuleEnum rule) throws ParseException {
        if (ObjConvertUtils.isNotEmpty(value)) {
            byte var7 = -1;
            switch(type.hashCode()) {
                case -1561781994:
                    if (type.equals("class java.util.Date")) {
                        var7 = 6;
                    }
                    break;
                case -1228562056:
                    if (type.equals("class java.lang.Long")) {
                        var7 = 3;
                    }
                    break;
                case -1066470206:
                    if (type.equals("class java.lang.Integer")) {
                        var7 = 0;
                    }
                    break;
                case -105483565:
                    if (type.equals("class java.math.BigDecimal")) {
                        var7 = 1;
                    }
                    break;
                case 239044557:
                    if (type.equals("class java.lang.Double")) {
                        var7 = 5;
                    }
                    break;
                case 563652320:
                    if (type.equals("class java.lang.Float")) {
                        var7 = 4;
                    }
                    break;
                case 575539456:
                    if (type.equals("class java.lang.Short")) {
                        var7 = 2;
                    }
            }

            Object temp;
            switch(var7) {
                case 0:
                    temp = Integer.parseInt(value);
                    break;
                case 1:
                    temp = new BigDecimal(value);
                    break;
                case 2:
                    temp = Short.parseShort(value);
                    break;
                case 3:
                    temp = Long.parseLong(value);
                    break;
                case 4:
                    temp = Float.parseFloat(value);
                    break;
                case 5:
                    temp = Double.parseDouble(value);
                    break;
                case 6:
                    temp = getDateQueryByRule(value, rule);
                    break;
                default:
                    temp = value;
            }

            addEasyQuery(queryWrapper, name, rule, temp);
        }

    }

    private static Date getDateQueryByRule(String value, QueryRuleEnum rule) throws ParseException {
        Date date = null;
        if (value.length() == 10) {
            if (rule == QueryRuleEnum.GE) {
                date = getTime().parse(value + " 00:00:00");
            } else if (rule == QueryRuleEnum.LE) {
                date = getTime().parse(value + " 23:59:59");
            }
        }

        if (date == null) {
            date = getTime().parse(value);
        }

        return date;
    }

    private static void addEasyQuery(QueryWrapper<?> queryWrapper, String name, QueryRuleEnum rule, Object value) {
        if (value != null && rule != null && !ObjConvertUtils.isEmpty(value)) {
            name = ObjConvertUtils.camelToUnderline(name);
            log.info("--查询规则-->" + name + " " + rule.getValue() + " " + value);
            switch(rule) {
                case GT:
                    queryWrapper.gt(name, value);
                    break;
                case GE:
                    queryWrapper.ge(name, value);
                    break;
                case LT:
                    queryWrapper.lt(name, value);
                    break;
                case LE:
                    queryWrapper.le(name, value);
                    break;
                case EQ:
                    queryWrapper.eq(name, value);
                    break;
                case NE:
                    queryWrapper.ne(name, value);
                    break;
                case IN:
                    if (value instanceof String) {
                        queryWrapper.in(name, (Object[])value.toString().split(","));
                    } else if (value instanceof String[]) {
                        queryWrapper.in(name, (Object[])((Object[])value));
                    } else {
                        queryWrapper.in(name, new Object[]{value});
                    }
                    break;
                case LIKE:
                    queryWrapper.like(name, value);
                    break;
                case LEFT_LIKE:
                    queryWrapper.likeLeft(name, value);
                    break;
                case RIGHT_LIKE:
                    queryWrapper.likeRight(name, value);
                    break;
                default:
                    log.info("--查询规则未匹配到---");
            }

        }
    }

    private static boolean judgedIsUselessField(String name) {
        return "class".equals(name) || "ids".equals(name) || "page".equals(name) || "rows".equals(name) || "sort".equals(name) || "order".equals(name);
    }

    public static Map<String, SysPermissionDataRule> getRuleMap() {
        Map<String, SysPermissionDataRule> ruleMap = new HashMap();
        List<SysPermissionDataRule> list = DynamicDataAutorUtils.loadDataSearchConditon();
        if (list != null && list.size() > 0) {
            if (list.get(0) == null) {
                return ruleMap;
            }

            SysPermissionDataRule rule;
            String column;
            for(Iterator var2 = list.iterator(); var2.hasNext(); ruleMap.put(column, rule)) {
                rule = (SysPermissionDataRule)var2.next();
                column = rule.getRuleColumn();
                if (QueryRuleEnum.SQL_RULES.getValue().equals(rule.getRuleConditions())) {
                    column = "SQL_RULES_COLUMN" + rule.getId();
                }
            }
        }

        return ruleMap;
    }

    private static void addRuleToQueryWrapper(SysPermissionDataRule dataRule, String name, Class propertyType, QueryWrapper<?> queryWrapper) {
        QueryRuleEnum rule = QueryRuleEnum.getByValue(dataRule.getRuleConditions());
        if (rule.equals(QueryRuleEnum.IN) && !propertyType.equals(String.class)) {
            String[] values = dataRule.getRuleValue().split(",");
            Object[] objs = new Object[values.length];

            for(int i = 0; i < values.length; ++i) {
                objs[i] = NumberUtils.parseNumber(values[i], propertyType);
            }

            addEasyQuery(queryWrapper, name, rule, objs);
        } else if (propertyType.equals(String.class)) {
            addEasyQuery(queryWrapper, name, rule, convertRuleValue(dataRule.getRuleValue()));
        } else {
            addEasyQuery(queryWrapper, name, rule, NumberUtils.parseNumber(dataRule.getRuleValue(), propertyType));
        }

    }

    public static String convertRuleValue(String ruleValue) {
        String value = null;
        return (String)(value != null ? value : ruleValue);
    }

    public static String getSqlRuleValue(String sqlRule) {
        try {
            Set<String> varParams = getSqlRuleParams(sqlRule);

            String var;
            String tempValue;
            for(Iterator var2 = varParams.iterator(); var2.hasNext(); sqlRule = sqlRule.replace("#{" + var + "}", tempValue)) {
                var = (String)var2.next();
                tempValue = convertRuleValue(var);
            }
        } catch (Exception var5) {
            log.error(var5.getMessage(), var5);
        }

        return sqlRule;
    }

    public static Set<String> getSqlRuleParams(String sql) {
        if (ObjConvertUtils.isEmpty(sql)) {
            return null;
        } else {
            Set<String> varParams = new HashSet();
            String regex = "\\#\\{\\w+\\}";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(sql);

            while(m.find()) {
                String var = m.group();
                varParams.add(var.substring(var.indexOf("{") + 1, var.indexOf("}")));
            }

            return varParams;
        }
    }

    public static String getSingleQueryConditionSql(String field, String alias, Object value, boolean isString) {
        if (value == null) {
            return "";
        } else {
            field = alias + ObjConvertUtils.camelToUnderline(field);
            QueryRuleEnum rule = convert2Rule(value);
            return getSingleSqlByRule(rule, field, value, isString);
        }
    }

    public static String getSingleSqlByRule(QueryRuleEnum rule, String field, Object value, boolean isString) {
        String res = "";
        switch(rule) {
            case GT:
                res = field + rule.getValue() + getFieldConditionValue(value, isString);
                break;
            case GE:
                res = field + rule.getValue() + getFieldConditionValue(value, isString);
                break;
            case LT:
                res = field + rule.getValue() + getFieldConditionValue(value, isString);
                break;
            case LE:
                res = field + rule.getValue() + getFieldConditionValue(value, isString);
                break;
            case EQ:
                res = field + rule.getValue() + getFieldConditionValue(value, isString);
                break;
            case NE:
                res = field + " <> " + getFieldConditionValue(value, isString);
                break;
            case IN:
                res = field + " in " + getInConditionValue(value, isString);
                break;
            case LIKE:
                res = field + " like " + getLikeConditionValue(value);
                break;
            case LEFT_LIKE:
                res = field + " like " + getLikeConditionValue(value);
                break;
            case RIGHT_LIKE:
                res = field + " like " + getLikeConditionValue(value);
                break;
            default:
                res = field + " = " + getFieldConditionValue(value, isString);
        }

        return res;
    }

    private static String getFieldConditionValue(Object value, boolean isString) {
        String str = value.toString().trim();
        if (str.startsWith("!")) {
            str = str.substring(1);
        } else if (str.startsWith(">=")) {
            str = str.substring(2);
        } else if (str.startsWith("<=")) {
            str = str.substring(2);
        } else if (str.startsWith(">")) {
            str = str.substring(1);
        } else if (str.startsWith("<")) {
            str = str.substring(1);
        }

        if (isString) {
            return "SQLSERVER".equals(getDbType()) ? " N'" + str + "' " : " '" + str + "' ";
        } else {
            return value.toString();
        }
    }

    private static String getInConditionValue(Object value, boolean isString) {
        if (isString) {
            String[] temp = value.toString().split(",");
            String res = "";
            String[] var4 = temp;
            int var5 = temp.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String string = var4[var6];
                if ("SQLSERVER".equals(getDbType())) {
                    res = res + ",N'" + string + "'";
                } else {
                    res = res + ",'" + string + "'";
                }
            }

            return "(" + res.substring(1) + ")";
        } else {
            return "(" + value.toString() + ")";
        }
    }

    private static String getLikeConditionValue(Object value) {
        String str = value.toString().trim();
        if (str.startsWith("*") && str.endsWith("*")) {
            return "SQLSERVER".equals(getDbType()) ? "N'%" + str.substring(1, str.length() - 1) + "%'" : "'%" + str.substring(1, str.length() - 1) + "%'";
        } else if (str.startsWith("*")) {
            return "SQLSERVER".equals(getDbType()) ? "N'%" + str.substring(1) + "'" : "'%" + str.substring(1) + "'";
        } else if (str.endsWith("*")) {
            return "SQLSERVER".equals(getDbType()) ? "N'" + str.substring(0, str.length() - 1) + "%'" : "'" + str.substring(0, str.length() - 1) + "%'";
        } else if (str.indexOf("%") >= 0) {
            return "SQLSERVER".equals(getDbType()) ? "N" + str : str;
        } else {
            return "SQLSERVER".equals(getDbType()) ? "N'%" + str + "%'" : "'%" + str + "%'";
        }
    }

    public static String installAuthJdbc(Class<?> clazz) {
        StringBuffer sb = new StringBuffer();
        Map<String, SysPermissionDataRule> ruleMap = getRuleMap();
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
        String sql_and = " and ";
        Iterator var5 = ruleMap.keySet().iterator();

        while(var5.hasNext()) {
            String c = (String)var5.next();
            if (ObjConvertUtils.isNotEmpty(c) && c.startsWith("SQL_RULES_COLUMN")) {
                sb.append(sql_and + getSqlRuleValue(((SysPermissionDataRule)ruleMap.get(c)).getRuleValue()));
            }
        }

        for(int i = 0; i < origDescriptors.length; ++i) {
            String name = origDescriptors[i].getName();
            if (!judgedIsUselessField(name) && ruleMap.containsKey(name)) {
                SysPermissionDataRule dataRule = (SysPermissionDataRule)ruleMap.get(name);
                QueryRuleEnum rule = QueryRuleEnum.getByValue(dataRule.getRuleConditions());
                Class propType = origDescriptors[i].getPropertyType();
                boolean isString = propType.equals(String.class);
                Object value;
                if (isString) {
                    value = convertRuleValue(dataRule.getRuleValue());
                } else {
                    value = NumberUtils.parseNumber(dataRule.getRuleValue(), propType);
                }

                String filedSql = getSingleSqlByRule(rule, ObjConvertUtils.camelToUnderline(name), value, isString);
                sb.append(sql_and + filedSql);
            }
        }

        log.info("query auth sql is:" + sb.toString());
        return sb.toString();
    }

    public static void installAuthMplus(QueryWrapper<?> queryWrapper, Class<?> clazz) {
        Map<String, SysPermissionDataRule> ruleMap = getRuleMap();
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
        Iterator var4 = ruleMap.keySet().iterator();

        while(var4.hasNext()) {
            String c = (String)var4.next();
            if (ObjConvertUtils.isNotEmpty(c) && c.startsWith("SQL_RULES_COLUMN")) {
                queryWrapper.and((ix) -> {
                    QueryWrapper var10000 = (QueryWrapper)ix.apply(getSqlRuleValue(((SysPermissionDataRule)ruleMap.get(c)).getRuleValue()), new Object[0]);
                });
            }
        }

        for(int i = 0; i < origDescriptors.length; ++i) {
            String name = origDescriptors[i].getName();
            if (!judgedIsUselessField(name) && ruleMap.containsKey(name)) {
                addRuleToQueryWrapper((SysPermissionDataRule)ruleMap.get(name), name, origDescriptors[i].getPropertyType(), queryWrapper);
            }
        }

    }

    private static String getDbType() {
        if (ObjConvertUtils.isNotEmpty(DB_TYPE)) {
            return DB_TYPE;
        } else {
            try {
                ISysBaseAPI sysBaseAPI = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
                DB_TYPE = sysBaseAPI.getDatabaseType();
                return DB_TYPE;
            } catch (Exception var1) {
                var1.printStackTrace();
                return DB_TYPE;
            }
        }
    }
}
