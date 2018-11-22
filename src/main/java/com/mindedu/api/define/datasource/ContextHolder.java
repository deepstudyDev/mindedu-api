package com.mindedu.api.define.datasource;

public class ContextHolder {

    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(DataSourceType dataSourceType) { contextHolder.set(dataSourceType); }

    public static DataSourceType getDataSourceType() { return contextHolder.get(); }

    public static void clearDataSourceType() { contextHolder.remove(); }
}
