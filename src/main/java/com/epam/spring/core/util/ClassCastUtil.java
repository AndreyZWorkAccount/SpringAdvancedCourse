package com.epam.spring.core.util;

public class ClassCastUtil {

    public static java.sql.Date getDateUtilToSQL(java.util.Date utilDate){
        return new java.sql.Date(utilDate.getTime());
    }

    public static java.util.Date getDateSQLToUtil(java.sql.Date sqlDate){
        return new java.util.Date(sqlDate.getTime());
    }
    
}
